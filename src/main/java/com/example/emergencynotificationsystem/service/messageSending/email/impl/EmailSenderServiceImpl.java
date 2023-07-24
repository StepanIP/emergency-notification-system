package com.example.emergencynotificationsystem.service.messageSending.email.impl;

import com.example.emergencynotificationsystem.configuration.email.MailConfiguration;
import com.example.emergencynotificationsystem.service.messageSending.email.EmailSenderService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    private final MailConfiguration mailConfiguration;

    public EmailSenderServiceImpl(JavaMailSender mailSender, MailConfiguration mailConfiguration) {
        this.mailSender = mailSender;
        this.mailConfiguration = mailConfiguration;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public MailConfiguration getMailConfiguration() {
        return mailConfiguration;
    }

    @Override
    public void sendEmail(String to, String subject, String message) {
        if(mailConfiguration.getUsername()==null){
            throw new NullPointerException("Error, Sender can not be null");
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailConfiguration.getUsername());
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        this.mailSender.send(simpleMailMessage);
    }
}