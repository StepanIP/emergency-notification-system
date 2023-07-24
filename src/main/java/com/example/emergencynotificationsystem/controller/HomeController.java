package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.request.DataRequest;
import com.example.emergencynotificationsystem.service.messageSending.DataRequestService;
import com.example.emergencynotificationsystem.service.NotificationService;
import com.example.emergencynotificationsystem.service.UserService;
import com.example.emergencynotificationsystem.service.messageSending.email.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ENS-Ukraine")
public class HomeController {

    @Autowired
    DataRequestService service;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @Autowired
    EmailSenderService emailSenderService;

    @GetMapping
    public ResponseEntity<?> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("notifications", notificationService.getAll());
        response.put("users", userService.getAll());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public void sendMessage(@Valid @RequestBody DataRequest dataRequest) {
        service.sendDataRequest(dataRequest);
    }
}