package clavardage.controller.connectivity;

import clavardage.model.objects.DatabaseMap;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Synchronizer Service: When ENABLE_SYNCHRONIZER is set to true, proceed to a distributed exchange of the database with the other applications on the network
 * @author Romain MONIER
 */
public class SynchronizerService implements Activity {

    private DatabaseMap<Class<?>, ArrayList<?>> data;
    private final UDPConnector udpListener, udpSender;
    private final int SYNC_PORT = 4442;

    /**
     * Constructor setting up UDP Connectors on port 4442
     * @author Romain MONIER
     * @throws Exception
     */
    public SynchronizerService() throws Exception {
        super();
        udpSender = new UDPConnector(SYNC_PORT);
        udpListener = new UDPConnector(SYNC_PORT);
    }

    /**
     * Send data in UDP
     * @author Romain MONIER
     * @param data DatabaseMap object waited there
     * @param ip
     * @throws Exception
     */
    public void sendDataTo(Serializable data, InetAddress ip) throws Exception {
        udpSender.sendPacket(data, ip.getHostAddress(), SYNC_PORT);
    }

    /**
     * Wait for UDP entries and trigger done when new reception
     * @author Romain MONIER
     * @throws IOException
     */
    public void listen() throws IOException {
        udpListener.waitPacket();

        try {
            Object obj = udpListener.getPacketData();
            if(Objects.nonNull(obj) && obj instanceof DatabaseMap) {
                data = (DatabaseMap<Class<?>, ArrayList<?>>) obj;
                done(); // notify main daemon
            } else {
                System.err.println("Synchronizer data error");
                done(); // notify main daemon
                throw new Exception("Synchronizer data error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter of the retrieved data
     * @author Romain MONIER
     * @return
     */
    public DatabaseMap<Class<?>, ArrayList<?>> getNewData() {
        return data;
    }

    /**
     * Notifies the synchronizer server daemon when actions finished
     * @author Romain MONIER
     */
    @Override
    public void done() {
        ConnectivityDaemon.notifySynchronizerServerDaemon();
    }
}
