package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService{
    private JavaMailSender sender;

    @Value("${mymail}")
    private String ownerEmail;

    @Autowired
    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public boolean sendEmail(String message,String subject)  {
        return sendEmailTool(message,ownerEmail, subject);
    }

    private boolean sendEmailTool(String textMessage, String email, String subject) {
        boolean send = false;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setText(textMessage, true);
            helper.setSubject(subject);
            sender.send(message);
            send = true;

        } catch (MessagingException e) {
            System.out.println("Hubo un error al enviar el mail: "+e);
        }
        return send;
    }
}
