package clavardage.controller.connectivity;

import clavardage.model.objects.User;

import java.io.*;
import java.net.*;
import java.util.Objects;

/**
 * UDP Connector
 * @author Romain MONIER
 */
public class UDPConnector extends NetworkConnector {

    private final int DEFAULT_UDP_PORT = 4242;
    private final int BUFFER_SIZE = 65535;

    private final int FORCED_UDP_PORT;
    byte[] buffer;
    DatagramSocket dgramSocketIn, dgramSocketOut;
    DatagramPacket inPacket, outPacket;

    /**
     * Use the default 4242 port
     * @author Romain MONIER
     * @throws Exception
     */
    public UDPConnector() throws Exception {
        super();
        FORCED_UDP_PORT = DEFAULT_UDP_PORT;
    }

    /**
     * Use the custom port
     * @author Romain MONIER
     * @param port
     * @throws Exception
     */
    public UDPConnector(int port) throws Exception {
        super();
        FORCED_UDP_PORT = port;
    }

    /**
     * Send the packet data
     * @author Romain MONIER
     * @param obj
     * @param ip
     * @param port
     * @throws IOException
     */
    public void sendPacket(Serializable obj, String ip, int port) throws IOException {
        if(Objects.nonNull(dgramSocketOut))
            dgramSocketOut.close();
        dgramSocketOut = new DatagramSocket();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        out.flush();
        outPacket = new DatagramPacket(bos.toByteArray(), bos.toByteArray().length, InetAddress.getByName(ip), port);
        System.out.println("Log: Sending to " + ip + ":" + port);
        dgramSocketOut.send(outPacket);
        dgramSocketOut.close();
    }

    /**
     * Send a broadcast packet
     * @author Romain MONIER
     * @param obj
     */
    public void sendBroadcastPacket(Serializable obj) throws IOException {
        for(String ip_broadcast : LOCAL_IP_BROADCAST_LIST) {
            //try {
                sendPacket(obj, ip_broadcast, FORCED_UDP_PORT);
            //} catch (Exception ignore) { }
        }
    }

    /**
     * Get a packet data
     * @author Romain MONIER
     * @throws IOException
     */
    private void getPacket() throws IOException {
        if(Objects.nonNull(dgramSocketIn))
            dgramSocketIn.close();
        dgramSocketIn = new DatagramSocket(FORCED_UDP_PORT);
        buffer = new byte[BUFFER_SIZE];
        inPacket = new DatagramPacket(buffer, buffer.length);
        System.out.println("Log: Listening on *:" + FORCED_UDP_PORT + "...");
        dgramSocketIn.receive(inPacket);
        dgramSocketIn.close();
    }

    /**
     * UDP getter wrapper
     * @author Romain MONIER
     * @throws IOException
     */
    public void waitPacket() throws IOException {
        //do {
            getPacket();
        //} while(LOCAL_IP_ADDRESS_LIST.contains(inPacket.getAddress().getHostAddress()));
    }

    /**
     * Get the packet data and update the user IP in a hacky way
     * @author Romain MONIER
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object getPacketData() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(inPacket.getData()));
        Object obj = objectInputStream.readObject();
        objectInputStream.close();
        if(User.class.isAssignableFrom(obj.getClass())) {
            ((User)obj).setLastIp(inPacket.getAddress()); // quick hack to set the true address (improve this later)
            System.out.println("Log: new user UDP IP: " + inPacket.getAddress().getHostAddress());
        }
        return getDTO(obj);
    }
}
