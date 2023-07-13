package com.example.emergencynotificationsystem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SmsRequest{
    @NotBlank String phoneNumber;
    @NotBlank String message;

    public SmsRequest(@JsonProperty("phoneNumber") String phoneNumber,
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