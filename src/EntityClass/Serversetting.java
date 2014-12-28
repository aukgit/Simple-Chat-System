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
@Table(name = "serversetting")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Serversetting.findAll", query = "SELECT s FROM Serversetting s"),
    @NamedQuery(name = "Serversetting.findByServerSettingID", query = "SELECT s FROM Serversetting s WHERE s.serverSettingID = :serverSettingID"),
    @NamedQuery(name = "Serversetting.findByServerIP", query = "SELECT s FROM Serversetting s WHERE s.serverIP = :serverIP"),
    @NamedQuery(name = "Serversetting.findByServerPort", query = "SELECT s FROM Serversetting s WHERE s.serverPort = :serverPort"),
    @NamedQuery(name = "Serversetting.findByIsActive", query = "SELECT s FROM Serversetting s WHERE s.isActive = :isActive"),
    @NamedQuery(name = "Serversetting.findByConnectionString", query = "SELECT s FROM Serversetting s WHERE s.connectionString = :connectionString"),
    @NamedQuery(name = "Serversetting.findByUserOnlinePort", query = "SELECT s FROM Serversetting s WHERE s.userOnlinePort = :userOnlinePort"),
    @NamedQuery(name = "Serversetting.findByPictureUploaderPort", query = "SELECT s FROM Serversetting s WHERE s.pictureUploaderPort = :pictureUploaderPort"),
    @NamedQuery(name = "Serversetting.findByProfilePicWidth", query = "SELECT s FROM Serversetting s WHERE s.profilePicWidth = :profilePicWidth"),
    @NamedQuery(name = "Serversetting.findByProfilePicHeight", query = "SELECT s FROM Serversetting s WHERE s.profilePicHeight = :profilePicHeight"),
    @NamedQuery(name = "Serversetting.findByChatingThumbWidth", query = "SELECT s FROM Serversetting s WHERE s.chatingThumbWidth = :chatingThumbWidth"),
    @NamedQuery(name = "Serversetting.findByChatingThumbHeight", query = "SELECT s FROM Serversetting s WHERE s.chatingThumbHeight = :chatingThumbHeight"),
    @NamedQuery(name = "Serversetting.findByChatListThumbWidth", query = "SELECT s FROM Serversetting s WHERE s.chatListThumbWidth = :chatListThumbWidth"),
    @NamedQuery(name = "Serversetting.findByChatListThumbHeight", query = "SELECT s FROM Serversetting s WHERE s.chatListThumbHeight = :chatListThumbHeight")})
public class Serversetting implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ServerSettingID")
    private Short serverSettingID;
    @Basic(optional = false)
    @Column(name = "ServerIP")
    private String serverIP;
    @Basic(optional = false)
    @Column(name = "ServerPort")
    private short serverPort;
    @Basic(optional = false)
    @Column(name = "IsActive")
    private boolean isActive;
    @Column(name = "ConnectionString")
    private String connectionString;
    @Basic(optional = false)
    @Column(name = "UserOnlinePort")
    private short userOnlinePort;
    @Basic(optional = false)
    @Column(name = "PictureUploaderPort")
    private short pictureUploaderPort;
    @Basic(optional = false)
    @Column(name = "ProfilePicWidth")
    private short profilePicWidth;
    @Basic(optional = false)
    @Column(name = "ProfilePicHeight")
    private short profilePicHeight;
    @Basic(optional = false)
    @Column(name = "ChatingThumbWidth")
    private short chatingThumbWidth;
    @Basic(optional = false)
    @Column(name = "ChatingThumbHeight")
    private short chatingThumbHeight;
    @Basic(optional = false)
    @Column(name = "ChatListThumbWidth")
    private short chatListThumbWidth;
    @Basic(optional = false)
    @Column(name = "ChatListThumbHeight")
    private short chatListThumbHeight;

    public Serversetting() {
    }

    public Serversetting(Short serverSettingID) {
        this.serverSettingID = serverSettingID;
    }

    public Serversetting(Short serverSettingID, String serverIP, short serverPort, boolean isActive, short userOnlinePort, short pictureUploaderPort, short profilePicWidth, short profilePicHeight, short chatingThumbWidth, short chatingThumbHeight, short chatListThumbWidth, short chatListThumbHeight) {
        this.serverSettingID = serverSettingID;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.isActive = isActive;
        this.userOnlinePort = userOnlinePort;
        this.pictureUploaderPort = pictureUploaderPort;
        this.profilePicWidth = profilePicWidth;
        this.profilePicHeight = profilePicHeight;
        this.chatingThumbWidth = chatingThumbWidth;
        this.chatingThumbHeight = chatingThumbHeight;
        this.chatListThumbWidth = chatListThumbWidth;
        this.chatListThumbHeight = chatListThumbHeight;
    }

    public Short getServerSettingID() {
        return serverSettingID;
    }

    public void setServerSettingID(Short serverSettingID) {
        this.serverSettingID = serverSettingID;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public short getServerPort() {
        return serverPort;
    }

    public void setServerPort(short serverPort) {
        this.serverPort = serverPort;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public short getUserOnlinePort() {
        return userOnlinePort;
    }

    public void setUserOnlinePort(short userOnlinePort) {
        this.userOnlinePort = userOnlinePort;
    }

    public short getPictureUploaderPort() {
        return pictureUploaderPort;
    }

    public void setPictureUploaderPort(short pictureUploaderPort) {
        this.pictureUploaderPort = pictureUploaderPort;
    }

    public short getProfilePicWidth() {
        return profilePicWidth;
    }

    public void setProfilePicWidth(short profilePicWidth) {
        this.profilePicWidth = profilePicWidth;
    }

    public short getProfilePicHeight() {
        return profilePicHeight;
    }

    public void setProfilePicHeight(short profilePicHeight) {
        this.profilePicHeight = profilePicHeight;
    }

    public short getChatingThumbWidth() {
        return chatingThumbWidth;
    }

    public void setChatingThumbWidth(short chatingThumbWidth) {
        this.chatingThumbWidth = chatingThumbWidth;
    }

    public short getChatingThumbHeight() {
        return chatingThumbHeight;
    }

    public void setChatingThumbHeight(short chatingThumbHeight) {
        this.chatingThumbHeight = chatingThumbHeight;
    }

    public short getChatListThumbWidth() {
        return chatListThumbWidth;
    }

    public void setChatListThumbWidth(short chatListThumbWidth) {
        this.chatListThumbWidth = chatListThumbWidth;
    }

    public short getChatListThumbHeight() {
        return chatListThumbHeight;
    }

    public void setChatListThumbHeight(short chatListThumbHeight) {
        this.chatListThumbHeight = chatListThumbHeight;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serverSettingID != null ? serverSettingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Serversetting)) {
            return false;
        }
        Serversetting other = (Serversetting) object;
        if ((this.serverSettingID == null && other.serverSettingID != null) || (this.serverSettingID != null && !this.serverSettingID.equals(other.serverSettingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClass.Serversetting[ serverSettingID=" + serverSettingID + " ]";
    }
    
}
