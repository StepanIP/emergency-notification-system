package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.model.User;
import com.example.emergencynotificationsystem.request.UserRequest;
import com.example.emergencynotificationsystem.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest extends ControllerTestClass{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @SneakyThrows
    @WithMockUser(username = "test@gmail.com", password = "5b2h1k", roles = "USER")
    void testDeleteUser_SuccessfulDeletion() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        when(userService.readByEmail("test@example.com")).thenReturn(user);

        mockMvc.perform(delete("/ENS-Ukraine/user/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequest)))
                .andExpect(status().isOk());

        verify(userService).readByEmail("test@example.com");
        verify(userService).delete(user);
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "test@gmail.com", password = "5b2h1k", roles = "USER")
    void testEditUser_SuccessfulEdit() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        when(userService.readByEmail("test@example.com")).thenReturn(user);

        mockMvc.perform(put("/ENS-Ukraine/user/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequest)))
                .andExpect(status().isOk());

        verify(userService).readByEmail("test@example.com");
        verify(userService).update(user);
    }
}