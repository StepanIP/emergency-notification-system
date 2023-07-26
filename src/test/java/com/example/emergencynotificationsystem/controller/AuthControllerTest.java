package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class AuthControllerTest extends ControllerTestClass{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogin_ValidCredentials_Success() throws Exception {
        String username = "mike@mail.com";
        String password = "1111";

        LoginRequest loginRequest = new LoginRequest(username, password);
        String requestBody = asJsonString(loginRequest);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.accessToken").isString());
    }

    @Test
    public void testLogin_InvalidCredentials_Unauthorized() throws Exception {
        String username = "mike@mail.com";
        String password = "incorrectPassword";

        LoginRequest loginRequest = new LoginRequest(username, password);
        String requestBody = asJsonString(loginRequest);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnauthorized());
    }
}