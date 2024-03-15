package com.SpringBootProject.StudentDetails.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.SpringBootProject.StudentDetails.Model.StudentModel;
import com.SpringBootProject.StudentDetails.Repository.StudentDAO;

@Service
public class StudentDetailsServiceImpl {
    public static final Logger logInfo=LoggerFactory.getLogger(StudentDetailsServiceImpl.class);
    private StudentDAO studentDetailsRepo;

    @Autowired
    public void setStudentDetailsRepo(StudentDAO studentDetailsRepo) {
        this.studentDetailsRepo = studentDetailsRepo;
    }
   
    @Autowired
	private PasswordEncoder encoder;
    
 // In StudentDetailsServiceImpl.java
    public ResponseEntity<String> addStudentDetails(StudentModel studentModel) {
        // Add logging to track the input student model
        logInfo.info("Received student details: {}", studentModel);

        // Validate the studentModel
        if (isEmpty(studentModel.getName()) ||
                isEmpty(studentModel.getRegisterNo()) ||
                isEmpty(studentModel.getGender()) ||
                isEmpty(studentModel.getPhoneNumber()) ||
                isEmpty(studentModel.getCurrentStatus()) ||
                isEmpty(studentModel.getEmailId()) ||
                isEmpty(studentModel.getCourse()) ||
                isEmpty(studentModel.getPassword()) ||
                isEmpty(studentModel.getRole()) ||
                isEmpty(studentModel.getBatch())) {
            logInfo.warn("All fields are required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are required");
        }
        studentModel.setPassword(encoder.encode(studentModel.getPassword()));
        // Process the studentModel and return appropriate response
        int result = studentDetailsRepo.addStudentDetails(studentModel);
        if (result > 0) {
            logInfo.info("Student inserted successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("Student inserted successfully");
        } else {
            logInfo.error("Student record is not inserted");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Student record is not inserted");
        }
    }

    // Helper method to check if a value is empty (null or empty string)
    private boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }
   

    public ResponseEntity<String> deleteStudentDetails(int studentId) {
        int result = studentDetailsRepo.deleteStudentDetails(studentId);
        if(result!=0) {
        	logInfo.info("Student Details deleted successfully");
       	 return ResponseEntity.ok().body("Student Details deleted successfully");
       }
       else {
    	   logInfo.error("StudentDetails is not deleted");
       	 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("StudentDetails is not deleted");
       }
      
    }
  //Update Student Details
    public ResponseEntity<String> updateStudentDetails(StudentModel studentModel) {
    	 if (isEmpty(studentModel.getName()) ||
                 isEmpty(studentModel.getRegisterNo()) ||
                 isEmpty(studentModel.getGender()) ||
                 isEmpty(studentModel.getPhoneNumber()) ||
                 isEmpty(studentModel.getCurrentStatus()) ||
                 isEmpty(studentModel.getEmailId()) ||
                 isEmpty(studentModel.getCourse()) ||
                 isEmpty(studentModel.getBatch())) {
    		 logInfo.warn("All fields are Required");
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are Required");
         }

         int result = studentDetailsRepo.updateStudentDetails(studentModel);
         
         if(result > 0) {
        	 logInfo.info("Student Updates successfully");
         }
        	 else {
        		 logInfo.error("Student Record is not Updated");
        	 }
         return ResponseEntity.status(result > 0 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR)
                 .body(result > 0 ? "Student Updates successfully" : "Student Record is not Updated");
     }
    public ResponseEntity<StudentModel> findById(int studentId) {
        StudentModel student = studentDetailsRepo.findById(studentId);
        if (student == null) {
            logInfo.error("Record not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        logInfo.info("Details retrieved successfully");
        return ResponseEntity.ok().body(student);
    }
    
    public ResponseEntity<List<StudentModel>> getAllStudentDetails() {
        List<StudentModel> students = studentDetailsRepo.getAllStudentDetails();
        if (students.isEmpty()) {
        	logInfo.error("Record not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        logInfo.info("Details Retrived Successfully");
        return ResponseEntity.ok().body(students);
    }  
 
}