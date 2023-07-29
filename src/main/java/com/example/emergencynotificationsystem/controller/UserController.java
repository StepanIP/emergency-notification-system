package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.model.User;
import com.example.emergencynotificationsystem.request.UserRequest;
import com.example.emergencynotificationsystem.service.RoleService;
import com.example.emergencynotificationsystem.transformer.UserTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.emergencynotificationsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ENS-Ukraine/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public void register(@RequestBody UserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        LOGGER.info("Password encoded successfully.");
        User user = UserTransformer.transformToEntity(userRequest, roleService.readByName("USER"));
        userService.create(user);
        LOGGER.info("User " + user.getName() + " registered successfully.");
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody UserRequest userRequest) {
        LOGGER.info("Received request to delete a user with name: {}", userRequest.getEmail());
        userService.delete(userService.readByEmail(userRequest.getEmail()));
        LOGGER.info("User deleted successfully.");
    }

    @PutMapping("/edit")
    public void editUser(@RequestBody UserRequest userRequest) {
        LOGGER.info("Received request to edit a user with name: {}", userRequest.getEmail());
        userService.update(userService.readByEmail(userRequest.getEmail()));
        LOGGER.info("User edited successfully.");
    }


}
