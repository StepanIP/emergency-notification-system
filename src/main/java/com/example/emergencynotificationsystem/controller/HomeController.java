package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.request.SmsRequest;
import com.example.emergencynotificationsystem.service.twilio.impl.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/ENS-Ukraine")
public class HomeController {

    private final SmsService service;

    @Autowired
    public HomeController(SmsService service) {
        this.service = service;
    }

    @PostMapping
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        service.sendSms(smsRequest);
    }
}