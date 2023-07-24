package com.example.emergencynotificationsystem.service;

import com.example.emergencynotificationsystem.configuration.twilio.TwilioConfiguration;
import com.example.emergencynotificationsystem.service.messageSending.twilio.impl.TwilioSmsSender;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TwilioSmsSenderTest {

    private static final String VALID_PHONE_NUMBER = "+380681918275";
    private static final String INVALID_PHONE_NUMBER = "invalid_number";
    private static final String MESSAGE = "Test message";

    @Mock
    private TwilioConfiguration twilioConfiguration;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private TwilioSmsSender twilioSmsSender;

    @Test
    public void testSendMessage_ValidPhoneNumber_Successful() {
        when(twilioSmsSender.getTwilioConfiguration().getPhoneNumber()).thenReturn("+12344054366");
        TwilioSmsSender spySmsSender = spy(twilioSmsSender);

        //To not spend my free trial money on tests :)
        doNothing().when(spySmsSender).sendMessage(anyString(), anyString());

        spySmsSender.sendMessage(VALID_PHONE_NUMBER, MESSAGE);

        verify(mailSender, never()).send(any(MimeMessage.class));
        verify(spySmsSender, times(1)).sendMessage(VALID_PHONE_NUMBER, MESSAGE);
    }

    @Test
    public void testSendMessage_InvalidPhoneNumber_ExceptionThrown() {
        TwilioSmsSender spySmsSender = spy(twilioSmsSender);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            spySmsSender.sendMessage(INVALID_PHONE_NUMBER, MESSAGE);
        });

        String expectedMessage = "Phone number [" + INVALID_PHONE_NUMBER + "] is not a valid number";
        String actualMessage = exception.getMessage();
        org.junit.jupiter.api.Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
