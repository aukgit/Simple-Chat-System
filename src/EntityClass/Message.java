/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClass;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alim
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findByMessageID", query = "SELECT m FROM Message m WHERE m.messageID = :messageID"),
    @NamedQuery(name = "Message.findBySendFromUserID", query = "SELECT m FROM Message m WHERE m.sendFromUserID = :sendFromUserID"),
    @NamedQuery(name = "Message.findBySendToUserID", query = "SELECT m FROM Message m WHERE m.sendToUserID = :sendToUserID"),
    @NamedQuery(name = "Message.findByIsReceivedByUser", query = "SELECT m FROM Message m WHERE m.isReceivedByUser = :isReceivedByUser"),
    @NamedQuery(name = "Message.findByMessage", query = "SELECT m FROM Message m WHERE m.message = :message"),
    @NamedQuery(name = "Message.findByIsFileExit", query = "SELECT m FROM Message m WHERE m.isFileExit = :isFileExit"),
    @NamedQuery(name = "Message.findByFileLocation", query = "SELECT m FROM Message m WHERE m.fileLocation = :fileLocation")})
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MessageID")
    private Long messageID;
    @Basic(optional = false)
    @Column(name = "SendFromUserID")
    private int sendFromUserID;
    @Basic(optional = false)
    @Column(name = "SendToUserID")
    private int sendToUserID;
    @Basic(optional = false)
    @Column(name = "IsReceivedByUser")
    private boolean isReceivedByUser;
    @Basic(optional = false)
    @Column(name = "Message")
    private String message;
    @Basic(optional = false)
    @Column(name = "IsFileExit")
    private boolean isFileExit;
    @Column(name = "FileLocation")
    private String fileLocation;

    public Message() {
    }

    public Message(Long messageID) {
        this.messageID = messageID;
    }

    public Message(Long messageID, int sendFromUserID, int sendToUserID, boolean isReceivedByUser, String message, boolean isFileExit) {
        this.messageID = messageID;
        this.sendFromUserID = sendFromUserID;
        this.sendToUserID = sendToUserID;
        this.isReceivedByUser = isReceivedByUser;
        this.message = message;
        this.isFileExit = isFileExit;
    }

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public int getSendFromUserID() {
        return sendFromUserID;
    }

    public void setSendFromUserID(int sendFromUserID) {
        this.sendFromUserID = sendFromUserID;
    }

    public int getSendToUserID() {
        return sendToUserID;
    }

    public void setSendToUserID(int sendToUserID) {
        this.sendToUserID = sendToUserID;
    }

    public boolean getIsReceivedByUser() {
        return isReceivedByUser;
    }

    public void setIsReceivedByUser(boolean isReceivedByUser) {
        this.isReceivedByUser = isReceivedByUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsFileExit() {
        return isFileExit;
    }

    public void setIsFileExit(boolean isFileExit) {
        this.isFileExit = isFileExit;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageID != null ? messageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messageID == null && other.messageID != null) || (this.messageID != null && !this.messageID.equals(other.messageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClass.Message[ messageID=" + messageID + " ]";
    }
    
}
