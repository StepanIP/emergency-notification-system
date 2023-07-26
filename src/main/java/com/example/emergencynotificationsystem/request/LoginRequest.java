package com.example.emergencynotificationsystem.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}