package com.example.emergencynotificationsystem.service.messageSending.email;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String message);
}