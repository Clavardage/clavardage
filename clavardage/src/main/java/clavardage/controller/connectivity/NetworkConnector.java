package clavardage.controller.connectivity;

import clavardage.controller.Clavardage;
import clavardage.model.objects.Conversation;
import clavardage.model.objects.Message;
import clavardage.model.objects.User;
import clavardage.model.objects.UserPrivate;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

abstract public class NetworkConnector {
    protected final String LOCAL_IP_BROADCAST = true ? "127.255.255.255" : getBroadcastAddress(); // localhost for testing
    protected final String LOCAL_IP_ADDRESS = true ? (Clavardage.machine1 ? "127.0.0.1" : "127.0.0.2") : getLocalAddress(); // localhost for testing

    protected NetworkConnector() throws Exception {
    }

    public static String getBroadcastAddress() throws Exception {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback())
                continue;
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (broadcast == null)
                    continue;

                return broadcast.getHostAddress();
            }
        }
        throw new Exception("Error while retrieving broadcast address");
    }

    public static String getLocalAddress() throws Exception {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback())
                continue;
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress local = interfaceAddress.getAddress();
                if (local == null)
                    continue;

                return local.getHostAddress();
            }
        }
        throw new Exception("Error while retrieving local address");
    }

    /**
     * Returns the true DTO type or null if not assigned
     * @param obj
     * @return
     */
    public Object getDTO(Object obj) {
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
