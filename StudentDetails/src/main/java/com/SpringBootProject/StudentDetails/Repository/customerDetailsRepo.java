package com.SpringBootProject.StudentDetails.Repository;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.SpringBootProject.StudentDetails.Model.CombinedProduct;
import com.SpringBootProject.StudentDetails.Model.CustomerModel;
import com.SpringBootProject.StudentDetails.Model.Orders;
import com.SpringBootProject.StudentDetails.Model.Product;
import com.SpringBootProject.StudentDetails.Model.StudentModel;


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


	public int CustomerLogin(String name, String password) {
	    try {
	        // Query to check if a customer with the given name and password exists
	        String loginQuery = "SELECT customerid FROM customerDetails WHERE customername = ? AND password = ?";
	        return jdbcTemplate.update(loginQuery,name,password);
	       
	    } catch (Exception e) {
	        logger.error("Error occurred while performing customer login: {}", e.getMessage());
	        return -1; // Indicate failure
	    }
	}
	
	public  List<CombinedProduct> purchasetheproduct(Orders orders) {
	    try {
	    	float totalPrice = 0;
	       
	    	System.out.println("inside the repo");
	    	// Assuming you have a variable named 'orders' of type Orders containing the customer ID
	    	String customerDetailsQuery = "SELECT * FROM customerDetails WHERE customerid = ?";
	        
	        // Execute the query and map the result to a CustomerModel object
	        List<CustomerModel> customermodel=jdbcTemplate.query(
	                customerDetailsQuery, 
	                new BeanPropertyRowMapper<>(CustomerModel.class),orders.getCustomerId());
	        System.out.println(customermodel);
	        List<Product> product=orders.getProducts();	       
	    	System.out.println(product);
	    	
	    	 List<CombinedProduct> combinedProducts = new ArrayList<>();

	         // Loop through the list of products
	         for (Product product1 : product) {
	             // Fetch product details along with associated shop details using SQL JOIN
	             String combinedQuery = "SELECT p.product_id, p.product_name, p.price, s.shopName " +
	                                    "FROM products p " +
	                                    "INNER JOIN Shops s ON p.shop_id = s.shopId " +
	                                    "WHERE p.product_id = ?";
	             
	             CombinedProduct combinedProduct = jdbcTemplate.queryForObject(
	                     combinedQuery,
	                     new BeanPropertyRowMapper<>(CombinedProduct.class),
	                     product1.getProductId()
	             );
	       
	    	// Calculate total amount for the product and set it in CombinedProduct
            float totalAmount = combinedProduct.getPrice() * product1.getQuantity();
            combinedProduct.setQunatity(product1.getQuantity());
            combinedProduct.setTotalAmount(totalAmount);

            combinedProducts.add(combinedProduct);
            totalPrice += totalAmount;
        }

        // Extract customer details from the customer object
        String customerName = customermodel.get(0).getCustomerName();
        String phone = customermodel.get(0).getPhonenumber();
        String email = customermodel.get(0).getEmail();

        // Set customer details, total price, transaction ID, date, and time to each combined product
        for (CombinedProduct combinedProduct : combinedProducts) {
            combinedProduct.setCustomerName(customerName);
            combinedProduct.setPhone(phone);
            combinedProduct.setEmail(email);
            combinedProduct.setTotalPrice(totalPrice);
            combinedProduct.setTransaction_id(orders.getTransactionId()); // Example transaction ID
            combinedProduct.setTransaction_date(orders.getTransaction_date()); // Example transaction date
            combinedProduct.setTransaction_time(orders.getTransaction_time()); // Example transaction time
        }

        System.out.println(combinedProducts);
    
	          
	        return combinedProducts;
	    } catch (Exception e) {
	        logger.error("Error occurred while performing purchase operation: {}", e.getMessage());
	        return null; // Indicate failure
	    }
	}

}
