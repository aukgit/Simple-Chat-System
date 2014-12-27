/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mailer;

import java.util.Properties;

/**
 *
 * @author Alim
 */
public class MailerConfigInheritable {

    private String host;
    private int port;
    private String username;
    private String password;
    private Properties systemPropertise;

    public void setup(String host, String user, String password, int port) {
        this.setHost(host);
        this.setPort(port); //587
        this.setUsername(user);
        this.setPassword(password);
        initializePropertise();
    }

    public void initializePropertise() {
        setSystemPropertise(System.getProperties());
        getSystemPropertise().put("mail.smtp.starttls.enable", "true");
        getSystemPropertise().put("mail.smtp.host", getHost());
        getSystemPropertise().put("mail.smtp.user", getUsername());
        getSystemPropertise().put("mail.smtp.password", getPassword());
        getSystemPropertise().put("mail.smtp.port", getPort());
        getSystemPropertise().put("mail.smtp.auth", "true");
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the systemPropertise
     */
    public Properties getSystemPropertise() {
        return systemPropertise;
    }

    /**
     * @param systemPropertise the systemPropertise to set
     */
    public void setSystemPropertise(Properties systemPropertise) {
        this.systemPropertise = systemPropertise;
    }
}
