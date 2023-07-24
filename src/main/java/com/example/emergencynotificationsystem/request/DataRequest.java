package com.example.emergencynotificationsystem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class DataRequest {
    List<String> contacts;
    @NotBlank String message;

    public DataRequest(@JsonProperty("contacts") String contacts,
                       @JsonProperty("message") String message) {
        this.contacts = extractContacts(contacts);
        this.message = message;
    }

    @Override
    public String toString() {
        return "DataRequest{" +
               "contacts=" + contacts +
               ", message='" + message + '\'' +
               '}';
    }

    private List<String> extractContacts(String contacts){
        String[] contactArray = contacts.split(", ");
        return new ArrayList<>(Arrays.asList(contactArray));
    }
}