package com.example.emergencynotificationsystem.service.email;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String message);
}