package com.example.emergencynotificationsystem.service.twilio;

import com.example.emergencynotificationsystem.request.DataRequest;

public interface SmsSender {

    void sendMessage(DataRequest dataRequest);
}