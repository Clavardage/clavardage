package clavardage.controller.connectivity;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.objects.Conversation;
import clavardage.model.objects.Message;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ConversationService implements Activity {

    private final TCPConnector tcpServer, tcpClient;
    private HashMap<Conversation, Boolean> lockList;

    public ConversationService() throws Exception {
        super();
        lockList = new HashMap<Conversation, Boolean>();
        tcpServer = new TCPConnector(Clavardage.machine1 ? 4342 : 4343) {
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
        tcpClient = new TCPConnector(Clavardage.machine1 ? 4343 : 4342) {
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
            lockList.put(currentConv, true);
            System.out.println("Waiting for events for Conversation `" + currentConv.getName() + "`");
            while(lockList.get(currentConv)) {
                waitForConversationEvent(r, currentConv);
            }
        } catch (Exception e) {
            System.err.println("Conversation error: " + e);
        } finally {
            r.close();
        }
    }

    /**
     * Wait for any Conversation event possible : reception of message (getPacketData), user sending message or closing the conversation (unlock)
     * @param r
     * @throws Exception
     */
    private void waitForConversationEvent(RunnableTCPThread r, Conversation currentConv) throws Exception {
        if(true)
            return;
        //FIXME
        Thread fatherThread = Thread.currentThread();
        AtomicReference<Message> msgReceived = new AtomicReference<Message>(null);
        AtomicBoolean msgToSend = new AtomicBoolean(false);
        AtomicBoolean closeSignal = new AtomicBoolean(false);

        /* WAITING FOR NEW MESSAGE */

        Thread newMsg = new Thread(() -> {
            Object obj = null;
            try {
                obj = r.getPacketData();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                try {
                    r.close(); // if error, close the socket
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if(Objects.nonNull(obj) && obj instanceof Message) {
                msgReceived.set((Message) obj);
                fatherThread.notify();
            } else {
                System.err.println("Message data error");
                try {
                    throw new Exception("Message data error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /* WAITING FOR MESSAGE TO SEND */

        Thread sendMsg = new Thread(() -> {
            if(true) return; //TODO
            msgToSend.set(true);
            fatherThread.notify();
        });

        /* WAITING FOR CLOSING SIGNAL */

        Thread closeConv = new Thread(() -> {
            if(true) return; //TODO
            closeSignal.set(true);
            fatherThread.notify();
        });

        closeConv.start();
        sendMsg.start();
        newMsg.start();

        fatherThread.wait();

        /* CANCEL ALL THREADS WHEN ONE HAS NOTIFIED PARENT */

        newMsg.interrupt();
        sendMsg.interrupt();
        closeConv.interrupt();

        /* HANDLE THE EVENT */

        if(closeSignal.get()) {
            close(currentConv);
        } else if(msgToSend.get()) {

        } else if(Objects.nonNull(msgReceived.get())) {

        } else {
            throw new Exception("Error");
        }
    }

    /**
     * Close conversation (unlock the handleConversation instance, hence closing the socket)
     * @param c
     */
    public void close(Conversation c) {
        lockList.put(c, false);
    }

    @Override
    public void done() { }
}
