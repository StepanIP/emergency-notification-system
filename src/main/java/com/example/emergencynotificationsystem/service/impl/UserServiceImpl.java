package com.example.emergencynotificationsystem.service.impl;

import com.example.emergencynotificationsystem.exception.NullEntityReferenceException;
import com.example.emergencynotificationsystem.model.User;
import com.example.emergencynotificationsystem.repository.UserRepository;
import com.example.emergencynotificationsystem.service.UserService;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User create(User user) {
        if (user != null) {
            return userRepository.save(user);
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }

    @Override
    public User readById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User update(User user) {
        if (user != null) {
            readById(user.getId());
            return userRepository.save(user);
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    @Override
    public User readByContact(String contact) {
        return userRepository.findUserByContact(contact).orElseThrow(
                () -> new EntityNotFoundException("User with contact " + contact + " not found"));
    }

}
