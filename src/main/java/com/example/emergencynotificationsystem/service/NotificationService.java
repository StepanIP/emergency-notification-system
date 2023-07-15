package com.example.emergencynotificationsystem.service;

import com.example.emergencynotificationsystem.model.Notification;

import java.util.List;

public interface NotificationService {
    Notification create(Notification user);
    Notification readById(long id);
    Notification update(Notification user);
    void delete(long id);
    List<Notification> getAll();
    Notification readByName(String name);
}
