package com.example.emergencynotificationsystem.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginRequest {
    private String username;
    private String password;
}