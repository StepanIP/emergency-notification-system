package com.example.emergencynotificationsystem.service.messageSending.twilio.impl;

import com.example.emergencynotificationsystem.configuration.twilio.TwilioConfiguration;
import com.example.emergencynotificationsystem.service.messageSending.email.EmailSenderService;
import com.example.emergencynotificationsystem.service.messageSending.twilio.SmsSender;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final TwilioConfiguration twilioConfiguration;

    public TwilioConfiguration getTwilioConfiguration() {
        return twilioConfiguration;
    }

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration, JavaMailSender mailSender) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendMessage(String phoneNumber, String message) {
        if (isPhoneNumberValid(phoneNumber)) {
            PhoneNumber to = new PhoneNumber(phoneNumber);
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getPhoneNumber());
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            LOGGER.info("Send sms to {}: {}", phoneNumber, message);
        } else {
            throw new IllegalArgumentException("Phone number [" + phoneNumber + "] is not a valid number");
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        phoneNumber = phoneNumber.replaceAll("\\s+", "");
        String regex = "^\\+\\d{1,3}\\d{9}$";
        return phoneNumber.matches(regex);
    }
}