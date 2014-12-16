/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClass;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alim
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserID", query = "SELECT u FROM User u WHERE u.userID = :userID"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByLastLogin", query = "SELECT u FROM User u WHERE u.lastLogin = :lastLogin"),
    @NamedQuery(name = "User.findByIsBlocked", query = "SELECT u FROM User u WHERE u.isBlocked = :isBlocked"),
    @NamedQuery(name = "User.findByIsActive", query = "SELECT u FROM User u WHERE u.isActive = :isActive"),
    @NamedQuery(name = "User.findByIsAdmin", query = "SELECT u FROM User u WHERE u.isAdmin = :isAdmin")})
public class User implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UserID")
    private Integer userID;
    @Basic(optional = false)
    @Column(name = "Username")
    private String username;
    @Basic(optional = false)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @Column(name = "Password")
    private String password;
    @Column(name = "LastLogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @Basic(optional = false)
    @Column(name = "IsBlocked")
    private boolean isBlocked;
    @Basic(optional = false)
    @Column(name = "IsActive")
    private boolean isActive;
    @Basic(optional = false)
    @Column(name = "IsAdmin")
    private boolean isAdmin;

    public User() {
    }

    public User(Integer userID) {
        this.userID = userID;
    }

    public User(Integer userID, String username, String email, String password, boolean isBlocked, boolean isActive, boolean isAdmin) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isBlocked = isBlocked;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        Integer oldUserID = this.userID;
        this.userID = userID;
        changeSupport.firePropertyChange("userID", oldUserID, userID);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        String oldUsername = this.username;
        this.username = username;
        changeSupport.firePropertyChange("username", oldUsername, username);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String oldEmail = this.email;
        this.email = email;
        changeSupport.firePropertyChange("email", oldEmail, email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String oldPassword = this.password;
        this.password = password;
        changeSupport.firePropertyChange("password", oldPassword, password);
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        Date oldLastLogin = this.lastLogin;
        this.lastLogin = lastLogin;
        changeSupport.firePropertyChange("lastLogin", oldLastLogin, lastLogin);
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean isBlocked) {
        boolean oldIsBlocked = this.isBlocked;
        this.isBlocked = isBlocked;
        changeSupport.firePropertyChange("isBlocked", oldIsBlocked, isBlocked);
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        boolean oldIsActive = this.isActive;
        this.isActive = isActive;
        changeSupport.firePropertyChange("isActive", oldIsActive, isActive);
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        boolean oldIsAdmin = this.isAdmin;
        this.isAdmin = isAdmin;
        changeSupport.firePropertyChange("isAdmin", oldIsAdmin, isAdmin);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClass.User[ userID=" + userID + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
