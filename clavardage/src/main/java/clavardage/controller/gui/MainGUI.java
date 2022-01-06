package clavardage.controller.gui;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.*;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.connectivity.ConnectivityDaemon;
import clavardage.controller.connectivity.ConversationService;
import clavardage.model.exceptions.ConversationDoesNotExistException;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.managers.ConversationManager;
import clavardage.model.managers.UserManager;
import clavardage.model.objects.Conversation;
import clavardage.model.objects.Message;
import clavardage.model.objects.User;
import clavardage.view.main.Application;
import clavardage.view.main.MessageWindow;

import static clavardage.controller.authentification.AuthOperations.cancelIfNotConnected;

/**
 *
 */
public class MainGUI {

    private static JFrame app = null;

    /**
     * Create and display Application View
     * @return the application frame
     */
    public static JFrame createGUI() throws IOException {
        app = new Application("Clavardage", new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/icons/icon.png"))));
        app.setVisible(true);
        return app;
    }

    /**
     * Used by the server listener (ConnectivityDaemon)
     * @param c
     * @throws Exception
     */
    public static void askForConversationOpening(Conversation c) throws Exception {
        User uDest = null;
        if(c.isWithOneUserOnly()) { // 1 to 1
            for(User u : c.getListUsers()) {
                if(u.getUUID().equals(AuthOperations.getConnectedUser().getUUID())) {
                    continue;
                }
                uDest = u;
            }
        } else { // Groups
            System.err.println("NOT IMPLEMENTED");
        }
        System.out.println("Log: Asking for conversation opening...");
        int choice = JOptionPane.showConfirmDialog(app,
                uDest.getLogin() + " [" + uDest.getLastIp() + "] wants to talk with you! Accept?",
                "Message Request",
                JOptionPane.YES_NO_OPTION);

        if(choice == JOptionPane.YES_OPTION) {
            // handle GUI conversation
            Application.getMessageWindow().openConversation(uDest.getLogin(), uDest.getUUID(), MessageWindow.Destinataire.User);
            //ConnectivityDaemon.getConversationService().sendMessageToConversation(c, new Message(UUID.randomUUID(), "Bien recu bro", new User(UUID.randomUUID(), "test2", InetAddress.getByName("127.0.0.2")), c));
        } else { // close conversation
            ConnectivityDaemon.getConversationService().close(c);
        }
    }

    /**
     * Used by Connectivity daemon
     * @param u
     * @param connected
     */
    public static void setUserState(User u, boolean connected) {
        // TODO: handle user state in GUI
    	System.out.println("Log: " + u.getLogin() + " [" + u.getLastIp() + "] " + (connected ? "connected!" : "disconnected!"));
    	try {
    		if (connected) {

    			Application.getMessageWindow().setIconConnected(u.getUUID());
    		} else {
    			Application.getMessageWindow().setIconDisconnected(u.getUUID());
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Retrieve all usernames in database
     * @return
     * @throws UserNotConnectedException
     */
    public static ArrayList<String> getAllUsernamesInDatabase() throws UserNotConnectedException {
        cancelIfNotConnected();
        ArrayList<String> names = new ArrayList<String>();
        try {
            (new UserManager()).getAllUsers().forEach((u) -> names.add(u.getLogin()));
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }

        return names;
    }

    /**
     * Retrieve all users in database
     * @return
     * @throws UserNotConnectedException
     */
    public static ArrayList<User> getAllUsersInDatabase() throws UserNotConnectedException {
        cancelIfNotConnected();
        ArrayList<User> names = new ArrayList<User>();
        try {
            names.addAll((new UserManager()).getAllUsers());
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }

        return names;
    }

    /**
     * Open conversation with the destination UUID of the user
     * @param uuidDestination
     * @throws Exception
     */
    public static void openConversation(UUID uuidDestination) throws Exception {
        cancelIfNotConnected();

        /* CREATE CONVERSATION */

        User u1 = AuthOperations.getConnectedUser();
        User u2 = (new UserManager()).getUserByUUID(uuidDestination);
        ConversationManager cm = new ConversationManager();
        Conversation conv = null;
        try {
            conv = cm.getConversationByTwoUsers(u1, u2);
        } catch (ConversationDoesNotExistException e) {
            // Conversation will be created there
            try {
                conv = cm.createConversation(u1.getLogin() + " & " + u2.getLogin(), LocalDateTime.now(), new ArrayList<User>(Arrays.asList(u1, u2)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* SEND NETWORK REQUEST */

        ConversationService conv_serv = ConnectivityDaemon.getConversationService();
        conv_serv.openConversation(conv);
    }

    public static UUID getConversationUUIDByTwoUsersUUID(UUID u1, UUID u2) throws Exception {
        ConversationManager cm = new ConversationManager();
        UserManager um = new UserManager();
        return cm.getConversationByTwoUsers(um.getUserByUUID(u1), um.getUserByUUID(u2)).getUUID();
    }

    public static void sendMessageInConversation(UUID uuid, String message) throws Exception {
        Conversation conv = (new ConversationManager()).getConversationByUUID(uuid);
        Message msgObj = new Message(UUID.randomUUID(), message, AuthOperations.getConnectedUser(), conv, LocalDateTime.now());
        // TODO: save in DB
        // send it
        ConnectivityDaemon.getConversationService().sendMessageToConversation(conv, msgObj);
    }

    /**
     * Used by Conversation Service
     * @param c
     * @param msg
     * @throws UserNotConnectedException 
     */
    public static void addNewMessage(Conversation c, Message msg) throws UserNotConnectedException {
        // handle message display in GUI
    	User uDest = null;
        if(c.isWithOneUserOnly()) { // 1 to 1
            for(User u : c.getListUsers()) {
                if(u.getUUID().equals(AuthOperations.getConnectedUser().getUUID())) {
                    continue;
                }
                uDest = u;
            }
        } else { // Groups
            System.err.println("NOT IMPLEMENTED");
        }
    	Application.getMessageWindow().receiveMessage(msg.getText(), uDest.getUUID());
    }

    public static void testConversation2() throws Exception {
        //test for openConv
        if(!Clavardage.machine1) {
            AuthOperations.connectUser("mail_2@clav.com", "pass_2");
            /* CREATE CONVERSATION */
            User u2 = new User(UUID.randomUUID(), "test1", InetAddress.getByName("127.0.0.1"));
            User u1 = new User(UUID.randomUUID(), "test2", InetAddress.getByName("127.0.0.2"));
            ConversationManager cm = new ConversationManager();
            Conversation conv = null;
            try {
                conv = cm.getConversationByTwoUsers(u1, u2);
            } catch (ConversationDoesNotExistException e) {
                // Conversation will be created there
                try {
                    conv = cm.createConversation(u1.getLogin() + " & " + u2.getLogin(), LocalDateTime.now(), new ArrayList<User>(Arrays.asList(u1, u2)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            /* SEND NETWORK REQUEST */

            ConversationService conv_serv = ConnectivityDaemon.getConversationService();
            conv_serv.openConversation(conv);
        } else {
            AuthOperations.connectUser("mail_1@clav.com", "pass_1");
        }
    }

    public static void testConversation() {
        // tests
        ConversationService conv = ConnectivityDaemon.getConversationService();
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
                        conv.openConversation(c);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        conv.sendMessageToConversation(c, new Message(UUID.randomUUID(), "Nouveau msg test !!!!!!! \\n\\tblablou", alice, c, LocalDateTime.now()));
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
                    conv.sendMessageToConversation(c, new Message(UUID.randomUUID(), "Bien recu bro", bob, c, LocalDateTime.now()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}