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
@Table(name = "chatlist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chatlist.findAll", query = "SELECT c FROM Chatlist c"),
    @NamedQuery(name = "Chatlist.findByChatListID", query = "SELECT c FROM Chatlist c WHERE c.chatListID = :chatListID"),
    @NamedQuery(name = "Chatlist.findByOriginalUserID", query = "SELECT c FROM Chatlist c WHERE c.originalUserID = :originalUserID"),
    @NamedQuery(name = "Chatlist.findByRelatedUserID", query = "SELECT c FROM Chatlist c WHERE c.relatedUserID = :relatedUserID"),
    @NamedQuery(name = "Chatlist.findByAliasAs", query = "SELECT c FROM Chatlist c WHERE c.aliasAs = :aliasAs")})
public class Chatlist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ChatListID")
    private Long chatListID;
    @Basic(optional = false)
    @Column(name = "OriginalUserID")
    private int originalUserID;
    @Basic(optional = false)
    @Column(name = "RelatedUserID")
    private int relatedUserID;
    @Basic(optional = false)
    @Column(name = "AliasAs")
    private String aliasAs;

    public Chatlist() {
    }

    public Chatlist(Long chatListID) {
        this.chatListID = chatListID;
    }

    public Chatlist(Long chatListID, int originalUserID, int relatedUserID, String aliasAs) {
        this.chatListID = chatListID;
        this.originalUserID = originalUserID;
        this.relatedUserID = relatedUserID;
        this.aliasAs = aliasAs;
    }

    public Long getChatListID() {
        return chatListID;
    }

    public void setChatListID(Long chatListID) {
        this.chatListID = chatListID;
    }

    public int getOriginalUserID() {
        return originalUserID;
    }

    public void setOriginalUserID(int originalUserID) {
        this.originalUserID = originalUserID;
    }

    public int getRelatedUserID() {
        return relatedUserID;
    }

    public void setRelatedUserID(int relatedUserID) {
        this.relatedUserID = relatedUserID;
    }

    public String getAliasAs() {
        return aliasAs;
    }

    public void setAliasAs(String aliasAs) {
        this.aliasAs = aliasAs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chatListID != null ? chatListID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chatlist)) {
            return false;
        }
        Chatlist other = (Chatlist) object;
        if ((this.chatListID == null && other.chatListID != null) || (this.chatListID != null && !this.chatListID.equals(other.chatListID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClass.Forms.Chatlist[ chatListID=" + chatListID + " ]";
    }
    
}
