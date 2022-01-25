package clavardage.controller.connectivity;

import clavardage.model.objects.*;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

abstract public class NetworkConnector {
    protected final ArrayList<String> LOCAL_IP_BROADCAST_LIST = getBroadcastAddresses();
    protected final ArrayList<String> LOCAL_IP_ADDRESS_LIST = getLocalAddresses();

    protected NetworkConnector() throws Exception {
    }

    public static ArrayList<String> getBroadcastAddresses() throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (broadcast == null)
                    continue;

                list.add(broadcast.getHostAddress());
            }
        }

        if(list.size() > 0)
            return list;

        throw new Exception("Error while retrieving broadcast address");
    }

    public static ArrayList<String> getLocalAddresses() throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress local = interfaceAddress.getAddress();
                if (local == null)
                    continue;

                list.add(local.getHostAddress());
            }
        }

        if(list.size() > 0)
            return list;

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
        } else if(DatabaseMap.class.isAssignableFrom(obj.getClass())) {
            return (DatabaseMap<Class<?>, ArrayList<?>>)obj;
        }

        return null;
    }
}
