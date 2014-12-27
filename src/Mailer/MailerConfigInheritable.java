/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mailer;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    private Authenticator authenticator;

    public void setup(String host, String user, String password, int port) {
        this.setHost(host);
        this.setPort(port); //587
        this.setUsername(user);
        this.setPassword(password);
        initializePropertise();
    }

    public void initializePropertise() {
        setPropertise(new Properties());
        getPropertise().put("mail.smtp.starttls.enable", "true");
        getPropertise().put("mail.smtp.host", getHost());
        getPropertise().put("mail.smtp.user", getUsername());
        getPropertise().put("mail.smtp.password", getPassword());
        getPropertise().put("mail.smtp.port", getPort());
        getPropertise().put("mail.smtp.auth", "true");
        // creates a new session with an authenticator
        authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getUsername(), getPassword());
            }
        };
    }

    public boolean sendEmail(String to, String subject, String message) {
        return sendEmail(getUsername(), to, subject, message);
    }

    public boolean sendEmail(String from, String to, String subject, String message) {
        try {

            // sets SMTP server properties
         
            // creates a new session with an authenticator
     
            Session session = Session.getInstance(getPropertise(), authenticator);

            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(getUsername()));
            InternetAddress[] toAddresses = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            // set plain text message
            msg.setContent(message, "text/html");

            // sends the e-mail
            Transport.send(msg);

            return true;
        } catch (javax.mail.MessagingException ex) {
            Logger.getLogger(Gmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
    public Properties getPropertise() {
        return systemPropertise;
    }

    /**
     * @param systemPropertise the systemPropertise to set
     */
    public void setPropertise(Properties systemPropertise) {
        this.systemPropertise = systemPropertise;
    }

    /**
     * @return the authenticator
     */
    public Authenticator getAuthenticator() {
        return authenticator;
    }

    /**
     * @param authenticator the authenticator to set
     */
    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }
}
