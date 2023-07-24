package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.configuration.email.MailConfiguration;
import com.example.emergencynotificationsystem.model.Notification;
import com.example.emergencynotificationsystem.model.User;
import com.example.emergencynotificationsystem.request.DataRequest;
import com.example.emergencynotificationsystem.service.NotificationService;
import com.example.emergencynotificationsystem.service.UserService;
import com.example.emergencynotificationsystem.service.messageSending.DataRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class HomeControllerTest extends ControllerTestClass{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private UserService userService;

    @Autowired
    private MailConfiguration mailConfiguration;

    @MockBean
    private DataRequestService dataRequestService;

    @Test
    public void homeControllerTest_Get() throws Exception {
        List<Notification> notifications = Arrays.asList(
                new Notification("1", "Notification 1"),
                new Notification("2", "Notification 2")
        );
        List<User> users = Arrays.asList(
                new User("User 1", "User 1", mailConfiguration.getUsername()),
                new User("User 2", "User 2", mailConfiguration.getUsername())
        );

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("notifications", notifications);
        expectedResponse.put("users", users);

        when(notificationService.getAll()).thenReturn(notifications);
        when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get("/ENS-Ukraine"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(expectedResponse)));
    }

    @Test
    public void sendMessageSuccessful_Post() throws Exception {
        DataRequest validDataRequest = new DataRequest("scrupnichuk@gmail.com", "Test");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(validDataRequest);

        requestBody = requestBody.replaceAll("[\\[\\]]", "");

        mockMvc.perform(post("/ENS-Ukraine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        verify(dataRequestService, times(1)).sendDataRequest(validDataRequest);
    }

    @Test
    public void sendMessageServerError_Post() throws Exception {
        DataRequest invalidDataRequest = new DataRequest();

        mockMvc.perform(post("/ENS-Ukraine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidDataRequest)))
                .andExpect(status().is5xxServerError());

        verify(dataRequestService, never()).sendDataRequest(any(DataRequest.class));
    }
}
