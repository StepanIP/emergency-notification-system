package com.example.emergencynotificationsystem.controller;

import com.example.emergencynotificationsystem.model.User;
import com.example.emergencynotificationsystem.request.UserRequest;
import com.example.emergencynotificationsystem.service.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;

import static com.example.emergencynotificationsystem.controller.ControllerTestClass.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testAddUsers_ValidFile() throws Exception {
        File tempFile = createTempExcelFile();

        try (InputStream inputStream = Files.newInputStream(tempFile.toPath())) {
            MockMultipartFile file = new MockMultipartFile("file", "users.xlsx",
                    MediaType.MULTIPART_FORM_DATA_VALUE, inputStream);

            MvcResult result = mockMvc.perform(multipart("/ENS-Ukraine/user/add")
                            .file(file))
                    .andExpect(status().isOk())
                    .andReturn();

            verify(userService, times(3)).create(any(User.class));
        }
    }

    @Test
    void testDeleteUser() throws Exception {
        UserRequest userRequest = new UserRequest("John", "Doe", "12345");

        when(userService.readByContact(userRequest.getContact())).thenReturn(new User());

        mockMvc.perform(delete("/ENS-Ukraine/user/delete")
                        .content(asJsonString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(any(User.class));
    }

    @Test
    void testEditUser() throws Exception {
        UserRequest userRequest = new UserRequest("John", "Doe", "12345");

        when(userService.readByContact(userRequest.getContact())).thenReturn(new User());

        mockMvc.perform(put("/ENS-Ukraine/user/edit")
                        .content(asJsonString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).update(any(User.class));
    }

    private File createTempExcelFile() throws Exception {
        File tempFile = File.createTempFile("users", ".xlsx");
        try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Users");

            Row headerRow = sheet.createRow(0);
            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("First Name");
            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("Last Name");
            Cell headerCell3 = headerRow.createCell(2);
            headerCell3.setCellValue("Email");

            Row row1 = sheet.createRow(1);
            Cell cell1 = row1.createCell(0);
            cell1.setCellValue("John");
            Cell cell2 = row1.createCell(1);
            cell2.setCellValue("Doe");
            Cell cell3 = row1.createCell(2);
            cell3.setCellValue("john.doe@example.com");

            Row row2 = sheet.createRow(2);
            Cell cell4 = row2.createCell(0);
            cell4.setCellValue("Jane");
            Cell cell5 = row2.createCell(1);
            cell5.setCellValue("Smith");
            Cell cell6 = row2.createCell(2);
            cell6.setCellValue("jane.smith@example.com");

            workbook.write(fileOutputStream);
        }
        return tempFile;
    }
}
