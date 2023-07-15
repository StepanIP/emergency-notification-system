package com.example.emergencynotificationsystem.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserRequest {

    String name;

    String surname;

    String contact;

}
