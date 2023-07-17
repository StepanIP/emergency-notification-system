package com.example.emergencynotificationsystem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class DataRequest {
    @NotBlank List<String> contacts;
    @NotBlank String message;

    public DataRequest(@JsonProperty("phoneNumber") List<String> contacts,
                       @JsonProperty("message") String message) {
        this.contacts = contacts;
        this.message = message;
    }

    @Override
    public String toString() {
        return "DataRequest{" +
               "phoneNumber= ..." + '\'' +
               ", message='" + message + '\'' +
               '}';
    }
}