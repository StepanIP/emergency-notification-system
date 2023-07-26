package com.example.emergencynotificationsystem.repository;

import com.example.emergencynotificationsystem.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findUserByContact(String contact);
}
