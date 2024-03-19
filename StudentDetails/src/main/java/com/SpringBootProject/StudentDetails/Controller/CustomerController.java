package com.SpringBootProject.StudentDetails.Controller;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootProject.StudentDetails.Model.CombinedProduct;
import com.SpringBootProject.StudentDetails.Model.CustomerModel;
import com.SpringBootProject.StudentDetails.Model.Orders;
import com.SpringBootProject.StudentDetails.Model.Product;
import com.SpringBootProject.StudentDetails.Model.StudentModel;
import com.SpringBootProject.StudentDetails.Service.CustomerService;
import com.SpringBootProject.StudentDetails.Service.MyUserServiceDetails;
import com.SpringBootProject.StudentDetails.Service.PDFGenerator;
import com.SpringBootProject.StudentDetails.Service.PDFGenerator1;
import com.SpringBootProject.StudentDetails.dto.AuthenticationRequest;
import com.SpringBootProject.StudentDetails.util.StudentJWTUtil;

import jakarta.servlet.http.HttpServletResponse;



@RestController
public class CustomerController {
	
	 @Autowired
	  private UserDetailsService userdetailsservice;
	
	 @Autowired
	    private StudentJWTUtil jwtutil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomerService customerservice;
	
	@GetMapping("/hello")
	public void getmessage() {
		System.out.println("Welcome to the Customer details");
	}
	
	@PostMapping("/addcustomer")
	public ResponseEntity<String> addCustomerDetails(@RequestBody CustomerModel CustomerModel)
	{
		System.out.println(CustomerModel.getCustomerName());
		ResponseEntity<String> addCustomerDetails=customerservice.addCustomerDetails(CustomerModel);
		return addCustomerDetails;
		
	}
	
	@PostMapping("/customerLogin")

	    public ResponseEntity<String> authenticateCustomer(@RequestParam String name, @RequestParam String password) {
	        // Perform authentication logic here
	        
		     ResponseEntity<String> CustomerLogin=customerservice.CustomerLogin(name,password);
	        // For demonstration purposes, printing the received username and password
	        System.out.println("Received username: " + name);
	        System.out.println("Received password: " + password);
	        
	        // Return a response indicating success
	        return ResponseEntity.ok("Authentication successful");
	    }
	
    @PostMapping("/Order")
    public ResponseEntity<InputStreamResource> productorders(@RequestBody Orders orders) {
        String filename = "Bill.pdf";

        // Set the current date and time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // Set the transaction_date and transaction_time fields
        orders.setTransaction_date(currentDate);
        orders.setTransaction_time(currentTime);

        System.out.println(orders);

        // Purchase the products and get the order details
        ResponseEntity<List<CombinedProduct>> orderDetailsResponse = customerservice.purchasetheproduct(orders);
        List<CombinedProduct> orderDetails = orderDetailsResponse.getBody();
        System.out.println(orderDetails);

//         Generate PDF bill
        ByteArrayInputStream bis = PDFGenerator1.generateBill( orderDetails);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
    }
//	public ResponseEntity<Resource> getPDFFileUser(@PathVariable int StudentId) {
//		  String filename = "studentdetail.pdf";
//		// Get the StudentModel object by studentId
//	      ResponseEntity<StudentModel> getStudentById = studentService.findById(StudentId);
//	      StudentModel student = getStudentById.getBody();
//	      
//	      // Create a list and add the student object to it
//	      List<StudentModel> studentList = new ArrayList<>();
//	      studentList.add(student);
//	      InputStreamResource file = new InputStreamResource(PDFGenerator.generator(studentList));
//	      return ResponseEntity.ok()
//	     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//	     .contentType(MediaType.APPLICATION_PDF)
//       .body(file);
//	  }
