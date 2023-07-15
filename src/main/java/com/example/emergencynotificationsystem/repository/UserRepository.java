package com.example.emergencynotificationsystem.repository;

import com.example.emergencynotificationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByContact(String contact);
}
