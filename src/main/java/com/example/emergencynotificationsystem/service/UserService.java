package com.example.emergencynotificationsystem.service;

import com.example.emergencynotificationsystem.model.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User readById(long id);
    User update(User user);
    void delete(long id);
    List<User> getAll();
    User readByContact(String contact);
}