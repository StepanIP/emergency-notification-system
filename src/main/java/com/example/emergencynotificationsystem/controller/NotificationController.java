package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.request.NotificationRequest;
import com.example.emergencynotificationsystem.request.UserRequest;
import com.example.emergencynotificationsystem.service.NotificationService;
import com.example.emergencynotificationsystem.transformer.NotificationTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ENS-Ukraine/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping("/add")
    public void addUsers(@RequestBody NotificationRequest notificationRequest) {
        notificationService.create(NotificationTransformer.transformToEntity(notificationRequest));
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody NotificationRequest notificationRequest) {
        notificationService.delete(notificationService.readByName(notificationRequest.getName()));
    }

    @PutMapping("/edit")
    public void editUser(@RequestBody NotificationRequest notificationRequest) {
        notificationService.update(notificationService.readByName(notificationRequest.getName()));
    }
}
