package clavardage.controller.connectivity;

import clavardage.model.objects.User;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;
import java.util.UUID;

public class DiscoveryService implements Activity {

    private User newUser;
    private final UDPConnector udpListener, udpSender;

    public DiscoveryService() throws Exception {
        super();
        udpSender = new UDPConnector();
        udpListener = new UDPConnector();
    }

    public void sendHello() throws Exception {
        // bonjour routine
        udpSender.sendBroadcastPacket(new User(UUID.randomUUID(), "test", InetAddress.getByName(NetworkConnector.getLocalAddress())));
    }

    /**
     * Wait for UDP connections and trigger done when new connection
     */
    public void listen() throws IOException {
        udpListener.waitPacket();

        try {
            Object obj = udpListener.getPacketData();
            if(Objects.nonNull(obj) && obj instanceof User) {
                newUser = (User)obj;
                done();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getNewUser() {
        return newUser;
    }

    @Override
    public void done() { }
}
