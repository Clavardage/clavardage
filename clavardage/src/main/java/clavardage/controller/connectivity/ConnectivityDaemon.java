package clavardage.controller.connectivity;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.model.objects.Conversation;
import clavardage.model.objects.Message;
import clavardage.model.objects.User;

import java.net.BindException;
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
                } catch (BindException e) {
                    e.printStackTrace();
                    waitForRetryConnection();
                } catch(Exception e) {
                    e.printStackTrace();
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
                } catch (BindException e) {
                    e.printStackTrace();
                    waitForRetryConnection();
                } catch(Exception e) {
                    System.err.println("Server error: " + e);
                    System.out.println("Log: Relaunching server...");
                }
            }
        });

        // tests
        ConversationService finalConv1 = conv;
        new Thread(() -> {
            try {
                ArrayList<User> testarr = new ArrayList<User>();
                User alice = new User(UUID.randomUUID(), "test1", InetAddress.getByName("127.0.0.1"));
                User bob = new User(UUID.randomUUID(), "test2", InetAddress.getByName("127.0.0.2"));
                testarr.add(alice);
                testarr.add(bob);
                Conversation c = new Conversation(UUID.fromString("7275cad1-d551-4e84-9eb3-fc2dc5812f32"), "test", LocalDateTime.of(12,12,1,1,1), testarr);
                if(!Clavardage.machine1) {
                    try {
                        AuthOperations.connectUser("mail_1@clav.com", "pass_1");
                        finalConv1.openConversation(c);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finalConv1.sendMessageToConversation(c, new Message(1, "Nouveau msg test !!!!!!! \\n\\tblablou", alice, c));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    AuthOperations.connectUser("mail_2@clav.com", "pass_2");
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finalConv1.sendMessageToConversation(c, new Message(2, "Bien recu bro", bob, c));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        })/*.start()*/;
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
