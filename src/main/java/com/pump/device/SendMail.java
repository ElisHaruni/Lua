package com.pump.device;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;


public abstract class SendMail {

    private static final String SMTP_HOST_NAME = "smtps.aruba.it";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "lua@emptyingthebuffer.com";
    private static final String SMTP_AUTH_PWD  = "projectlua";

    public static void send(String message_body) throws Exception{
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");

        Session mailSession = Session.getDefaultInstance(props);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(SMTP_AUTH_USER));  
        message.setSubject("!ALARM! Insuline Pump !ALARM!");
        message.setContent(message_body, "text/plain");

        message.addRecipient(Message.RecipientType.TO,
             new InternetAddress("lua@emptyingthebuffer.com"));

        transport.connect
          (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}