package clavardage.controller.connectivity;

import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.gui.MainGUI;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.managers.ConversationManager;
import clavardage.model.managers.MessageManager;
import clavardage.model.objects.Conversation;
import clavardage.model.objects.Message;
import clavardage.model.objects.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Conversation Service: uses TCP Connectors to handle conversations and processes database actions linked to it
 * @author Romain MONIER
 */
public class ConversationService implements Activity {

    private final TCPConnector tcpServer, tcpClient;
    private final HashMap<UUID, RunnableTCPThread> convList;
    private Conversation newConversation;

    /**
     * Constructor overriding conversation handlers in TCP Connectors (client and server) on default port
     * @author Romain MONIER
     * @throws Exception
     */
    public ConversationService() throws Exception {
        super();
        convList = new HashMap<UUID, RunnableTCPThread>();
        tcpServer = new TCPConnector() {
            @Override
            public final void conversationHandler(RunnableTCPThread r, Conversation currentConv) {
                try {
                    // Should return a Conversation DTO (with two users, this one and the other)
                    // corresponding to the current thread
                    Object obj = r.getPacketData();
                    if(Objects.nonNull(obj) && obj instanceof Conversation) {
                        currentConv = (Conversation)obj;
                        newConversation = currentConv;
                        done(); // notify main daemon
                    } else {
                        System.err.println("Conversation data error");
                        done(); // notify main daemon
                        throw new Exception("Conversation data error");
                    }
                    handleConversation(r, currentConv);
                } catch (Exception ignore) { }
            }
        };
        tcpClient = new TCPConnector() {
            @Override
            public final void conversationHandler(RunnableTCPThread r, Conversation currentConv) {
                try {
                    r.sendPacket(currentConv);
                    handleConversation(r, currentConv);
                } catch (Exception ignore) { }
            }
        };
    }

    /**
     * Wait for TCP connections and handle clients
     * @author Romain MONIER
     * @throws IOException
     */
    public void listen() throws IOException {
        tcpServer.waitForClient().start();
    }

    /**
     * Try to open a TCP connection with server
     * @author Romain MONIER
     * @param conv
     * @throws IOException
     * @throws UserNotConnectedException
     */
    public void openConversation(Conversation conv) throws IOException, UserNotConnectedException {
        User uDest = null;
        if(conv.isWithOneUserOnly()) { // TCP for conversation with 1 user
            for(User u : conv.getListUsers()) { // filter to get the other user ip
                if(u.getUUID() == AuthOperations.getConnectedUser().getUUID())
                    continue;
                uDest = u;
            }
            tcpClient.askServer(conv, uDest.getLastIp().getHostAddress()).start();
        } else { // multicast for groups
            System.err.println("NOT IMPLEMENTED");
        }
    }

    /**
     * Conversation thread handled there, main loop
     * @author Romain MONIER
     * @param r
     * @param currentConv
     * @throws IOException
     */
    public void handleConversation(RunnableTCPThread r, Conversation currentConv) throws IOException {
        try {
            convList.put(currentConv.getUUID(), r);
            System.out.println("Log: Waiting for events for Conversation `" + currentConv.getName() + "`");
            while(true) {
                Message msg = waitForConversationEvent(r);

                /* HANDLE MESSAGE */
                
                // save in DB
                (new MessageManager()).saveExistingMessage(msg.getUUID(), msg.getText(), msg.getUser(), msg.getConversation(), msg.getDateCreated());
                // send it to GUI
                MainGUI.addNewMessage(currentConv, msg);
            }
        } catch (Exception e) {
            System.err.println("Log: Conversation error: " + e);
        } finally {
            try {
                r.close();
                close(currentConv);
            } catch(Exception e) {
                System.err.println("Unable to close conversation, maybe already closed?");
            }
        }
    }

    /**
     * Wait for Conversation event : reception of message (getPacketData) OR OTHER DATA (TODO)
     * @author Romain MONIER
     * @param r
     * @return New Message
     * @throws Exception
     */
    private Message waitForConversationEvent(RunnableTCPThread r) throws Exception {

        /* WAITING FOR NEW MESSAGE */

        Message msgReceived = null;
        Object obj = null;

        try {
            obj = r.getPacketData();
        } catch (IOException | ClassNotFoundException e) {
            try {
                r.close(); // if error, close the socket
            } catch (IOException ex) {
                //ex.printStackTrace();
            }
            throw new Exception("Message reception error");
        }

        if(Objects.nonNull(obj) && obj instanceof Message) {
            msgReceived = (Message) obj;
        } else {
            System.err.println("Message data error");
            throw new Exception("Message data error");
        }

        return msgReceived;
    }

    /**
     * Close conversation (unlock the handleConversation instance by closing the socket)
     * @author Romain MONIER
     * @param c
     * @throws UserNotConnectedException 
     */
    public void close(Conversation c) throws UserNotConnectedException {
        try {
            convList.get(c.getUUID()).close();
            convList.remove(c.getUUID());
            System.out.println("Log: Conversation `" + c.getName() + "` closed!");
        } catch (IOException e) {
            System.err.println("Unable to close conversation, maybe already closed?");
        } finally {
        	User uDest = null;
	        if(c.isWithOneUserOnly()) {
	            for(User u : c.getListUsers()) {
	                if(u.getUUID().equals(AuthOperations.getConnectedUser().getUUID()))
	                    continue;
	                uDest = u;
	            }
	            MainGUI.conversationClosed(uDest.getUUID());
	        }
        }
    }

    /**
     * Close all opened conversations
     * @author Romain MONIER
     */
    public void closeAllConversations() {
        for(Map.Entry<UUID, RunnableTCPThread> c : convList.entrySet()) {
            try {
                close((new ConversationManager()).getConversationByUUID(c.getKey()));
            } catch (Exception ignore) { }
        }
    }

    /**
     * Send Message
     * @author Romain MONIER
     * @param c
     * @param m
     * @throws Exception
     */
    public void sendMessageToConversation(Conversation c, Message m) throws Exception {
        try {
            convList.get(c.getUUID()).sendPacket(m);
        } catch (Exception e) {
            throw new Exception("Unable to send message : " + e);
        }
    }

    /**
     * Conversation data retrieved getter
     * @author Romain MONIER
     * @return
     */
    public Conversation getNewConversation() {
        return newConversation;
    }

    /**
     * Notify the conversation daemon when actions finished
     * @author Romain MONIER
     */
    @Override
    public void done() {
        ConnectivityDaemon.notifyConversationDaemon();
    }
}
