package com.example.emergencynotificationsystem.service.twilio.impl;

import com.example.emergencynotificationsystem.configuration.twilio.TwilioConfiguration;
import com.example.emergencynotificationsystem.request.DataRequest;
import com.example.emergencynotificationsystem.service.email.EmailSenderService;
import com.example.emergencynotificationsystem.service.twilio.SmsSender;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final TwilioConfiguration twilioConfiguration;

    private final JavaMailSender mailSender;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration, JavaMailSender mailSender) {
        this.twilioConfiguration = twilioConfiguration;
        this.mailSender = mailSender;
    }

    @Override
    public void sendMessage(DataRequest dataRequest) {
        for(String contact : dataRequest.getContacts()){
            if(Character.isDigit(contact.charAt(contact.length() - 1))){
                if (isPhoneNumberValid(contact)) {
                    PhoneNumber to = new PhoneNumber(contact);
                    PhoneNumber from = new PhoneNumber(twilioConfiguration.getPhoneNumber());
                    String message = dataRequest.getMessage();
                    MessageCreator creator = Message.creator(to, from, message);
                    creator.create();
                    LOGGER.info("Send sms {}", dataRequest);
                } else {
                    throw new IllegalArgumentException(
                            "Phone number [" + dataRequest.getContacts() + "] is not a valid number"
                    );
                }
            }
            else {
                emailSenderService.sendEmail(contact,"This is important", dataRequest.getMessage());
            }
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator
        return true;
    }
}