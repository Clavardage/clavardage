package clavardage.controller.gui;

import static clavardage.controller.authentification.AuthOperations.cancelIfNotConnected;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.connectivity.ConnectivityDaemon;
import clavardage.controller.connectivity.ConversationService;
import clavardage.controller.connectivity.NetworkConnector;
import clavardage.model.exceptions.ConversationDoesNotExistException;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.managers.ConversationManager;
import clavardage.model.managers.MessageManager;
import clavardage.model.managers.UserManager;
import clavardage.model.objects.Conversation;
import clavardage.model.objects.Message;
import clavardage.model.objects.User;
import clavardage.view.Application;
import clavardage.view.Application.Destinataire;
import clavardage.view.Application.TypeBuble;
import clavardage.view.listener.ActionConnectivity;
import clavardage.view.listener.ActionSendMessage;
import clavardage.view.listener.MouseCloseConversation;
import clavardage.view.listener.MouseOpenConversation;
import clavardage.view.main.MessageBuble;
import clavardage.view.mystyle.MyDate;

/**
 * GUI Controller linking Model and View
 * @author Romain MONIER, Célestine PAILLÉ
 */
public class MainGUI {

    private static JFrame app = null;

    /**
     * Create and display Application View
     * @author Romain MONIER, Célestine PAILLÉ
     * @return the application frame
     * @throws IOException
     */
    public static JFrame createGUI() throws IOException {
        app = new Application("Clavardage", new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/icons/icon.png"))));
        app.setVisible(true);
        return app;
    }

    /**
     * Used by the server listener (ConnectivityDaemon)
     * @author Romain MONIER, Célestine PAILLÉ
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
            MouseOpenConversation.openConversation(uDest.getLogin(), uDest.getUUID(), Destinataire.User);
            // call pastille bleu func
            Application.getMessageWindow().findMyDestinataireJPanel(uDest.getUUID()).openConversationInList();
        } else { // close conversation
            ConnectivityDaemon.getConversationService().close(c);
        }
    }

    /**
     * Used by Connectivity daemon
     * @author Romain MONIER, Célestine PAILLÉ
     * @param u
     * @param connected
     */
    public static void setUserState(User u, boolean connected) {
    	if (!(Application.getMessageWindow() == null)) {	
    		//handle user state in GUI
    		System.out.println("Log: " + u.getLogin() + " [" + u.getLastIp() + "] " + (connected ? "connected!" : "disconnected!"));
    		try {
    			if (AuthOperations.isUserConnected()) {
    				//set icon and connectivity
        			ActionConnectivity.setConnected(u.getUUID(), connected);

        			//replace (or add if new) user in the list
        			ActionConnectivity.updateListUser(u,connected);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }

    /**
     * Retrieve all usernames in database
     * @author Romain MONIER, Célestine PAILLÉ
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
     * @author Romain MONIER, Célestine PAILLÉ
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
     * Edit the username
     * @author Romain MONIER, Célestine PAILLÉ
     * @param newUsername
     * @throws Exception
     */
    public static void editUsername(String newUsername) throws Exception {
        AuthOperations.editUsername(newUsername);
    }

    /**
     * Open conversation with the destination UUID of the user
     * @author Romain MONIER, Célestine PAILLÉ
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
        
        //call pastille bleu func
        Application.getMessageWindow().findMyDestinataireJPanel(u2.getUUID()).openConversationInList();

    }

    /**
     * Triggered when a conversation is closed
     * @author Romain MONIER, Célestine PAILLÉ
     * @param uuidDestination
     */
    public static void conversationClosed(UUID uuidDestination) {
    	try {
//        	//enlever pastille bleue
//			Application.getMessageWindow().findMyDestinataireJPanel(uuidDestination).closeConversationInList();
	    	// handle GUI conversation
			MouseCloseConversation.closeConversation(uuidDestination);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Close a conversation (for user to user conversations only)
     * @author Romain MONIER, Célestine PAILLÉ
     * @param uuidDestination the destination user UUID
     * @throws Exception
     */
    public static void closeConversation(UUID uuidDestination) throws Exception {
        ConnectivityDaemon.getConversationService().close((new ConversationManager()).getConversationByUUID(getConversationUUIDByTwoUsersUUID(AuthOperations.getConnectedUser().getUUID(), uuidDestination)));
    }

    /**
     * Get a conversation UUID by the two users UUID
     * @author Romain MONIER, Célestine PAILLÉ
     * @param u1
     * @param u2
     * @return
     * @throws Exception
     */
    public static UUID getConversationUUIDByTwoUsersUUID(UUID u1, UUID u2) throws Exception {
        ConversationManager cm = new ConversationManager();
        UserManager um = new UserManager();
        return cm.getConversationByTwoUsers(um.getUserByUUID(u1), um.getUserByUUID(u2)).getUUID();
    }

    /**
     * Send a message in the conversation
     * @author Romain MONIER, Célestine PAILLÉ
     * @param uuid
     * @param message
     * @throws Exception
     */
    public static void sendMessageInConversation(UUID uuid, String message) throws Exception {
        Conversation conv = (new ConversationManager()).getConversationByUUID(uuid);
        // save in DB
        Message msgObj = (new MessageManager()).saveNewMessage(message, AuthOperations.getConnectedUser(), conv);
        // send it if saved
        ConnectivityDaemon.getConversationService().sendMessageToConversation(conv, msgObj);
    }

    /**
     * Used by Conversation Service
     * @author Romain MONIER, Célestine PAILLÉ
     * @param c
     * @param msg
     * @throws Exception 
     */
    public static void addNewMessage(Conversation c, Message msg) throws Exception {
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
    	ActionSendMessage.receiveMessage(msg.getText(), uDest.getUUID());
    }

    /**
     * Proxy between Model Messages (with LocalDateTime) and View Messages (with MyDate)
     * @author Romain MONIER, Célestine PAILLÉ
     * @param uuidConversation
     * @param num
     * @param page
     * @return array of MessageBubble
     * @throws Exception
     */
    public static ArrayList<MessageBuble> getMessagesFrom(UUID uuidConversation, int num, int page) throws Exception {
        ArrayList<MessageBuble> msgs = new ArrayList<>();
        ConversationManager cm = new ConversationManager();
        MessageManager mm = new MessageManager();
        Conversation c = cm.getConversationByUUID(uuidConversation);

        ArrayList<Message> allMsgs;

        if(num > -1) {
            if(page > -1) {
                allMsgs = mm.getLastMessagesFromConversation(c, num, page);
            } else {
                allMsgs = mm.getLastMessagesFromConversation(c, num);
            }
        } else {
            allMsgs = mm.getAllMessagesFromConversation(c);
        }

        for (Message m : allMsgs) {
            msgs.add(new MessageBuble(
                            (m.getUser().getUUID().equals(AuthOperations.getConnectedUser().getUUID()) ? TypeBuble.MINE : TypeBuble.THEIR),
                            m.getText(),
                            new MyDate(m.getDateCreated().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    )
            );
        }

        return msgs;
    }

    /**
     * Shortcut for messages getter
     * @author Romain MONIER, Célestine PAILLÉ
     * @param uuidConversation
     * @param num
     * @return
     * @throws Exception
     */
    public static ArrayList<MessageBuble> getMessagesFrom(UUID uuidConversation, int num) throws Exception {
        return getMessagesFrom(uuidConversation, num, -1);
    }

    /**
     * Shortcut for messages getter
     * @author Romain MONIER, Célestine PAILLÉ
     * @param uuidConversation
     * @return
     * @throws Exception
     */
    public static ArrayList<MessageBuble> getAllMessagesFrom(UUID uuidConversation) throws Exception {
        return getMessagesFrom(uuidConversation, -1);
    }

    /**
     * Create a new user
     * @author Romain MONIER, Célestine PAILLÉ
     * @param username
     * @param mail
     * @param pass_one
     * @param pass_two
     * @throws Exception
     */
    public static void createNewUser(String username, String mail, String pass_one, String pass_two) throws Exception {
        if(!pass_one.equals(pass_two)) {
            throw new Exception("Passwords don't match!");
        }
        (new UserManager()).createUser(username, pass_one, mail, InetAddress.getByName(NetworkConnector.getLocalAddresses().get(0)));
    }
}