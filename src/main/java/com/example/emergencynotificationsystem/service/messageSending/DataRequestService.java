package com.example.emergencynotificationsystem.service.messageSending;

import com.example.emergencynotificationsystem.request.DataRequest;
import com.example.emergencynotificationsystem.service.messageSending.email.EmailSenderService;
import com.example.emergencynotificationsystem.service.messageSending.twilio.SmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataRequestService.class);

    @Autowired
    private SmsSender smsSender;

    @Autowired
    private EmailSenderService emailSender;

    public void sendDataRequest(DataRequest dataRequest) {
        LOGGER.info("Sending data request to contacts: {}", dataRequest.getContacts());
        for (String contact : dataRequest.getContacts()) {
            if (Character.isDigit(contact.charAt(contact.length() - 1))) {
                LOGGER.info("Sending SMS to contact: {}", contact);
                smsSender.sendMessage(contact, dataRequest.getMessage());
            } else {
                LOGGER.info("Sending email to contact: {}", contact);
                emailSender.sendEmail(contact, "This is important", dataRequest.getMessage());
            }
        }
        LOGGER.info("Data request sent successfully to all contacts.");
    }
}
