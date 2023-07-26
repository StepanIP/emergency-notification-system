package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.configuration.email.MailConfiguration;
import com.example.emergencynotificationsystem.model.Contact;
import com.example.emergencynotificationsystem.model.Notification;
import com.example.emergencynotificationsystem.model.User;
import com.example.emergencynotificationsystem.request.DataRequest;
import com.example.emergencynotificationsystem.service.NotificationService;
import com.example.emergencynotificationsystem.service.ContactService;
import com.example.emergencynotificationsystem.service.UserService;
import com.example.emergencynotificationsystem.service.messageSending.DataRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
    private ContactService contactService;

    @Autowired
    private MailConfiguration mailConfiguration;

    @MockBean
    private DataRequestService dataRequestService;

    @MockBean
    UserService userService;

    @Test
    @WithMockUser(username = "mike@mail.com", password = "1111", roles = "USER")
    public void homeControllerTest_Get() throws Exception {
        List<Notification> notifications = Arrays.asList(
                new Notification("1", "Notification 1"),
                new Notification("2", "Notification 2")
        );
        List<Contact> contacts = Arrays.asList(
                new Contact(mailConfiguration.getUsername(), userService.readById(1)),
                new Contact(mailConfiguration.getUsername(), userService.readById(2))
        );

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("notifications", notifications);
        expectedResponse.put("users", contacts);

        when(notificationService.getAll()).thenReturn(notifications);
        when(contactService.getAll()).thenReturn(contacts);

        mockMvc.perform(get("/ENS-Ukraine"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(expectedResponse)));
    }

    @Test
    @WithMockUser(username = "mike@mail.com", password = "1111", roles = "USER")
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
    @WithMockUser(username = "mike@mail.com", password = "1111", roles = "USER")
    public void sendMessageServerError_Post() throws Exception {
        DataRequest invalidDataRequest = new DataRequest();

        mockMvc.perform(post("/ENS-Ukraine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidDataRequest)))
                .andExpect(status().is5xxServerError());

        verify(dataRequestService, never()).sendDataRequest(any(DataRequest.class));
    }
}
