package com.example.emergencynotificationsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class ControllerTestClass {

    @SneakyThrows
    static String asJsonString(final Object obj) {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
