/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mailer;

import ConsolePackage.Console;

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
    
    public static void main(String[] args) {
        Gmail mail = new Gmail("testmailer.why@gmail.com", "asdf1234@");
        final String to = "devorg.bd@gmail.com";
        final String subject = "Test Subject";
        final String body = "Mail body message <h1>hello</h1>";
        mail.sendEmail(to, subject, body);
        Console.writeLine("mail sent.");
    }
}
