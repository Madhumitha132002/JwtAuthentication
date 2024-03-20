package com.SpringBootProject.StudentDetails.Controller;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.SpringBootProject.StudentDetails.Service.OrderSummary;
import com.SpringBootProject.StudentDetails.Service.OrderSummaryCSV;
import com.SpringBootProject.StudentDetails.Service.PDFGenerator;
import com.SpringBootProject.StudentDetails.Service.PDFGenerator1;
import com.SpringBootProject.StudentDetails.dto.AuthenticationRequest;
import com.SpringBootProject.StudentDetails.util.StudentJWTUtil;

import jakarta.servlet.http.HttpServletResponse;

 

@RestController
public class CustomerController {
	
	
	 @Autowired
	    private DataSource dataSource;
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
	 @CrossOrigin(origins = "*")
	@PostMapping("/Order")
	public ResponseEntity<InputStreamResource> productorders(@RequestBody Orders orders) {
	    String filename = "Bill.pdf";
	    
	    System.out.println(orders.getType());

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
	    if (orders.getType().equals("pdf")) {
	        // Create an instance of PDFGenerator1
	        PDFGenerator1 pdfGenerator = new PDFGenerator1(dataSource);

	        // Generate PDF bill
	        ByteArrayInputStream bis = pdfGenerator.generateBill(orderDetails);

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
	    } else if (orders.getType().equals("excel")) {
	        // Extract order details from the Orders object
	        List<CombinedProduct> orderDetails1 = orderDetails; // Assuming getOrderDetails() is a method in your Orders class that returns the list of CombinedProduct

	        // Generate Excel file
	        InputStreamResource file = new InputStreamResource(OrderSummary.OrdersToExcel(orderDetails1));
	        String filename1 = "orders.xlsx"; // Example filename

	  
	        return ResponseEntity.ok()
		              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename1)
		              .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
		              .body(file);
	    }
       else if(orders.getType().equals("csv")) {
	       
    	   List<CombinedProduct> orderDetails2=orderDetails;
    	   
    	   InputStreamResource file=new InputStreamResource(OrderSummaryCSV.OrdersToCSV(orderDetails2));
    	   String filename2="orders.csv";//Filename
    	   
    	   return ResponseEntity.ok()
    				  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
    				  .contentType(MediaType.parseMediaType("application/csv"))
    				  .body(file);
	    }
		return null;    
	}
	
}

