/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineServers;

/**
 *
 * @author Alim
 */
public class ClientToCheckUserOnline {

    public static void main(String[] args) throws ClassNotFoundException {

        UserOnlineServer online = new UserOnlineServer();
        online.reReadDataFromServer();
//        online.sendUserOnlineRequestToServer();

    }
}
