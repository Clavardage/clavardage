package clavardage.controller.connectivity;

import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.data.DatabaseSynchronizer;
import clavardage.controller.gui.MainGUI;
import clavardage.model.managers.ConversationManager;
import clavardage.model.managers.UserManager;
import clavardage.model.objects.*;

import java.net.BindException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * Handle the network connectivity activities and use infos to update database and program state
 * Static constructor set up the daemons threads and make them ready to run
 * @author Romain MONIER
 */
public class ConnectivityDaemon {

    private static final Thread daemon, discoveryDaemon, helloDaemon, conversationServerDaemon, synchronizerServerDaemon, synchronizerClientDaemon;
    private static boolean kill;
    private static final ConversationService convService;
    private static final ArrayList<UUID> usersConnected;
    private static final int USER_CONNECTION_TIMEOUT_MS = 10000, SYNCHRONIZER_CONNECTION_TIMEOUT_MS = 60000;
    private static final boolean ENABLE_SYNCHRONIZER;

    static {
        kill = false;
        usersConnected = new ArrayList<UUID>();

        ENABLE_SYNCHRONIZER = true;

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

        /* SYNCHRONIZER SERVICE */

        SynchronizerService sync = null;
        try {
            sync = new SynchronizerService();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SynchronizerService finalSync = sync;

        synchronizerClientDaemon = new Thread(() -> {
            while (keepDaemonAlive()) {
                for(UUID uuid : usersConnected) {
                    try {
                        // send database data to all users connected
                        // also, it's not really safe to send unencrypted hashed passwords in UDP but well it will be okay for this project I guess...
                        //TODO: HANDLE THE CHANGES BY USING A TIMESTAMP OR SOMETHING LIKE THAT OTHERWISE IT WILL LOOP WITH UNDEFINED BEHAVIOR!!!!
                        finalSync.sendDataTo(DatabaseSynchronizer.getData(), (new UserManager()).getUserByUUID(uuid).getLastIp());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(SYNCHRONIZER_CONNECTION_TIMEOUT_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        synchronizerServerDaemon = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (synchronizerServerDaemon) {
                    while (keepDaemonAlive()) {
                        try {
                            finalSync.listen();
                            System.out.println("Log: Exiting SYNC-UDP listener, waiting for signal...");
                            // FIXME: wait UDP
                            //synchronizerServerDaemon.wait();
                            System.out.println("Log: SYNC-UDP signal received!");

                            DatabaseMap<Class<?>, ArrayList<?>> data = finalSync.getNewData();

                            //TODO: CHECK TIMESTAMP FOR EACH ROW AND ADD ONLY IF MORE RECENT!!!!!!!!!!!!!!!!!!!!!!!!
                            for(Map.Entry<Class<?>, ArrayList<?>> entry : data.entrySet()) {
                                if(UserPrivate.class.isAssignableFrom(entry.getKey())) {
                                    //TODO: IF CONNECTED USER IS IN THE LIST, IGNORE
                                    DatabaseSynchronizer.feedWithUserPrivate((ArrayList<UserPrivate>)entry.getValue());
                                } else if(User.class.isAssignableFrom(entry.getKey())) {
                                    //TODO: IF CONNECTED USER IS IN THE LIST, IGNORE
                                    DatabaseSynchronizer.feedWithUser((ArrayList<User>)entry.getValue());
                                } else if(Conversation.class.isAssignableFrom(entry.getKey())) {
                                    DatabaseSynchronizer.feedWithConversation((ArrayList<Conversation>)entry.getValue());
                                } else if(Message.class.isAssignableFrom(entry.getKey())) {
                                    DatabaseSynchronizer.feedWithMessage((ArrayList<Message>)entry.getValue());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        DatabaseSynchronizer.synchronize(); // synchronize the database with the fetched queue of data
                    }
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
                            UserPrivate u = finalDisc.getNewUser();
                            if (u != null) {
                                if(!usersConnected.contains(u.getUUID())) {
                                    if (!user_mngr.isUserExist(u.getUUID())) {
                                        user_mngr.addExistingUser(u.getUUID(), u.getLogin(), u.getPassword(), u.getMail(), u.getLastIp());
                                    } else {
                                        user_mngr.updateLogin(u.getUUID(), u.getLogin());
                                        user_mngr.updateHashedPassword(u.getUUID(), u.getPassword());
                                        user_mngr.updateMail(u.getUUID(), u.getMail());
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

    /**
     * ConversationService instance getter
     * @author Romain MONIER
     * @return
     */
    public static ConversationService getConversationService() {
        return convService;
    }

    /**
     * Boolean getter to check if we need to continue to run the daemons
     * @author Romain MONIER
     * @return
     */
    private static boolean keepDaemonAlive() {
        return !kill;
    }

    /**
     * Stop all daemons.
     * Probably a bad idea to use this though as it permanently interrupts the daemons for this app instance
     * @author Romain MONIER
     */
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
        if(ENABLE_SYNCHRONIZER) {
            synchronized (synchronizerServerDaemon) {
                synchronizerServerDaemon.interrupt();
            }
            synchronized (synchronizerClientDaemon) {
                synchronizerClientDaemon.interrupt();
            }
        }
    }

    /**
     * Start all daemons
     * @author Romain MONIER
     */
    public static void start() {
        daemon.start();
        helloDaemon.start();
        discoveryDaemon.start();
        conversationServerDaemon.start();
        if(ENABLE_SYNCHRONIZER) {
            synchronizerServerDaemon.start();
            synchronizerClientDaemon.start();
        }
    }

    /**
     * Notify the main daemon to unlock the wait
     * @author Romain MONIER
     */
    public static void notifyThread() {
        synchronized (daemon) {
            daemon.notify();
        }
    }

    /**
     * Notify the discovery daemon to unlock the wait
     * @author Romain MONIER
     */
    public static void notifyDiscoveryDaemon() {
        synchronized (discoveryDaemon) {
            discoveryDaemon.notify();
        }
    }

    /**
     * Notify the conversation daemon to unlock the wait
     * @author Romain MONIER
     */
    public static void notifyConversationDaemon() {
        synchronized (conversationServerDaemon) {
            conversationServerDaemon.notify();
        }
    }

    /**
     * Notify the synchronizer client daemon to unlock the wait
     * @author Romain MONIER
     */
    public static void notifySynchronizerClientDaemon() {
        if(ENABLE_SYNCHRONIZER) {
            synchronized (synchronizerClientDaemon) {
                synchronizerClientDaemon.notify();
            }
        }
    }

    /**
     * Notify the synchronizer server daemon to unlock the wait
     * @author Romain MONIER
     */
    public static void notifySynchronizerServerDaemon() {
        if(ENABLE_SYNCHRONIZER) {
            synchronized (synchronizerServerDaemon) {
                synchronizerServerDaemon.notify();
            }
        }
    }

    /**
     * Wait 5 seconds and display the remaining time (useful for connection retries)
     * @author Romain MONIER
     */
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
