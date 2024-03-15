package com.SpringBootProject.StudentDetails.Controller;
import com.SpringBootProject.StudentDetails.Model.StudentModel;
import org.springframework.web.bind.annotation.RequestBody;
import com.SpringBootProject.StudentDetails.Service.CSVHelper;
import com.SpringBootProject.StudentDetails.Service.Excelhelper;
import com.SpringBootProject.StudentDetails.Service.PDFGenerator;
import com.SpringBootProject.StudentDetails.Service.RefreshTokenService;
import com.SpringBootProject.StudentDetails.Service.StudentDetailsServiceImpl;
import com.SpringBootProject.StudentDetails.dto.AuthenticationRequest;
import com.SpringBootProject.StudentDetails.dto.JwtResponse;
import com.SpringBootProject.StudentDetails.dto.Tokenkey;
import com.SpringBootProject.StudentDetails.model.RefreshToken;
import com.SpringBootProject.StudentDetails.util.StudentJWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost")
//Indicate a particular class servers RESTFUL web servers
@RestController
//Specifies all the request mappings in controller using this base path

public class StudentController {
//
	public static final Logger logInfo=LoggerFactory.getLogger(StudentController.class);
private static final int String = 0;
	  @Autowired
    private  StudentDetailsServiceImpl studentService;
	 
	  @Autowired
	  private UserDetailsService userdetailsservice;
	  
	@Autowired
	  private Excelhelper excelhelper;
	@Autowired
	private AuthenticationManager authenticationManager;
	
    @Autowired
    private StudentJWTUtil jwtutil;
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
   

    
  
	
    @PostMapping("/login")
    public JwtResponse authenticateAndGetToken(@RequestBody AuthenticationRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getName());
            return new JwtResponse.Builder()
                    .accessToken(jwtutil.generateToken(authRequest.getName()))
                    .token(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid user request !");
        }
    }


    @PostMapping("/refresh")
    public JwtResponse refreshToken(@RequestBody Tokenkey token) {
        
      
       // Fetch RefreshToken
        RefreshToken refreshToken = refreshTokenService.findByToken(token.getToken());
        if (refreshToken == null) {
            throw new RuntimeException("Refresh token is not in the database!");
        }
        System.out.println(refreshToken.getStudent_id());
        
        // Verify expiration
        refreshTokenService.verifyExpiration(refreshToken);
        
        // Retrieve student information
        ResponseEntity<StudentModel> responseEntity = studentService.findById(refreshToken.getStudent_id());
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            StudentModel student = responseEntity.getBody();
            System.out.println("Student Name: " + student.getName());
            
            // Generate access token
            String accessToken = jwtutil.generateToken(student.getName());

         // Create JwtResponse
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(accessToken);
            jwtResponse.setToken(token.getToken());
       return jwtResponse;
//            return accessToken;
        } else {
            System.err.println("Error retrieving student: " + responseEntity.getStatusCode());
            throw new RuntimeException("Error retrieving student: " + responseEntity.getStatusCode());
    } 
    }



	  
	  @Operation(
			  description="This Method is used to add the Student Details",
			  summary="It uses the POST request Method ",
			  responses= {
					  @ApiResponse(
							  description="Data Inserted Successfully",
							  responseCode="200"
							  ),
					  @ApiResponse(
							  description="Data is not Inserted",
							  responseCode="403"
							  )	
			  })
	  
	  
	  @PostMapping("/add")   
	    public ResponseEntity<String> addStudent(@RequestBody StudentModel studentModel) { 
		  
		  ResponseEntity<String> addStudentDetails=studentService.addStudentDetails(studentModel);
		  logInfo.info(addStudentDetails.getBody());
	        return addStudentDetails;
	    }
	 
	  @Operation(
			  description="This Method is used to delete the Student Details",
			  summary="It uses the DELETE request Method ",
			  responses= {
					  @ApiResponse(
							  description="Data Deleted Successfully",
							  responseCode="200"
							  ),
					  @ApiResponse(
							  description="Data is not Deleted",
							  responseCode="403"
							  )	
			  })
	  @PreAuthorize("hasAuthority('ROLE_STUDENT')")
     @DeleteMapping("/delete/{studentId}")
     public ResponseEntity<String> deleteStudent(@PathVariable int studentId) {
//    	 logInfo.info("It is inside the controller");
//    	 logInfo.warn("it has the error");
//    	 logInfo.error("It found the error");
//    	 logInfo.debug("It is debug Application");
    	ResponseEntity<String> deleteStudentDetails = studentService.deleteStudentDetails(studentId);
    	logInfo.info(deleteStudentDetails.getBody());
    	return deleteStudentDetails; // Assuming you return the ResponseEntity further
        
    }
	  @Operation(
			  description="This Method is used to update the Student Details",
			  summary="It uses the PUT request Method ",
			  responses= {
					  @ApiResponse(
							  description="Data Updated Successfully",
							  responseCode="200"
							  ),
					  @ApiResponse(
							  description="Data is not Updated",
							  responseCode="403"
							  )	
			  })
	  @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @PutMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentModel studentModel) {
    	ResponseEntity<String> updateStudentDetails=studentService.updateStudentDetails(studentModel);
    	logInfo.info(updateStudentDetails.getBody());
        return updateStudentDetails;    
    }
	  @Operation(
			  description="This Method is used to get Particular Student Details",
			  summary="It uses the GET request Method ",
			  responses= {
					  @ApiResponse(
							  description="Data Retrived Successfully",
							  responseCode="200"
							  ),
					  @ApiResponse(
							  description="Data is not Retrieved",
							  responseCode="403"
							  )	
			  })
	  
	 @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentModel> getStudentById(@PathVariable int studentId) {
		  System.out.println("Helloworld");
        ResponseEntity<StudentModel> getStudentById = studentService.findById(studentId);
       
        StudentModel student = getStudentById.getBody();
        logInfo.info(""+student);
        return getStudentById;
    }

	  
	  @Operation(
			  description="This Method is used to Fetch the Student Details",
			  summary="It uses the GET request Method ",
			  responses= {
					  @ApiResponse(
							  description="Data Retrived Successfully",
							  responseCode="200"
							  ),
					  @ApiResponse(
							  description="Data is not Retrived",
							  responseCode="403"
							  )	
			  })
	  @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/all")
    public ResponseEntity<List<StudentModel>> getAllStudents() {
    	ResponseEntity<List<StudentModel>> getAllStudentDetails=studentService.getAllStudentDetails();
    	List<StudentModel> student=getAllStudentDetails.getBody();
    	logInfo.info(""+student);
        return getAllStudentDetails;
    }
	  
	  @GetMapping("/download/excel")
	  public ResponseEntity<Resource> getFile() {
	      String filename = "students.xlsx";
	      ResponseEntity<List<StudentModel>> getAllStudentDetails = studentService.getAllStudentDetails();
	      List<StudentModel> studentList = getAllStudentDetails.getBody(); // Extracting the list of student models
	      InputStreamResource file = new InputStreamResource(Excelhelper.studentToExcel(studentList));
	      return ResponseEntity.ok()
	              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	              .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	              .body(file);
	  }
	  
	  @GetMapping("/download/excel/{studentId}")
	  public ResponseEntity<Resource> getuserFile(@PathVariable int studentId) {
	      String filename = "studentdetail.xlsx";
	      // Get the StudentModel object by studentId
	      ResponseEntity<StudentModel> getStudentById = studentService.findById(studentId);
	      StudentModel student = getStudentById.getBody();
	      
	      // Create a list and add the student object to it
	      List<StudentModel> studentList = new ArrayList<>();
	      studentList.add(student);
	      // Convert the list of student objects to Excel file
	      InputStreamResource file = new InputStreamResource(Excelhelper.studentToExcel(studentList));
	      return ResponseEntity.ok()
	              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	              .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	              .body(file);
	  } 
	  @GetMapping("/download/CSV")
	  public ResponseEntity<Resource> getCSVFile() {
		  String filename = "students.csv";
		  ResponseEntity<List<StudentModel>> getAllStudentDetails = studentService.getAllStudentDetails();
	      List<StudentModel> studentList = getAllStudentDetails.getBody(); // Extracting the list of student models
	      InputStreamResource file = new InputStreamResource(CSVHelper.studentToCSV(studentList));
	  return ResponseEntity.ok()
	  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	  .contentType(MediaType.parseMediaType("application/csv"))
	  .body(file);
	  }
	  
	  @GetMapping("/download/CSV/{StudentId}")
	  public ResponseEntity<Resource> getCSVFileUser(@PathVariable int StudentId) {
		  String filename = "studentdetail.csv";
		// Get the StudentModel object by studentId
	      ResponseEntity<StudentModel> getStudentById = studentService.findById(StudentId);
	      StudentModel student = getStudentById.getBody();
	      
	      // Create a list and add the student object to it
	      List<StudentModel> studentList = new ArrayList<>();
	      studentList.add(student);
	      InputStreamResource file = new InputStreamResource(CSVHelper.studentToCSV(studentList));
	  return ResponseEntity.ok()
	  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	  .contentType(MediaType.parseMediaType("application/csv"))
	  .body(file);
	  }
	  
	  @GetMapping("/download/PDF")
	    public ResponseEntity<Resource> getPDFFile() {
	        String filename = "students.pdf";
	        ResponseEntity<List<StudentModel>> getAllStudentDetails = studentService.getAllStudentDetails();
	        List<StudentModel> studentList = getAllStudentDetails.getBody(); // Extracting the list of student models

	        InputStreamResource file = new InputStreamResource(PDFGenerator.generator(studentList));
	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(file);
	    }
	  @GetMapping("/download/PDF/{StudentId}")
	  public ResponseEntity<Resource> getPDFFileUser(@PathVariable int StudentId) {
		  String filename = "studentdetail.pdf";
		// Get the StudentModel object by studentId
	      ResponseEntity<StudentModel> getStudentById = studentService.findById(StudentId);
	      StudentModel student = getStudentById.getBody();
	      
	      // Create a list and add the student object to it
	      List<StudentModel> studentList = new ArrayList<>();
	      studentList.add(student);
	      InputStreamResource file = new InputStreamResource(PDFGenerator.generator(studentList));
	      return ResponseEntity.ok()
	     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	     .contentType(MediaType.APPLICATION_PDF)
         .body(file);
	  }




	  
}