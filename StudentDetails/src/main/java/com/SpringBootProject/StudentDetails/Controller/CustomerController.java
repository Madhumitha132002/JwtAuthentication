package com.SpringBootProject.StudentDetails.Controller;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.SpringBootProject.StudentDetails.Model.CustomerModel;
import com.SpringBootProject.StudentDetails.Service.CustomerService;
import com.SpringBootProject.StudentDetails.Service.MyUserServiceDetails;
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
	@PostMapping("/customerlogin")
	public ResponseEntity<?> getAuthenticate(@RequestBody AuthenticationRequest authenticateRequest,HttpServletResponse response) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getName(),
				authenticateRequest.getPassword()));
		UserDetails userDetails = userdetailsservice.loadUserByUsername(authenticateRequest.getName());
		UserDetails jwt = jwtutil.generateToken(userDetails);
		response.setHeader("Authorization", jwt);
		return new ResponseEntity<String>("User Authenticated successfully.",HttpStatus.OK);
	}

	
}