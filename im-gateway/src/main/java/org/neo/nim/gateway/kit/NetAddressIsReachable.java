package org.neo.nim.gateway.kit;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class NetAddressIsReachable {

    /**
     * check ip and port
     *
     * @param address  String
     * @param port     int
     * @param timeout   int
     * @return True if connection successful
     */
    public static boolean checkAddressReachable(String address, int port, int timeout) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(address, port), timeout);
            return true;
        } catch (IOException exception) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                return false;
            }
        }
    }
}
