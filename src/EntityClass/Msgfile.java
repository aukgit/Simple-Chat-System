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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alim
 */
@Entity
@Table(name = "msgfile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Msgfile.findAll", query = "SELECT m FROM Msgfile m"),
    @NamedQuery(name = "Msgfile.findByMsgFileID", query = "SELECT m FROM Msgfile m WHERE m.msgFileID = :msgFileID"),
    @NamedQuery(name = "Msgfile.findByMessageID", query = "SELECT m FROM Msgfile m WHERE m.messageID = :messageID")})
public class Msgfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MsgFileID")
    private Long msgFileID;
    @Basic(optional = false)
    @Column(name = "MessageID")
    private long messageID;
    @Basic(optional = false)
    @Lob
    @Column(name = "File")
    private byte[] file;

    public Msgfile() {
    }

    public Msgfile(Long msgFileID) {
        this.msgFileID = msgFileID;
    }

    public Msgfile(Long msgFileID, long messageID, byte[] file) {
        this.msgFileID = msgFileID;
        this.messageID = messageID;
        this.file = file;
    }

    public Long getMsgFileID() {
        return msgFileID;
    }

    public void setMsgFileID(Long msgFileID) {
        this.msgFileID = msgFileID;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msgFileID != null ? msgFileID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Msgfile)) {
            return false;
        }
        Msgfile other = (Msgfile) object;
        if ((this.msgFileID == null && other.msgFileID != null) || (this.msgFileID != null && !this.msgFileID.equals(other.msgFileID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClass.Msgfile[ msgFileID=" + msgFileID + " ]";
    }
    
}
