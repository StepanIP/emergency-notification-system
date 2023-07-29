package com.example.emergencynotificationsystem.service;

import com.example.emergencynotificationsystem.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role create(Role role);
    Role readById(long id);
    Role update(Role role);
    void delete(long id);
    List<Role> getAll();
    Role readByName(String name);
}
