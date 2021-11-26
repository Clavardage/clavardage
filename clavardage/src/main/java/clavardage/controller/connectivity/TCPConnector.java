package clavardage.controller.connectivity;

import clavardage.model.objects.Conversation;
import clavardage.model.objects.Message;
import clavardage.model.objects.User;
import clavardage.model.objects.UserPrivate;

import java.io.*;
import java.net.*;
import java.util.Objects;

public class TCPConnector extends NetworkConnector implements ConversationActivity {
    private final int DEFAULT_TCP_PORT = 4342;
    private final int BUFFER_SIZE = 2048;

    private final int FORCED_TCP_PORT;
    byte[] buffer;
    ServerSocket servSocket;
    Socket cliSocket;
    DatagramPacket inPacket, outPacket;

    public TCPConnector() throws Exception {
        super();
        FORCED_TCP_PORT = DEFAULT_TCP_PORT;
        System.out.println("Log: broadcast addr = " + LOCAL_IP_ADDRESS + ":" + FORCED_TCP_PORT);
    }

    public TCPConnector(int port) throws Exception {
        super();
        FORCED_TCP_PORT = port;
        System.out.println("Log: broadcast addr = " + LOCAL_IP_ADDRESS + ":" + FORCED_TCP_PORT);
    }

    public Thread askServer(Serializable obj, String ip) throws IOException {
        if(Objects.nonNull(cliSocket))
            cliSocket.close();
        cliSocket = new Socket(InetAddress.getByName(ip), FORCED_TCP_PORT);
        System.out.println("Log: TCP asking to " + ip + ":" + FORCED_TCP_PORT);
        Socket link = servSocket.accept();
        return new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Log: Connected to server, delegating to new thread.");
                TCPConnector.this.conversationHandler();
            }

            private final void close() throws IOException {
                link.close();
                servSocket.close();
            }

            private final Socket getLink() {
                return link;
            }

            private final void sendPacket() {

            }

            private final Object getPacketData() throws IOException, ClassNotFoundException {
                ObjectInputStream objectInputStream = new ObjectInputStream(link.getInputStream());
                Object obj = objectInputStream.readObject();

                /* CHECK DTO TYPE */

                if(UserPrivate.class.isAssignableFrom(obj.getClass())) {
                    return (UserPrivate)obj;
                } else if(User.class.isAssignableFrom(obj.getClass())) {
                    return (User)obj;
                } else if(Conversation.class.isAssignableFrom(obj.getClass())) {
                    return (Conversation)obj;
                } else if(Message.class.isAssignableFrom(obj.getClass())) {
                    return (Message)obj;
                }

                return null;
            }
        });
    }

    private Thread getClient() throws IOException {
        servSocket = new ServerSocket(FORCED_TCP_PORT, 1, InetAddress.getByName(LOCAL_IP_ADDRESS));
        System.out.println("Log: TCP accepting on " + LOCAL_IP_ADDRESS + ":" + FORCED_TCP_PORT + "...");
        Socket link = servSocket.accept();
        return new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Log: New client, delegating to new thread.");
                conversationHandler();
            }

            private final void close() throws IOException {
                link.close();
                servSocket.close();
            }

            private final Socket getLink() {
                return link;
            }

            private final void sendPacket() {

            }

            private final Object getPacketData() throws IOException, ClassNotFoundException {
                ObjectInputStream objectInputStream = new ObjectInputStream(link.getInputStream());
                Object obj = objectInputStream.readObject();

                /* CHECK DTO TYPE */

                if(UserPrivate.class.isAssignableFrom(obj.getClass())) {
                    return (UserPrivate)obj;
                } else if(User.class.isAssignableFrom(obj.getClass())) {
                    return (User)obj;
                } else if(Conversation.class.isAssignableFrom(obj.getClass())) {
                    return (Conversation)obj;
                } else if(Message.class.isAssignableFrom(obj.getClass())) {
                    return (Message)obj;
                }

                return null;
            }
        });
    }

    public Thread waitForClient() throws IOException {
        return getClient();
    }

    @Override
    public void conversationHandler() { }
}
