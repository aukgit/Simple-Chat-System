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
    @NamedQuery(name = "Serversetting.findByConnectionString", query = "SELECT s FROM Serversetting s WHERE s.connectionString = :connectionString")})
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
    private int serverPort;
    @Basic(optional = false)
    @Column(name = "IsActive")
    private boolean isActive;
    @Column(name = "ConnectionString")
    private String connectionString;

    public Serversetting() {
    }

    public Serversetting(Short serverSettingID) {
        this.serverSettingID = serverSettingID;
    }

    public Serversetting(Short serverSettingID, String serverIP, int serverPort, boolean isActive) {
        this.serverSettingID = serverSettingID;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.isActive = isActive;
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

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
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
