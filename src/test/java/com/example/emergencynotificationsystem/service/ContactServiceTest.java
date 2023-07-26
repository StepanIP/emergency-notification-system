package com.example.emergencynotificationsystem.service;

import com.example.emergencynotificationsystem.exception.NullEntityReferenceException;
import com.example.emergencynotificationsystem.model.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Test
    public void createNotificationTest(){
        Contact contact = new Contact();
        contact.setContact("test@gmail.com");
        contact.setOwner(userService.readById(1));

        List<Contact> before = contactService.getAll();

        Contact contact1 = contactService.create(contact);

        assertEquals(before.size()+1, contactService.getAll().size());
        assertEquals(contact, contactService.readByContact(contact.getContact()));
    }

    @Test
    public void createNotificationExceptionTest(){
        assertThrows(NullEntityReferenceException.class, () -> contactService.create(null));
    }

    @Test
    public void readByIdNotificationTest(){
        Contact contact = new Contact();
        contact.setOwner(userService.readById(1));
        contact.setContact("test@gmail.com");

        Contact contact1 = contactService.create(contact);

        assertEquals(contact, contactService.readById(contact.getId()));
    }

    @Test
    public void readByIdNotificationExceptionTest(){
        Contact contact = new Contact();
        contact.setOwner(userService.readById(1));
        contact.setContact("test@gmail.com");

        Contact contact1 = contactService.create(contact);

        assertThrows(EntityNotFoundException.class, () -> contactService.readById(contact.getId() + 1));
    }

    @Test
    public void updateNotificationTest(){
        Contact contact = new Contact();
        contact.setOwner(userService.readById(1));
        contact.setContact("test@gmail.com");

        Contact contact1 = contactService.create(contact);

        Contact updateContact = contactService.readById(contact.getId());
        updateContact.setOwner(userService.readById(2));
        updateContact.setContact("new@gmail.com");

        contactService.update(updateContact);

        assertEquals(updateContact, contactService.readById(updateContact.getId()));
    }

    @Test
    public void updateNotificationExceptionTest(){
        assertThrows(NullEntityReferenceException.class, () -> contactService.update(null));
    }

    @Test
    public void deleteNotificationTest(){
        Contact contact = new Contact();
        contact.setOwner(userService.readById(1));
        contact.setContact("test@gmail.com");

        contactService.create(contact);
        List<Contact> before = contactService.getAll();

        contactService.delete(contact);

        assertEquals(before.size()-1, contactService.getAll().size());
        assertThrows(EntityNotFoundException.class, () -> contactService.readByContact(contact.getContact()));
    }

    @Test
    public void getAllNotificationsTest(){
        List<Contact> expected = new ArrayList<>();
        Contact contact = new Contact();
        contact.setOwner(userService.readById(1));
        contact.setContact("test@gmail.com");
        expected.add(contact);

        Contact contact1 = new Contact();
        contact1.setOwner(userService.readById(2));
        contact1.setContact("testtwo@gmail.com");
        expected.add(contact1);

        contactService.create(contact);
        contactService.create(contact1);

        assertEquals(expected.size(), contactService.getAll().size());
        assertEquals(expected, contactService.getAll());
    }

    @Test
    public void getAllEmptyNotificationsTest(){
        assertEquals(new ArrayList<>(), contactService.getAll());
    }

    @Test
    public void readByNameNotificationTest(){
        Contact contact = new Contact();
        contact.setOwner(userService.readById(1));
        contact.setContact("test@gmail.com");

        contactService.create(contact);

        assertEquals(contact, contactService.readByContact(contact.getContact()));
    }

    @Test
    public void readByNameNotificationExceptionTest(){
        Contact contact = new Contact();
        contact.setOwner(userService.readById(1));
        contact.setContact("test@gmail.com");

        contactService.create(contact);

        assertThrows(EntityNotFoundException.class, () -> contactService.readByContact(contact.getContact() + 1));
    }
}
