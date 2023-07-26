package com.example.emergencynotificationsystem.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class AuthResponse {
    private String username;
    private String accessToken;
}