package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.request.NotificationRequest;
import com.example.emergencynotificationsystem.service.NotificationService;
import com.example.emergencynotificationsystem.transformer.NotificationTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ENS-Ukraine/notification")
public class NotificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    NotificationService notificationService;

    @PostMapping("/add")
    public void addUsers(@RequestBody NotificationRequest notificationRequest) {
        LOGGER.info("Received request to add a new notification.");
        notificationService.create(NotificationTransformer.transformToEntity(notificationRequest));
        LOGGER.info("Notification added successfully.");
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody NotificationRequest notificationRequest) {
        LOGGER.info("Received request to delete a notification with name: {}", notificationRequest.getName());
        notificationService.delete(notificationService.readByName(notificationRequest.getName()));
        LOGGER.info("Notification deleted successfully.");
    }

    @PutMapping("/edit")
    public void editUser(@RequestBody NotificationRequest notificationRequest) {
        LOGGER.info("Received request to edit a notification with name: {}", notificationRequest.getName());
        notificationService.update(notificationService.readByName(notificationRequest.getName()));
        LOGGER.info("Notification edited successfully.");
    }
}
