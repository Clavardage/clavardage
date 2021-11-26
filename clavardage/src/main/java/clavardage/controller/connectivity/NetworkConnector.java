package clavardage.controller.connectivity;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

abstract public class NetworkConnector {
    protected final String LOCAL_IP_BROADCAST = true ? "127.0.0.255" : getBroadcastAddress(); // localhost for testing
    protected final String LOCAL_IP_ADDRESS = true ? "127.0.0.1" : getLocalAddress(); // localhost for testing

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
}
