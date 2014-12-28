/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineServers.RelatedObjects;

import java.util.Date;

/**
 *
 * @author Alim
 */
public class ChatMessageSendingObject {
    int sessionID;
    int senderUserID;
    Date sendingTime;
    
    boolean isContainFile;
    byte[] file;
    
    
    String message;
    String messageDisplay;
    
}
