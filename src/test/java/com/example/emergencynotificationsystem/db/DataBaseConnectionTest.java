package com.example.emergencynotificationsystem.db;

import com.example.emergencynotificationsystem.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.emergencynotificationsystem.repository.UserRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class DataBaseConnectionTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void testCreateUser(){
        User user = new User();
        user.setName("Test");
        user.setSurname("Test");
        user.setContact("myGmail@gmail.com");

        int beforeSize = userRepository.findAll().size();

        userRepository.save(user);
        Optional<User> actual = userRepository.findUserByContact(user.getContact());

        assertEquals(user.getName(), actual.get().getName());
        assertEquals(user.getSurname(), actual.get().getSurname());
        assertEquals(user.getContact(), actual.get().getContact());
        assertNotEquals(beforeSize, userRepository.findAll().size());
    }

}
