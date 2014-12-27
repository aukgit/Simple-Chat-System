/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineServers;

import CurrentDb.Tables.UserTable;
import OnlineServers.Inheritable.GeneralServer;

/**
 *
 * @author Alim
 */
public class ReplaceOnlineUser extends GeneralServer<UserTable> {

    public ReplaceOnlineUser() {
        super.initialize("Replace User", 9810, super._serverConfig.ServerIP);

    }

    @Override
    public boolean doProcessInServer(UserTable clientObject) {
        int index = userAlreadyOnline(clientObject);
        if (index > -1) {
            // user exist
            super._UsersOnline.set(index, clientObject);
        }
        return true;
    }

    public static void main(String[] args) {
        ReplaceOnlineUser server = new ReplaceOnlineUser();
        server.startThread();
    }
}
