package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.model.Role;
import com.example.emergencynotificationsystem.model.User;
import com.example.emergencynotificationsystem.request.LoginRequest;
import com.example.emergencynotificationsystem.request.UserRequest;
import com.example.emergencynotificationsystem.service.RoleService;
import com.example.emergencynotificationsystem.service.UserService;
import com.example.emergencynotificationsystem.service.impl.RoleServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
        String username = "test@gmail.com";
        String password = "5b2h1k";

        LoginRequest loginRequest = new LoginRequest(username, password);
        String requestBody = asJsonString(loginRequest);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin_InvalidCredentials_Unauthorized() throws Exception {
        String username = "test@gmail.com";
        String password = "incorrectPassword";

        LoginRequest loginRequest = new LoginRequest(username, password);
        String requestBody1 = asJsonString(loginRequest);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody1))
                .andExpect(status().isUnauthorized());
    }
}