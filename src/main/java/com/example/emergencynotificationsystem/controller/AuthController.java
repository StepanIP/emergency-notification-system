package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.configuration.jwt.JwtUtils;
import com.example.emergencynotificationsystem.request.LoginRequest;
import com.example.emergencynotificationsystem.response.AuthResponse;
import com.example.emergencynotificationsystem.service.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            LOGGER.info("Attempting login for user: " + loginRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername());
            AuthResponse authResponse = new AuthResponse(user.getUsername(), jwtToken);
            LOGGER.info("User successfully logged in: " + loginRequest.getUsername());
            return ResponseEntity.ok().body(authResponse);
        } catch (BadCredentialsException e) {
            LOGGER.warn("Login failed for user: " + loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}