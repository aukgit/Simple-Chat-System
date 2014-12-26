/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClass;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alim
 */
@Entity
@Table(name = "chatsession")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chatsession.findAll", query = "SELECT c FROM Chatsession c"),
    @NamedQuery(name = "Chatsession.findByChatSessionID", query = "SELECT c FROM Chatsession c WHERE c.chatSessionID = :chatSessionID"),
    @NamedQuery(name = "Chatsession.findByTimed", query = "SELECT c FROM Chatsession c WHERE c.timed = :timed"),
    @NamedQuery(name = "Chatsession.findByIsActive", query = "SELECT c FROM Chatsession c WHERE c.isActive = :isActive")})
public class Chatsession implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ChatSessionID")
    private Integer chatSessionID;
    @Basic(optional = false)
    @Column(name = "Timed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timed;
    @Basic(optional = false)
    @Column(name = "IsActive")
    private boolean isActive;

    public Chatsession() {
    }

    public Chatsession(Integer chatSessionID) {
        this.chatSessionID = chatSessionID;
    }

    public Chatsession(Integer chatSessionID, Date timed, boolean isActive) {
        this.chatSessionID = chatSessionID;
        this.timed = timed;
        this.isActive = isActive;
    }

    public Integer getChatSessionID() {
        return chatSessionID;
    }

    public void setChatSessionID(Integer chatSessionID) {
        this.chatSessionID = chatSessionID;
    }

    public Date getTimed() {
        return timed;
    }

    public void setTimed(Date timed) {
        this.timed = timed;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chatSessionID != null ? chatSessionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chatsession)) {
            return false;
        }
        Chatsession other = (Chatsession) object;
        if ((this.chatSessionID == null && other.chatSessionID != null) || (this.chatSessionID != null && !this.chatSessionID.equals(other.chatSessionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClass.Forms.Chatsession[ chatSessionID=" + chatSessionID + " ]";
    }
    
}
