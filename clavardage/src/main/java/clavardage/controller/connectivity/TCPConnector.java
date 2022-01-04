package clavardage.controller.connectivity;

import clavardage.model.objects.Conversation;

import java.io.*;
import java.net.*;
import java.util.Objects;

public class TCPConnector extends NetworkConnector implements ConversationActivity {
    private final int DEFAULT_TCP_PORT = 4342;
    protected final int MAX_SERVER_REQUESTS = 10;

    private final int FORCED_TCP_PORT;
    ServerSocket servSocket;

    public TCPConnector() throws Exception {
        super();
        FORCED_TCP_PORT = DEFAULT_TCP_PORT;
    }

    public TCPConnector(int port) throws Exception {
        super();
        FORCED_TCP_PORT = port;
    }

    public Thread askServer(Conversation c, String ip) throws IOException {
        System.out.println("Log: TCP TRYING: " + ip + ":" + FORCED_TCP_PORT);
        Socket link = new Socket(InetAddress.getByName(ip), FORCED_TCP_PORT);
        System.out.println("Log: TCP asking to " + ip + ":" + FORCED_TCP_PORT);
        return new Thread(new RunnableTCPThread() {
            @Override
            public void run() {
                System.out.println("Log: Connected to server, delegating to new thread.");
                conversationHandler(this, c);
            }

            public final void close() throws IOException {
                link.close();
            }

            public final Socket getLink() {
                return link;
            }

            public final void sendPacket(Serializable obj) throws IOException {
                ObjectOutputStream out = new ObjectOutputStream(link.getOutputStream());
                out.writeObject(obj);
                out.flush();
            }

            public final Object getPacketData() throws IOException, ClassNotFoundException {
                ObjectInputStream objectInputStream = new ObjectInputStream(link.getInputStream());
                Object obj = objectInputStream.readObject();
                return getDTO(obj);
            }
        });
    }

    private Thread getClient() throws IOException {
        if(Objects.isNull(servSocket) || servSocket.isClosed()) {
            servSocket = new ServerSocket(FORCED_TCP_PORT, MAX_SERVER_REQUESTS);
            System.out.println("Log: TCP accepting on *:" + FORCED_TCP_PORT + "...");
        } else {
            System.out.println("Log: Continuing TCP accepting on *:" + FORCED_TCP_PORT + "...");
        }
        Socket link = servSocket.accept();
        return new Thread(new RunnableTCPThread() {
            @Override
            public void run() {
                System.out.println("Log: New client, delegating to new thread.");
                conversationHandler(this, null);
            }

            public final void close() throws IOException {
                link.close();
                servSocket.close();
            }

            public final Socket getLink() {
                return link;
            }

            public final void sendPacket(Serializable obj) throws IOException {
                ObjectOutputStream out = new ObjectOutputStream(link.getOutputStream());
                out.writeObject(obj);
                out.flush();
            }

            public final Object getPacketData() throws IOException, ClassNotFoundException {
                ObjectInputStream objectInputStream = new ObjectInputStream(link.getInputStream());
                Object obj = objectInputStream.readObject();
                return getDTO(obj);
            }
        });
    }

    public Thread waitForClient() throws IOException {
        return getClient();
    }

    @Override
    public void conversationHandler(RunnableTCPThread r, Conversation c) { }
}
