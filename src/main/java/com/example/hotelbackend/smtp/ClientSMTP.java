package com.example.hotelbackend.smtp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class ClientSMTP {

    private static final String USERNAME = "royalresidencecontact@gmail.com";

    @Value("${smtp.password}")
    private String PASSWORD;
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;

    public void sendEmail(String recipient, String subject, String text) {
        Properties props = initProperties();
        Session session = createSession(props);

        try {
            sendMessage(recipient, session, subject,text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(String recipient, Session session, String subject, String text) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(text);

        Transport.send(message);
    }

    private static Properties initProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        return props;
    }

    private Session createSession(Properties props){
        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });
    }
}