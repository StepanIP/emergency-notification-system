package com.example.emergencynotificationsystem.service;

import com.example.emergencynotificationsystem.model.Contact;

import java.util.List;

public interface ContactService {
    Contact create(Contact contact);
    Contact readById(long id);
    Contact update(Contact contact);
    void delete(Contact contact);
    List<Contact> getAll();
    Contact readByContact(String contact);
}