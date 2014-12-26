/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alim
 */
public class InternetProtocol {

    private static ArrayList<String> ipCollection;

    public static String getIp() {
        if (ipCollection == null) {
            ipCollection = new ArrayList<>(10);
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface n = networkInterfaces.nextElement();
                    Enumeration ee;
                    ee = n.getInetAddresses();
                    while (ee.hasMoreElements()) {
                        InetAddress i = (InetAddress) ee.nextElement();
                        ipCollection.add(i.getHostAddress());
                    }
                }
            } catch (SocketException ex) {
                Logger.getLogger(InternetProtocol.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ipCollection.get(ipCollection.size() - 2);
    }

    public static void main(String[] args) {
        System.out.println(getIp());
    }
}
