package com.SpringBootProject.StudentDetails.Repository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.SpringBootProject.StudentDetails.Model.CustomerModel;


@Repository
public class customerDetailsRepo {

	  private static final Logger logger = LoggerFactory.getLogger(customerDetailsRepo.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public customerDetailsRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

  
    public int addCustomerDetails(CustomerModel CustomerModel) {
        try {
            String insertQuery = "INSERT INTO customerDetails VALUES (?,?,?,?,?,?,?,?)";
            return jdbcTemplate.update(insertQuery, CustomerModel.getCustomerId(), CustomerModel.getCustomerName(),CustomerModel.getPassword(),CustomerModel.getPhonenumber(),
             CustomerModel.getEmail(),CustomerModel.getAge(),CustomerModel.getGender(),CustomerModel.getRole());
            
        } catch (Exception e) {
            logger.error("Error occurred while adding student details: {}", e.getMessage());
            return -1; // Indicate failure
        }
    }
}
