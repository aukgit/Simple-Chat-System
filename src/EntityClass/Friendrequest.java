/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClass;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alim
 */
@Entity
@Table(name = "friendrequest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Friendrequest.findAll", query = "SELECT f FROM Friendrequest f"),
    @NamedQuery(name = "Friendrequest.findByFriendRequestID", query = "SELECT f FROM Friendrequest f WHERE f.friendRequestID = :friendRequestID"),
    @NamedQuery(name = "Friendrequest.findBySenderUserID", query = "SELECT f FROM Friendrequest f WHERE f.senderUserID = :senderUserID"),
    @NamedQuery(name = "Friendrequest.findByToWhomUserID", query = "SELECT f FROM Friendrequest f WHERE f.toWhomUserID = :toWhomUserID"),
    @NamedQuery(name = "Friendrequest.findByIsAccept", query = "SELECT f FROM Friendrequest f WHERE f.isAccept = :isAccept"),
    @NamedQuery(name = "Friendrequest.findByMessage", query = "SELECT f FROM Friendrequest f WHERE f.message = :message"),
    @NamedQuery(name = "Friendrequest.findByIsSeen", query = "SELECT f FROM Friendrequest f WHERE f.isSeen = :isSeen")})
public class Friendrequest implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FriendRequestID")
    private Long friendRequestID;
    @Basic(optional = false)
    @Column(name = "SenderUserID")
    private int senderUserID;
    @Basic(optional = false)
    @Column(name = "ToWhomUserID")
    private int toWhomUserID;
    @Basic(optional = false)
    @Column(name = "IsAccept")
    private short isAccept;
    @Basic(optional = false)
    @Column(name = "Message")
    private String message;
    @Basic(optional = false)
    @Column(name = "IsSeen")
    private boolean isSeen;

    public Friendrequest() {
    }

    public Friendrequest(Long friendRequestID) {
        this.friendRequestID = friendRequestID;
    }

    public Friendrequest(Long friendRequestID, int senderUserID, int toWhomUserID, short isAccept, String message, boolean isSeen) {
        this.friendRequestID = friendRequestID;
        this.senderUserID = senderUserID;
        this.toWhomUserID = toWhomUserID;
        this.isAccept = isAccept;
        this.message = message;
        this.isSeen = isSeen;
    }

    public Long getFriendRequestID() {
        return friendRequestID;
    }

    public void setFriendRequestID(Long friendRequestID) {
        Long oldFriendRequestID = this.friendRequestID;
        this.friendRequestID = friendRequestID;
        changeSupport.firePropertyChange("friendRequestID", oldFriendRequestID, friendRequestID);
    }

    public int getSenderUserID() {
        return senderUserID;
    }

    public void setSenderUserID(int senderUserID) {
        int oldSenderUserID = this.senderUserID;
        this.senderUserID = senderUserID;
        changeSupport.firePropertyChange("senderUserID", oldSenderUserID, senderUserID);
    }

    public int getToWhomUserID() {
        return toWhomUserID;
    }

    public void setToWhomUserID(int toWhomUserID) {
        int oldToWhomUserID = this.toWhomUserID;
        this.toWhomUserID = toWhomUserID;
        changeSupport.firePropertyChange("toWhomUserID", oldToWhomUserID, toWhomUserID);
    }

    public short getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(short isAccept) {
        short oldIsAccept = this.isAccept;
        this.isAccept = isAccept;
        changeSupport.firePropertyChange("isAccept", oldIsAccept, isAccept);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        String oldMessage = this.message;
        this.message = message;
        changeSupport.firePropertyChange("message", oldMessage, message);
    }

    public boolean getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(boolean isSeen) {
        boolean oldIsSeen = this.isSeen;
        this.isSeen = isSeen;
        changeSupport.firePropertyChange("isSeen", oldIsSeen, isSeen);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (friendRequestID != null ? friendRequestID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Friendrequest)) {
            return false;
        }
        Friendrequest other = (Friendrequest) object;
        if ((this.friendRequestID == null && other.friendRequestID != null) || (this.friendRequestID != null && !this.friendRequestID.equals(other.friendRequestID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClass.Friendrequest[ friendRequestID=" + friendRequestID + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
