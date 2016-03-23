package org.registrator.community.mailer;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

    private Session session = null;

    private static String emailSenderAddress = "5zydx2ni.esb@20mail.it";
    private static String emailSubject = "New password for Registrator application";
    private String emailText = "Hi!\n You or someone has requested new password for your account of Registrator application.\n" +
            "Click the link below to get new password:";

    Mailer(){

        Properties sessionProperties = new Properties();
        sessionProperties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        sessionProperties.put("mail.smtp.user", "j2529823@trbvm.com");
        sessionProperties.put("mail.smtp.port", "25");
        sessionProperties.put("mail.smtp.auth", "true");

        MailAuthenticator authenticatorForMessage = new MailAuthenticator();

        session = Session.getInstance(sessionProperties, authenticatorForMessage);
    }

    public void setPropertiesAndSend(String emailRecipient){
        try{
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(emailSenderAddress));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRecipient, false));
            emailMessage.setSubject(emailSubject);
            emailMessage.setSentDate(new Date());
            emailMessage.setText(emailText);

            System.out.println("*** Your email to " + emailRecipient + " has been sent successfully");
        }
        catch (Exception ex){
            System.out.println("*** Your email to " + emailRecipient + " has not been sent due to thrown exception: \n" + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

