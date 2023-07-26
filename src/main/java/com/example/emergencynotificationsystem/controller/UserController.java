package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.model.Contact;
import com.example.emergencynotificationsystem.request.UserRequest;
import com.example.emergencynotificationsystem.service.ContactService;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/ENS-Ukraine/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ContactService contactService;

    @SneakyThrows
    @PostMapping("/add")
    public void addUsers(@RequestParam("file") MultipartFile file) {
        LOGGER.info("Received request to add users from an Excel file.");

        if (!file.isEmpty() && (Objects.requireNonNull(file.getOriginalFilename()).endsWith(".xls") || file.getOriginalFilename().endsWith(".xlsx"))) {
            try (InputStream inputStream = file.getInputStream()) {
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);

                for (Row row : sheet) {
                    String contact = row.getCell(0).getStringCellValue();

                    Contact contact1 = new Contact();
                    contact1.setContact(contact);
                    contactService.create(contact1);
                }
            }
            LOGGER.info("Users added successfully.");
        } else {
            LOGGER.error("Invalid file or format.");
            throw new IllegalArgumentException("Invalid file or format.");
        }
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody UserRequest user) {
        LOGGER.info("Received request to delete user with contact: {}", user.getContact());
        contactService.delete(contactService.readByContact(user.getContact()));
        LOGGER.info("Contact deleted successfully.");
    }

    @PutMapping("/edit")
    public void editUser(@RequestBody UserRequest user) {
        LOGGER.info("Received request to edit user with contact: {}", user.getContact());
        contactService.update(contactService.readByContact(user.getContact()));
        LOGGER.info("Contact edited successfully.");
    }
}

