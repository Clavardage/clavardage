package clavardage.controller.connectivity;

import clavardage.controller.authentification.AuthOperations;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.objects.Conversation;

import java.io.IOException;

public class ConversationService implements Activity {

    private final TCPConnector tcpServer, tcpClient;

    public ConversationService() throws Exception {
        super();
        tcpServer = new TCPConnector() /*{
            @Override
            public void converationHandler() {
                handleConversation();
            }
        }*/;
        tcpClient = new TCPConnector();
    }

    /**
     * Wait for TCP connections and handle clients
     */
    public void listen() throws IOException {
        handleClient(tcpServer.waitForClient());
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
            handleClient(tcpClient.askServer(conv, conv.getListUsers().get(0).getLastIp().getHostAddress()));
        } else { // multicast for groups
            System.err.println("NOT IMPLEMENTED");
        }
    }

    public void handleClient(Thread cli) {
        cli.start();
    }
/*
    public void handleConversation() {
        try {

            Object obj = getPacketData();
            PrintWriter out = new PrintWriter(getLink().getOutputStream(), true);
            out.println("coucou je suis le serveur :)");
        } catch (Exception e) {
            System.err.println("Error");
        }

    }
*/
    @Override
    public void done() { }
}
