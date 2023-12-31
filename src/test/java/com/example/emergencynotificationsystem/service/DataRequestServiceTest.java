package com.example.emergencynotificationsystem.service;

import com.example.emergencynotificationsystem.request.DataRequest;
import com.example.emergencynotificationsystem.service.messageSending.DataRequestService;
import com.example.emergencynotificationsystem.service.messageSending.email.EmailSenderService;
import com.example.emergencynotificationsystem.service.messageSending.twilio.SmsSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DataRequestServiceTest {

    @Mock
    private SmsSender smsSender;

    @MockBean
    private EmailSenderService emailSender;

    @InjectMocks
    private DataRequestService dataRequestService;

    @Test
    void testSendDataRequest_SendsSmsToContact() {
        DataRequest dataRequest = new DataRequest();
        dataRequest.setContacts(Collections.singletonList("+1234567890"));
        dataRequest.setMessage("test");

        doNothing().when(smsSender).sendMessage(anyString(), anyString());

        dataRequestService.sendDataRequest(dataRequest);

        verify(smsSender, times(1)).sendMessage(eq("+1234567890"), anyString());
        verify(emailSender, never()).sendEmail(anyString(), anyString(), anyString());
    }


    @Test
    void testSendDataRequest_SendsEmailToContact() {
        DataRequest dataRequest = new DataRequest();
        dataRequest.setContacts(Collections.singletonList("test@example.com"));
        dataRequest.setMessage("Test");

        dataRequestService.sendDataRequest(dataRequest);

        verify(emailSender, times(1)).sendEmail(eq("test@example.com"), eq("This is important"), anyString());
        verify(smsSender, never()).sendMessage(anyString(), anyString());
    }

    @Test
    void testSendDataRequest_SendsBothSmsAndEmail() {
        DataRequest dataRequest = new DataRequest();
        dataRequest.setContacts(Arrays.asList("+1234567890", "test@example.com"));
        dataRequest.setMessage("test");

        doNothing().when(smsSender).sendMessage(anyString(), anyString());
        dataRequestService.sendDataRequest(dataRequest);

        verify(smsSender, times(1)).sendMessage(eq("+1234567890"), anyString());
        verify(emailSender, times(1)).sendEmail(eq("test@example.com"), eq("This is important"), anyString());
    }
}
