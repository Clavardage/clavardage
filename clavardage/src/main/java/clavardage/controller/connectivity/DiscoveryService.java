package clavardage.controller.connectivity;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.model.objects.User;
import clavardage.model.objects.UserPrivate;

import java.io.IOException;
import java.util.Objects;

public class DiscoveryService implements Activity {

    private UserPrivate newUser;
    private final UDPConnector udpListener, udpSender;

    public DiscoveryService() throws Exception {
        super();
        udpSender = new UDPConnector();
        udpListener = new UDPConnector();
    }

    public void sendHello() throws Exception {
        // bonjour routine
        udpSender.sendBroadcastPacket(AuthOperations.getConnectedUser()); // it's not really safe to send unencrypted hashed passwords in UDP but well it will be okay for this project I guess...
    }

    /**
     * Wait for UDP connections and trigger done when new connection
     */
    public void listen() throws IOException {
        udpListener.waitPacket();

        try {
            Object obj = udpListener.getPacketData();
            if(Objects.nonNull(obj) && obj instanceof UserPrivate) {
                newUser = (UserPrivate) obj;
                done(); // notify main daemon
            } else {
                System.err.println("User discovery data error");
                done(); // notify main daemon
                throw new Exception("User discovery data error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserPrivate getNewUser() {
        return newUser;
    }

    @Override
    public void done() {
        ConnectivityDaemon.notifyDiscoveryDaemon();
    }
}
