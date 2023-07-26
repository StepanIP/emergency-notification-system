package com.example.emergencynotificationsystem.db;

import com.example.emergencynotificationsystem.model.Contact;
import com.example.emergencynotificationsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.emergencynotificationsystem.repository.ContactRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class DataBaseConnectionTest {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void testCreateContact(){
        Contact contact = new Contact();
        contact.setContact("myGmail@gmail.com");
        contact.setOwner(userRepository.findByEmail("mike@mail.com"));

        int beforeSize = contactRepository.findAll().size();

        contactRepository.save(contact);
        Optional<Contact> actual = contactRepository.findUserByContact(contact.getContact());

        assertEquals(contact.getContact(), actual.get().getContact());
        assertNotEquals(beforeSize, contactRepository.findAll().size());
    }

}
