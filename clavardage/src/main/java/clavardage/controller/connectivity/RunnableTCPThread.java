package clavardage.controller.connectivity;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public interface RunnableTCPThread extends Runnable {
    public void close() throws IOException;
    public Socket getLink();
    public void sendPacket(Serializable obj) throws IOException;
    public Object getPacketData() throws IOException, ClassNotFoundException;
}
