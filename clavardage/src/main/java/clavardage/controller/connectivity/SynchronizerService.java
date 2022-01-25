package clavardage.controller.connectivity;

import clavardage.model.objects.DatabaseMap;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Objects;

public class SynchronizerService implements Activity {

    private DatabaseMap<Class<?>, ArrayList<?>> data;
    private final UDPConnector udpListener, udpSender;
    private final int SYNC_PORT = 4442;

    public SynchronizerService() throws Exception {
        super();
        udpSender = new UDPConnector(SYNC_PORT);
        udpListener = new UDPConnector(SYNC_PORT);
    }

    public void sendDataTo(Serializable data, InetAddress ip) throws Exception {
        udpSender.sendPacket(data, ip.getHostAddress(), SYNC_PORT);
    }

    /**
     * Wait for UDP entries and trigger done when new reception
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

    public DatabaseMap<Class<?>, ArrayList<?>> getNewData() {
        return data;
    }

    @Override
    public void done() {
        ConnectivityDaemon.notifySynchronizerServerDaemon();
    }
}
