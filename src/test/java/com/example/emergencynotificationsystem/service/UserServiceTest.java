package com.example.emergencynotificationsystem.service;

import com.example.emergencynotificationsystem.exception.NullEntityReferenceException;
import com.example.emergencynotificationsystem.model.Notification;
import com.example.emergencynotificationsystem.model.User;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void createNotificationTest(){
        User user = new User();
        user.setName("Test");
        user.setSurname("Test");
        user.setContact("test@gmail.com");
        List<User> before = userService.getAll();

        User user1 = userService.create(user);

        assertEquals(before.size()+1, userService.getAll().size());
        assertEquals(user, userService.readByContact(user.getContact()));
    }

    @Test
    public void createNotificationExceptionTest(){
        assertThrows(NullEntityReferenceException.class, () -> userService.create(null));
    }

    @Test
    public void readByIdNotificationTest(){
        User user = new User();
        user.setName("Test");
        user.setSurname("Test");
        user.setContact("test@gmail.com");

        User user1 = userService.create(user);

        assertEquals(user, userService.readById(user.getId()));
    }

    @Test
    public void readByIdNotificationExceptionTest(){
        User user = new User();
        user.setName("Test");
        user.setSurname("Test");
        user.setContact("test@gmail.com");

        User user1 = userService.create(user);

        assertThrows(EntityNotFoundException.class, () -> userService.readById(user.getId()+1));
    }

    @Test
    public void updateNotificationTest(){
        User user = new User();
        user.setName("Test");
        user.setSurname("Test");
        user.setContact("test@gmail.com");

        User user1 = userService.create(user);

        User updateUser = userService.readById(user.getId());
        updateUser.setName("New");
        updateUser.setContact("new@gmail.com");

        userService.update(updateUser);

        assertEquals(updateUser, userService.readById(updateUser.getId()));
    }

    @Test
    public void updateNotificationExceptionTest(){
        assertThrows(NullEntityReferenceException.class, () -> userService.update(null));
    }

    @Test
    public void deleteNotificationTest(){
        User user = new User();
        user.setName("Test");
        user.setSurname("Test");
        user.setContact("test@gmail.com");

        userService.create(user);
        List<User> before = userService.getAll();

        userService.delete(user);

        assertEquals(before.size()-1, userService.getAll().size());
        assertThrows(EntityNotFoundException.class, () -> userService.readByContact(user.getContact()));
    }

    @Test
    public void getAllNotificationsTest(){
        List<User> expected = new ArrayList<>();
        User user = new User();
        user.setName("Test");
        user.setSurname("Test");
        user.setContact("test@gmail.com");
        expected.add(user);

        User user1 = new User();
        user1.setName("Testtwo");
        user1.setSurname("Testtwo");
        user1.setContact("testtwo@gmail.com");
        expected.add(user1);

        userService.create(user);
        userService.create(user1);

        assertEquals(expected.size(), userService.getAll().size());
        assertEquals(expected, userService.getAll());
    }

    @Test
    public void getAllEmptyNotificationsTest(){
        assertEquals(new ArrayList<>(), userService.getAll());
    }

    @Test
    public void readByNameNotificationTest(){
        User user = new User();
        user.setName("Test");
        user.setSurname("Test");
        user.setContact("test@gmail.com");

        userService.create(user);

        assertEquals(user, userService.readByContact(user.getContact()));
    }

    @Test
    public void readByNameNotificationExceptionTest(){
        User user = new User();
        user.setName("Test");
        user.setSurname("Test");
        user.setContact("test@gmail.com");

        userService.create(user);

        assertThrows(EntityNotFoundException.class, () -> userService.readByContact(user.getContact()+1));
    }
}
