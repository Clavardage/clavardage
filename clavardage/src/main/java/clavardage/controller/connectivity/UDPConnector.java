package clavardage.controller.connectivity;

import clavardage.model.objects.Conversation;
import clavardage.model.objects.Message;
import clavardage.model.objects.User;
import clavardage.model.objects.UserPrivate;

import java.io.*;
import java.net.*;
import java.util.Objects;

public class UDPConnector extends NetworkConnector {

    private final int DEFAULT_UDP_PORT = 4242;
    private final int BUFFER_SIZE = 2048;

    private final int FORCED_UDP_PORT;
    byte[] buffer;
    DatagramSocket dgramSocketIn, dgramSocketOut;
    DatagramPacket inPacket, outPacket;

    public UDPConnector() throws Exception {
        super();
        FORCED_UDP_PORT = DEFAULT_UDP_PORT;
        System.out.println("Log: broadcast addr = " + LOCAL_IP_BROADCAST + ":" + FORCED_UDP_PORT);
    }

    public UDPConnector(int port) throws Exception {
        super();
        FORCED_UDP_PORT = port;
        System.out.println("Log: broadcast addr = " + LOCAL_IP_BROADCAST + ":" + FORCED_UDP_PORT);
    }

    public void sendPacket(Serializable obj, String ip, int port) throws IOException {
        if(Objects.nonNull(dgramSocketOut))
            dgramSocketOut.close();
        dgramSocketOut = new DatagramSocket();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        out.flush();
        outPacket = new DatagramPacket(bos.toByteArray(), bos.toByteArray().length, InetAddress.getByName(ip), port);
        System.out.println("Log: Sending to " + LOCAL_IP_BROADCAST + ":" + FORCED_UDP_PORT);
        dgramSocketOut.send(outPacket);
        dgramSocketOut.close();
    }

    public void sendBroadcastPacket(Serializable obj) throws IOException {
        sendPacket(obj, LOCAL_IP_BROADCAST, FORCED_UDP_PORT);
    }

    private void getPacket() throws IOException {
        if(Objects.nonNull(dgramSocketIn))
            dgramSocketIn.close();
        dgramSocketIn = new DatagramSocket(FORCED_UDP_PORT, InetAddress.getByName(LOCAL_IP_BROADCAST));
        buffer = new byte[BUFFER_SIZE];
        inPacket = new DatagramPacket(buffer, buffer.length);
        System.out.println("Log: Listening on " + LOCAL_IP_BROADCAST + ":" + FORCED_UDP_PORT + "...");
        dgramSocketIn.receive(inPacket);
        dgramSocketIn.close();
    }

    public void waitPacket() throws IOException {
        getPacket();
    }

    public Object getPacketData() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(inPacket.getData()));
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
}
