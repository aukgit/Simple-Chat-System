/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineServers.RelatedObjects;

import CurrentDb.Tables.MessageTable;

/**
 *
 * @author Alim
 */
public class ChatSendingObject {

    private boolean newChatSession;

    private int sessionID;
    private int senderUserID;
    private boolean isSingleUser;
    private MessageTable message;
    private byte[] file;
    private int serverHitCounter;

    /**
     * @return the newChatSession
     */
    public boolean isNewChatSession() {
        return newChatSession;
    }

    /**
     * @param newChatSession the newChatSession to set
     */
    public void setNewChatSession(boolean newChatSession) {
        this.newChatSession = newChatSession;
    }

    /**
     * @return the sessionID
     */
    public int getSessionID() {
        return sessionID;
    }

    /**
     * @param sessionID the sessionID to set
     */
    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    /**
     * @return the senderUserID
     */
    public int getSenderUserID() {
        return senderUserID;
    }

    /**
     * @param senderUserID the senderUserID to set
     */
    public void setSenderUserID(int senderUserID) {
        this.senderUserID = senderUserID;
    }

    /**
     * @return the isSingleUser
     */
    public boolean isIsSingleUser() {
        return isSingleUser;
    }

    /**
     * @param isSingleUser the isSingleUser to set
     */
    public void setIsSingleUser(boolean isSingleUser) {
        this.isSingleUser = isSingleUser;
    }

    /**
     * @return the message
     */
    public MessageTable getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(MessageTable message) {
        this.message = message;
    }

    /**
     * @return the file
     */
    public byte[] getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(byte[] file) {
        this.file = file;
    }

    /**
     * @return the serverHitCounter
     */
    public int getServerHitCounter() {
        return serverHitCounter;
    }

    /**
     * @param serverHitCounter the serverHitCounter to set
     */
    public void setServerHitCounter(int serverHitCounter) {
        this.serverHitCounter = serverHitCounter;
    }
}
