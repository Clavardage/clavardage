package clavardage.controller.connectivity;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.gui.MainGUI;
import clavardage.model.managers.ConversationManager;
import clavardage.model.managers.UserManager;
import clavardage.model.objects.Conversation;
import clavardage.model.objects.User;

import java.net.BindException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Handle the network connectivity activities and use infos to update database and program state
 */
public class ConnectivityDaemon {

    private static final Thread daemon, discoveryDaemon, helloDaemon, conversationServerDaemon;
    private static boolean kill;
    private static final ConversationService convService;
    private static final ArrayList<UUID> usersConnected;
    private static final int USER_CONNECTION_TIMEOUT_MS = 10000;

    static {
        kill = false;
        usersConnected = new ArrayList<UUID>();

        /* MAIN DAEMON */

        daemon = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    /* DAEMON LOOP */

                    synchronized(daemon) {
                        while(keepDaemonAlive()) {
                            /* WAIT UNTIL A COMPONENT NOTIFIES THE DAEMON */
                            try {
                                daemon.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /* DISCOVERY SERVICE */

        DiscoveryService disc = null;
        try {
            disc = new DiscoveryService();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DiscoveryService finalDisc = disc;

        helloDaemon = new Thread(() -> {
            while (keepDaemonAlive()) {
                if(AuthOperations.isUserConnected()) {
                    try {
                        finalDisc.sendHello(); // indicate we are alive
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(USER_CONNECTION_TIMEOUT_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        discoveryDaemon = new Thread(new Runnable() {
            @Override
            public void run() {
                UserManager user_mngr = new UserManager();
                synchronized (discoveryDaemon) {
                    while (keepDaemonAlive()) {
                        try {
                            finalDisc.listen(); // wait for new connected users
                            System.out.println("Log: Exiting UDP listener, waiting for signal...");
                            // FIXME: wait UDP
                            //discoveryDaemon.wait();
                            System.out.println("Log: UDP signal received!");
                            User u = finalDisc.getNewUser();
                            if (u != null && AuthOperations.isUserConnected()) {
                                if(!usersConnected.contains(u.getUUID())) {
                                    if (!user_mngr.isUserExist(u.getUUID())) {
                                        // TODO: send userprivate !!!!!!!!!!!!!!!!!
                                        user_mngr.addExistingUser(u.getUUID(), u.getLogin(), "", u.getUUID() + "@clav.com", u.getLastIp());
                                    } else {
                                        user_mngr.updateLogin(u.getUUID(), u.getLogin());
                                        user_mngr.updateLastIp(u.getUUID(), u.getLastIp());
                                    }
                                    usersConnected.add(u.getUUID());
                                    new Thread(() -> {
                                        try {
                                            MainGUI.setUserState(u, true); // alert the GUI
                                            Thread.sleep(USER_CONNECTION_TIMEOUT_MS-1000);
                                            usersConnected.remove(u.getUUID());
                                            MainGUI.setUserState(u, false); // alert the GUI
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }).start();
                                }
                            } else {
                                System.err.println("Server discovery error: User is null!");
                            }
                        } catch (BindException e) {
                            e.printStackTrace();
                            waitForRetryConnection();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        /* CONVERSATION SERVICE */

        ConversationService conv = null;
        try {
            conv = new ConversationService();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ConversationService finalConv = conv;
        conversationServerDaemon = new Thread(new Runnable() {
            @Override
            public void run() {
                ConversationManager conv_mngr = new ConversationManager();
                synchronized (conversationServerDaemon) {
                    while (keepDaemonAlive()) {
                        try {
                            finalConv.listen();
                            conversationServerDaemon.wait();
                            Conversation c = finalConv.getNewConversation();
                            if (c != null && AuthOperations.isUserConnected()) {
                                if (!conv_mngr.isConversationExist(c.getUUID())) {
                                    conv_mngr.addExistingConversation(c.getUUID(), c.getName(), c.getDateCreated(), c.getListUsers());
                                }
                                new Thread(() -> {
                                    try {
                                        MainGUI.askForConversationOpening(c); // alert the GUI
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }).start();
                            } else if(!AuthOperations.isUserConnected()) {
                                System.err.println("Server error: no connected user!");
                            } else {
                                System.err.println("Server error: Conversation is null!");
                            }
                        } catch (BindException e) {
                            e.printStackTrace();
                            waitForRetryConnection();
                        } catch (Exception e) {
                            System.err.println("Server error: " + e);
                            System.out.println("Log: Relaunching server...");
                        }
                    }
                }
            }
        });

        convService = finalConv;
    }

    public static ConversationService getConversationService() {
        return convService;
    }

    private static boolean keepDaemonAlive() {
        return !kill;
    }

    public static void stop() {
        kill = true;
        notifyThread();
        synchronized (helloDaemon) {
            helloDaemon.interrupt();
        }
        synchronized (discoveryDaemon) {
            discoveryDaemon.interrupt();
        }
        synchronized (conversationServerDaemon) {
            conversationServerDaemon.interrupt();
        }
    }

    public static void start() {
        daemon.start();
        helloDaemon.start();
        discoveryDaemon.start();
        conversationServerDaemon.start();
    }

    public static void notifyThread() {
        synchronized (daemon) {
            daemon.notify();
        }
    }

    public static void notifyDiscoveryDaemon() {
        synchronized (discoveryDaemon) {
            discoveryDaemon.notify();
        }
    }

    public static void notifyConversationDaemon() {
        synchronized (conversationServerDaemon) {
            conversationServerDaemon.notify();
        }
    }

    private static void waitForRetryConnection() {
        try {
            System.err.print("Error, unable to connect, retrying in 5");
            Thread.sleep(1000);
            System.err.print(", 4");
            Thread.sleep(1000);
            System.err.print(", 3");
            Thread.sleep(1000);
            System.err.print(", 2");
            Thread.sleep(1000);
            System.err.println(", 1");
            Thread.sleep(1000);
            System.err.println("Retrying...");
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}
