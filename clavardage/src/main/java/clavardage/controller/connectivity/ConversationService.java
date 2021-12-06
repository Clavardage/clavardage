package clavardage.controller.connectivity;

import clavardage.controller.authentification.AuthOperations;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.objects.Conversation;
import clavardage.model.objects.Message;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class ConversationService implements Activity {

    private final TCPConnector tcpServer, tcpClient;
    private final HashMap<UUID, RunnableTCPThread> convList;

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
                        done();
                    } else {
                        System.err.println("Conversation data error");
                        throw new Exception("Conversation data error");
                    }
                    handleConversation(r, currentConv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        tcpClient = new TCPConnector() {
            @Override
            public final void conversationHandler(RunnableTCPThread r, Conversation currentConv) {
                try {
                    r.sendPacket(currentConv);
                    handleConversation(r, currentConv);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Wait for TCP connections and handle clients
     */
    public void listen() throws IOException {
        tcpServer.waitForClient().start();
    }

    /**
     * Try to open a TCP connection with server
     * @param conv
     * @throws IOException
     */
    public void openConversation(Conversation conv) throws IOException {
        if(conv.isWithOneUserOnly()) { // TCP for conversation with 1 user
            conv.getListUsers().removeIf(u -> { // filter to get the other user ip
                try {
                    return u.getUUID() == AuthOperations.getConnectedUser().getUUID();
                } catch (UserNotConnectedException e) {
                    e.printStackTrace();
                }
                return false;
            });
            tcpClient.askServer(conv, conv.getListUsers().get(0).getLastIp().getHostAddress()).start();
        } else { // multicast for groups
            System.err.println("NOT IMPLEMENTED");
        }
    }

    /**
     * Conversation thread handled there, main loop
     * @param r
     */
    public void handleConversation(RunnableTCPThread r, Conversation currentConv) throws IOException {
        try {
            convList.put(currentConv.getUUID(), r);
            System.out.println("Waiting for events for Conversation `" + currentConv.getName() + "`");
            while(true) {
                Message msg = waitForConversationEvent(r);

                /* HANDLE MESSAGE */
                System.out.println("Test: from: " + msg.getUser().getLogin() + " msg = " + msg.getText());
                //TODO
            }
        } catch (Exception e) {
            System.err.println("Log: Conversation error: " + e);
        } finally {
            try {
                r.close();
                System.out.println("Log: Conversation `" + currentConv.getName() + "` closed!");
            } catch(Exception e) {
                System.err.println("Unable to close conversation, maybe already closed?");
            }
        }
    }

    /**
     * Wait for Conversation event : reception of message (getPacketData) OR OTHER DATA (TODO)
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
            e.printStackTrace();
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
     * @param c
     */
    public void close(Conversation c) {
        try {
            convList.get(c.getUUID()).close();
            convList.remove(c.getUUID());
            System.out.println("Log: Conversation `" + c.getName() + "` closed!");
        } catch (IOException e) {
            System.err.println("Unable to close conversation, maybe already closed?");
        }
    }

    /**
     *
     * @param c
     * @param m
     */
    public void sendMessageToConversation(Conversation c, Message m) throws Exception {
        try {
            convList.get(c.getUUID()).sendPacket(m);
        } catch (Exception e) {
            throw new Exception("Unable to send message : " + e);
        }
    }

    @Override
    public void done() { }
}