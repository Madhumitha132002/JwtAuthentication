package com.SpringBootProject.StudentDetails.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SpringBootProject.StudentDetails.Model.CustomerModel;
import com.SpringBootProject.StudentDetails.Repository.customerDetailsRepo;




@Service
public class CustomerService {
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	public customerDetailsRepo customerDetailsRepo;
	
	    
	public ResponseEntity<String> addCustomerDetails(CustomerModel CustomerModel){
		

		if(isEmpty(CustomerModel.getCustomerName()) ||
				isEmpty(CustomerModel.getPassword()) ||
				isEmpty(CustomerModel.getPhonenumber()) ||
				isEmpty(CustomerModel.getEmail()) ||
				isEmpty(CustomerModel.getEmail())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are required");
		}
		 CustomerModel.setPassword(encoder.encode(CustomerModel.getPassword()));
		int result=customerDetailsRepo.addCustomerDetails(CustomerModel);
		if (result > 0) {
	        
	         return ResponseEntity.status(HttpStatus.CREATED).body("Customer inserted successfully");
	     } else {
	         
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer record is not inserted");
	     }
		
	}

	
	
	private boolean isEmpty(String customerName) {
		// TODO Auto-generated method stub
		return false;
	}

}
