package com.example.emergencynotificationsystem.service.twilio;

import com.example.emergencynotificationsystem.request.SmsRequest;

public interface SmsSender {

    void sendSms(SmsRequest smsRequest);

    // or maybe void sendSms(String phoneNumber, String message);
}