/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineServers;

import OnlineServers.Inheritable.GeneralServer;
import OnlineServers.RelatedObjects.ChatSendingObject;

/**
 * Uploads pictures and send pictures on behalf
 *
 * @author Alim
 *
 */
public class ChattingServer extends GeneralServer<ChatSendingObject> {

    public static int ChattingServerPort = 8887;

    public ChattingServer() {
        super.initialize("Chatting Server", ChattingServerPort, super._serverConfig.ServerIP);
    }

    @Override
    public boolean doProcessInServer(ChatSendingObject clientObject) {
        
        return true;
    }

    public static void main(String[] args) {
        ChattingServer uploaderServer = new ChattingServer();
        uploaderServer.startThread();
    }

}
