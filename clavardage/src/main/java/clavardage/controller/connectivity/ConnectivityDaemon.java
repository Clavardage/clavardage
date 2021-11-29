package clavardage.controller.connectivity;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.model.objects.Conversation;
import clavardage.model.objects.User;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Handle the network connectivity activities and use infos to update database and program state
 */
public class ConnectivityDaemon {

    private static final Thread daemon, discoveryDaemon, conversationServerDaemon;
    private static boolean kill;

    static {
        kill = false;

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
            disc.sendHello(); // indicate we are alive
        } catch (Exception e) {
            e.printStackTrace();
        }

        DiscoveryService finalDisc = disc;
        discoveryDaemon = new Thread(() -> {
            while(keepDaemonAlive()) {
                try {
                    finalDisc.listen(); // wait for new connected users
                    System.out.println(finalDisc.getNewUser().getUUID());
                } catch(Exception e) {
                    e.printStackTrace();
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
        });

        /* CONVERSATION SERVICE */

        ConversationService conv = null;
        try {
            conv = new ConversationService();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ConversationService finalConv = conv;
        conversationServerDaemon = new Thread(() -> {
            while(keepDaemonAlive()) {
                try {
                    finalConv.listen();
                } catch(Exception e) {
                    e.printStackTrace();
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
        });

        // tests
        if(!Clavardage.machine1) {
            try {
                AuthOperations.connectUser("mail_1@clav.com", "pass_1");
                ArrayList<User> testarr = new ArrayList<User>();
                testarr.add(new User(UUID.randomUUID(), "test1", InetAddress.getByName(NetworkConnector.getLocalAddress())));
                testarr.add(new User(UUID.randomUUID(), "test2", InetAddress.getByName(NetworkConnector.getLocalAddress())));
                conv.openConversation(new Conversation(1, "test", LocalDateTime.now(), testarr));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean keepDaemonAlive() {
        return !kill;
    }

    public static void stop() {
        kill = true;
        notifyThread();
        synchronized (discoveryDaemon) {
            discoveryDaemon.interrupt();
        }
        synchronized (conversationServerDaemon) {
            conversationServerDaemon.interrupt();
        }
    }

    public static void start() {
        daemon.start();
        discoveryDaemon.start();
        conversationServerDaemon.start();
    }

    public static void notifyThread() {
        synchronized (daemon) {
            daemon.notify();
        }
    }
}
