package clavardage.controller.connectivity;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

/**
 * Interface enabling retrieving of some TCP connection properties
 * @author Romain MONIER
 */
public interface RunnableTCPThread extends Runnable {
    /**
     * A closing TCP connection operation
     * @author Romain MONIER
     * @throws IOException
     */
    public void close() throws IOException;

    /**
     * A socket link getter
     * @author Romain MONIER
     * @return
     */
    public Socket getLink();

    /**
     * A TCP sending operation
     * @author Romain MONIER
     * @param obj
     * @throws IOException
     */
    public void sendPacket(Serializable obj) throws IOException;

    /**
     * A packet data getter
     * @author Romain MONIER
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object getPacketData() throws IOException, ClassNotFoundException;
}
