/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mailer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Alim
 */
public final class Gmail extends MailerConfigInheritable {

    private final String HOST = "smtp.gmail.com";

    public Gmail(String user, String password) {
        setup(HOST, user, password, 587);
    }

    public Gmail(String user, String password, int port) {
        setup(HOST, user, password, port);
    }

    public int SendEmail(String from, String to, String subject, String message) {
        try {
            int flag = 0;
            String sendingTo[] = to.split(",");

            Session session = Session.getDefaultInstance(getSystemPropertise(), null);
            MimeMessage mimemessage = new MimeMessage(session);
            mimemessage.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[sendingTo.length];
            for (int i = 0; i < sendingTo.length; i++) {
                toAddress[i] = new InternetAddress(sendingTo[i]);
            }
            for (InternetAddress toAddres : toAddress) {
                mimemessage.addRecipient(Message.RecipientType.TO, toAddres);
            }
            mimemessage.setSubject(subject);
            mimemessage.setText(message);
            Transport transport = session.getTransport("smtp");
            transport.connect(getHost(), from, getPassword());
            transport.sendMessage(mimemessage, mimemessage.getAllRecipients());
            transport.close();
            flag = 1;

            return flag;
        } catch (javax.mail.MessagingException ex) {
            Logger.getLogger(Gmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static void main(String [] args) {
        Gmail mail = new Gmail("testmailer.why@gmail.com", "asdf1234@");
        final String from = "testmailer.why@gmail.com";
        final String to = "devorg.bd@gmail.com";
        final String subject = "Test Subject";
        final String body = "Mail body message <h1>hello</h1>";
        mail.SendEmail(from, to, subject, body);
    }
}
