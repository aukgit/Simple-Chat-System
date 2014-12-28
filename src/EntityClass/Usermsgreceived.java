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
@Table(name = "usermsgreceived")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usermsgreceived.findAll", query = "SELECT u FROM Usermsgreceived u"),
    @NamedQuery(name = "Usermsgreceived.findByUserMsgReceivedID", query = "SELECT u FROM Usermsgreceived u WHERE u.userMsgReceivedID = :userMsgReceivedID"),
    @NamedQuery(name = "Usermsgreceived.findByUserID", query = "SELECT u FROM Usermsgreceived u WHERE u.userID = :userID"),
    @NamedQuery(name = "Usermsgreceived.findByMessageID", query = "SELECT u FROM Usermsgreceived u WHERE u.messageID = :messageID")})
public class Usermsgreceived implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UserMsgReceivedID")
    private Long userMsgReceivedID;
    @Basic(optional = false)
    @Column(name = "UserID")
    private int userID;
    @Basic(optional = false)
    @Column(name = "MessageID")
    private long messageID;

    public Usermsgreceived() {
    }

    public Usermsgreceived(Long userMsgReceivedID) {
        this.userMsgReceivedID = userMsgReceivedID;
    }

    public Usermsgreceived(Long userMsgReceivedID, int userID, long messageID) {
        this.userMsgReceivedID = userMsgReceivedID;
        this.userID = userID;
        this.messageID = messageID;
    }

    public Long getUserMsgReceivedID() {
        return userMsgReceivedID;
    }

    public void setUserMsgReceivedID(Long userMsgReceivedID) {
        this.userMsgReceivedID = userMsgReceivedID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userMsgReceivedID != null ? userMsgReceivedID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usermsgreceived)) {
            return false;
        }
        Usermsgreceived other = (Usermsgreceived) object;
        if ((this.userMsgReceivedID == null && other.userMsgReceivedID != null) || (this.userMsgReceivedID != null && !this.userMsgReceivedID.equals(other.userMsgReceivedID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClass.Usermsgreceived[ userMsgReceivedID=" + userMsgReceivedID + " ]";
    }
    
}
