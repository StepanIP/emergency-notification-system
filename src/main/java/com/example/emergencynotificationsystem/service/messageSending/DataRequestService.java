package com.example.emergencynotificationsystem.service.messageSending;

import com.example.emergencynotificationsystem.request.DataRequest;
import com.example.emergencynotificationsystem.service.messageSending.email.EmailSenderService;
import com.example.emergencynotificationsystem.service.messageSending.twilio.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataRequestService {

    @Autowired
    private SmsSender smsSender;

    @Autowired
    private EmailSenderService emailSender;

    public void sendDataRequest(DataRequest dataRequest) {
        for (String contact : dataRequest.getContacts()) {
            if (Character.isDigit(contact.charAt(contact.length() - 1))) {
                smsSender.sendMessage(contact, dataRequest.getMessage());
            } else {
                emailSender.sendEmail(contact, "This is important", dataRequest.getMessage());
            }
        }
    }
}