package com.example.emergencynotificationsystem.service.impl;

import com.example.emergencynotificationsystem.exception.NullEntityReferenceException;
import com.example.emergencynotificationsystem.model.Notification;
import com.example.emergencynotificationsystem.repository.NotificationRepository;
import com.example.emergencynotificationsystem.service.NotificationService;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public Notification create(Notification user) {
        if (user != null) {
            return notificationRepository.save(user);
        }
        throw new NullEntityReferenceException("Notification cannot be 'null'");
    }

    @Override
    public Notification readById(long id) {
        return notificationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Notification with id " + id + " not found"));
    }

    @Override
    public Notification update(Notification user) {
        if (user != null) {
            readById(user.getId());
            return notificationRepository.save(user);
        }
        throw new NullEntityReferenceException("Notification cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        notificationRepository.delete(readById(id));
    }

    @Override
    public List<Notification> getAll() {
        List<Notification> users = notificationRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    @Override
    public Notification readByName(String name) {
        return notificationRepository.findUserByName(name);
    }
}
