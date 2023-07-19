package com.example.emergencynotificationsystem.configuration.email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.mail")
@NoArgsConstructor
@Setter
@Getter
public class MailConfiguration {
    String username;
    String password;
}
