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
@Table(name = "chatsessionrelateduser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chatsessionrelateduser.findAll", query = "SELECT c FROM Chatsessionrelateduser c"),
    @NamedQuery(name = "Chatsessionrelateduser.findByChatSessionRelatedUserID", query = "SELECT c FROM Chatsessionrelateduser c WHERE c.chatSessionRelatedUserID = :chatSessionRelatedUserID"),
    @NamedQuery(name = "Chatsessionrelateduser.findByChatSessionID", query = "SELECT c FROM Chatsessionrelateduser c WHERE c.chatSessionID = :chatSessionID"),
    @NamedQuery(name = "Chatsessionrelateduser.findByUserID", query = "SELECT c FROM Chatsessionrelateduser c WHERE c.userID = :userID")})
public class Chatsessionrelateduser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ChatSessionRelatedUserID")
    private Long chatSessionRelatedUserID;
    @Basic(optional = false)
    @Column(name = "ChatSessionID")
    private int chatSessionID;
    @Basic(optional = false)
    @Column(name = "UserID")
    private int userID;

    public Chatsessionrelateduser() {
    }

    public Chatsessionrelateduser(Long chatSessionRelatedUserID) {
        this.chatSessionRelatedUserID = chatSessionRelatedUserID;
    }

    public Chatsessionrelateduser(Long chatSessionRelatedUserID, int chatSessionID, int userID) {
        this.chatSessionRelatedUserID = chatSessionRelatedUserID;
        this.chatSessionID = chatSessionID;
        this.userID = userID;
    }

    public Long getChatSessionRelatedUserID() {
        return chatSessionRelatedUserID;
    }

    public void setChatSessionRelatedUserID(Long chatSessionRelatedUserID) {
        this.chatSessionRelatedUserID = chatSessionRelatedUserID;
    }

    public int getChatSessionID() {
        return chatSessionID;
    }

    public void setChatSessionID(int chatSessionID) {
        this.chatSessionID = chatSessionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chatSessionRelatedUserID != null ? chatSessionRelatedUserID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chatsessionrelateduser)) {
            return false;
        }
        Chatsessionrelateduser other = (Chatsessionrelateduser) object;
        if ((this.chatSessionRelatedUserID == null && other.chatSessionRelatedUserID != null) || (this.chatSessionRelatedUserID != null && !this.chatSessionRelatedUserID.equals(other.chatSessionRelatedUserID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClass.Chatsessionrelateduser[ chatSessionRelatedUserID=" + chatSessionRelatedUserID + " ]";
    }
    
}
