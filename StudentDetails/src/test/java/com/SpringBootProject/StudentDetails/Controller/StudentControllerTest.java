package com.SpringBootProject.StudentDetails.Controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBootProject.StudentDetails.Model.StudentModel;
import com.SpringBootProject.StudentDetails.Service.StudentDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback(true)

public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StudentDetailsServiceImpl studentservice;

   
   
   
    @Test
    @DisplayName("Should add the records to the database")
    void addStudentDetails() throws Exception {
        // Create a sample student model
        StudentModel student = new StudentModel();
        student.setName("Gugan");
        student.setRegisterNo("67");
        student.setGender("Male");
        student.setAge(23);
        student.setPhoneNumber("8098161001");
        student.setCurrentStatus("Undergoing");
        student.setEmailId("Gugan@gmail.com");
        student.setCourse("MCA");
        student.setBatch("set89");
        student.setFees(19000);

          mockMvc.perform(post("/students/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(student)))
        		.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should delete the records from database")
    void deleteStudentDetails() throws Exception {
        mockMvc.perform(delete("/students/delete/{id}", 86))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Should add the records to the database")
    void updateStudentDetails() throws Exception {
      // Create a sample student model
      StudentModel student = new StudentModel();
      student.setName("Gugan");
      student.setRegisterNo("679");
      student.setGender("Male");
      student.setAge(23);
      student.setPhoneNumber("8098161001");
      student.setCurrentStatus("Completed");
      student.setEmailId("Gugan@gmail.com");
      student.setStudentId(86);
      student.setCourse("MCA");
      student.setBatch("set89");
      student.setFees(19000);

        mockMvc.perform(put("/students/update")
              .contentType(MediaType.APPLICATION_JSON)
              .content(asJsonString(student)))
      		.andExpect(status().isCreated());
  }
    @Test
    @DisplayName("Should retrieve all student details")
    void getAllStudentDetails() throws Exception {
        
        ResponseEntity<List<StudentModel>> studentsResponse = studentservice.getAllStudentDetails();
        
        List<StudentModel> students = studentsResponse.getBody();
      
        mockMvc.perform(get("/students/all"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(students.size())));
    }
    @Test
    @DisplayName("Should retrieve particular details of student ")
    void findById() throws Exception {
        int studentId = 86; // Specify the student ID to retrieve
        
        
        ResponseEntity<StudentModel> studentResponse = studentservice.findById(studentId);
        
        
        StudentModel student = studentResponse.getBody();
        
        // Perform the GET request using MockMvc and verify the response status
        mockMvc.perform(get("/students/all"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[?(@.studentId == " + studentId + ")]").exists()); // Check if a student with the specified ID exists in the response
    }
    
    
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}