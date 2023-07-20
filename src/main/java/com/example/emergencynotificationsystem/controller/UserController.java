package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.model.User;
import com.example.emergencynotificationsystem.request.UserRequest;
import com.example.emergencynotificationsystem.service.UserService;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/ENS-Ukraine/user")
public class UserController {

    @Autowired
    UserService userService;

    @SneakyThrows
    @PostMapping("/add")
    public void addUsers(@RequestParam("file") MultipartFile file) {
            if (!file.isEmpty() && (Objects.requireNonNull(file.getOriginalFilename()).endsWith(".xls") || file.getOriginalFilename().endsWith(".xlsx"))) {
                try (InputStream inputStream = file.getInputStream()) {
                    Workbook workbook = WorkbookFactory.create(inputStream);
                    Sheet sheet = workbook.getSheetAt(0);

                    for (Row row : sheet) {
                        String firstName = row.getCell(0).getStringCellValue();
                        String lastName = row.getCell(1).getStringCellValue();
                        String contact = row.getCell(2).getStringCellValue();

                        User user = new User();
                        user.setName(firstName);
                        user.setSurname(lastName);
                        user.setContact(contact);
                        userService.create(user);
                    }
                }
            } else {
                throw new IllegalArgumentException("Invalid file or format.");
            }
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody UserRequest user) {
        userService.delete(userService.readByContact(user.getContact()).getId());
    }

    @PutMapping("/edit")
    public void editUser(@RequestBody UserRequest user) {
        userService.update(userService.readByContact(user.getContact()));
    }
}
