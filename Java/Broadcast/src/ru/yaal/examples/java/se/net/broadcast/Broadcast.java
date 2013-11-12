package ru.yaal.examples.java.se.net.broadcast;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class Broadcast {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
        while (networks.hasMoreElements()) {
            NetworkInterface network = networks.nextElement();
//            System.out.println(network);
            List<InterfaceAddress> addresses = network.getInterfaceAddresses();
            for (InterfaceAddress address : addresses) {
                InetAddress broadcast = address.getBroadcast();
//                System.out.println(address);
                System.out.println(broadcast);
            }
        }
    }
}
