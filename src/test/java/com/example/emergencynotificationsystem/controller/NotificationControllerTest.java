package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.configuration.jwt.JwtUtils;
import com.example.emergencynotificationsystem.model.Notification;
import com.example.emergencynotificationsystem.request.NotificationRequest;
import com.example.emergencynotificationsystem.service.NotificationService;
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
public class NotificationControllerTest extends ControllerTestClass{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    NotificationService notificationService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Test
    @SneakyThrows
    @WithMockUser(username = "mike@mail.com", password = "1111", roles = "USER")
    void createNotification() {
        NotificationRequest request = new NotificationRequest("test", "This is test");

        mockMvc.perform(post("/ENS-Ukraine/notification/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).create(any(Notification.class));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "mike@mail.com", password = "1111", roles = "USER")
    void testDeleteUser() {
        NotificationRequest request = new NotificationRequest("test", "This is test");

        when(notificationService.readByName(request.getName())).thenReturn(new Notification());

        mockMvc.perform(delete("/ENS-Ukraine/notification/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).delete(any(Notification.class));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "mike@mail.com", password = "1111", roles = "USER")
    void testEditUser() {
        NotificationRequest request = new NotificationRequest("test", "This is test");

        when(notificationService.readByName(request.getName())).thenReturn(new Notification());

        mockMvc.perform(put("/ENS-Ukraine/notification/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).update(any(Notification.class));
    }
}
