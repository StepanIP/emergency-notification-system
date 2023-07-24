package com.example.emergencynotificationsystem.service.messageSending.twilio;

import com.example.emergencynotificationsystem.request.DataRequest;

public interface SmsSender {

    void sendMessage(String phoneNumber, String message);
}