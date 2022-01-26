package clavardage.controller.connectivity;

import clavardage.controller.authentification.AuthOperations;
import clavardage.model.objects.UserPrivate;

import java.io.IOException;
import java.util.Objects;

/**
 * Discovery Service: uses UDP Connectors to handle connected users
 * @author Romain MONIER
 */
public class DiscoveryService implements Activity {

    private UserPrivate newUser;
    private final UDPConnector udpListener, udpSender;

    /**
     * Constructor setting up the UDP Connectors (client and server) on default port
     * @author Romain MONIER
     * @throws Exception
     */
    public DiscoveryService() throws Exception {
        super();
        udpSender = new UDPConnector();
        udpListener = new UDPConnector();
    }

    /**
     * Bonjour Routine, send broadcasts with user to allow to be discovered
     * @author Romain MONIER
     * @throws Exception
     */
    public void sendHello() throws Exception {
        // bonjour routine
        udpSender.sendBroadcastPacket(AuthOperations.getConnectedUser()); // it's not really safe to send unencrypted hashed passwords in UDP but well it will be okay for this project I guess...
    }

    /**
     * Wait for UDP connections and trigger done when new connection
     * @author Romain MONIER
     * @throws IOException
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

    /**
     * New user getter
     * @author Romain MONIER
     * @return
     */
    public UserPrivate getNewUser() {
        return newUser;
    }

    /**
     * Notifies the discovery daemon when actions finished
     * @author Romain MONIER
     */
    @Override
    public void done() {
        ConnectivityDaemon.notifyDiscoveryDaemon();
    }
}
