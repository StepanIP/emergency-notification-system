package com.example.emergencynotificationsystem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class SmsRequest{
    @NotBlank List<String> phoneNumber;
    @NotBlank String message;

    public SmsRequest(@JsonProperty("phoneNumber") List<String> phoneNumber,
                      @JsonProperty("message") String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    @Override
    public String toString() {
        return "SmsRequest{" +
               "phoneNumber= ..." + '\'' +
               ", message='" + message + '\'' +
               '}';
    }
}