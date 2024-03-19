package com.SpringBootProject.StudentDetails.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	  public List<CombinedProduct> purchasetheproduct(Orders orders) {
	        try {
	        	float totalAmount=0;
	        	float totalPrice=0;
	            System.out.println("inside the repo");
	            
	            String customerDetailsQuery = "SELECT * FROM customerDetails WHERE customerid = ?";
	            List<CustomerModel> customermodel = jdbcTemplate.query(
	                    customerDetailsQuery,
	                    new BeanPropertyRowMapper<>(CustomerModel.class),
	                    orders.getCustomerId());
	            
	            System.out.println(customermodel);
	            List<Product> products = orders.getProducts(); // Assuming you have a method to retrieve products
	            List<Integer> productIds = new ArrayList<>();
	            List<Integer> productQuantities = new ArrayList<>();

	            for (Product product : products) {
	                productIds.add(product.getProductId());
	                productQuantities.add(product.getQuantity());
	            }

	            // Now you have two separate arrays for product IDs and quantities
	            System.out.println("Product IDs: " + productIds);
	            System.out.println("Product Quantities: " + productQuantities);


	         // Construct the SQL query with the correct 
	            StringBuilder combinedQuery = new StringBuilder("SELECT p.product_id, p.product_name, p.price, s.shopName " +
	                                   "FROM products p " +
	                                   "INNER JOIN Shops s ON p.shop_id = s.shopId " +
	                                   "WHERE p.product_id IN (");
	            
	            for (int i = 0; i < productIds.size(); i++) {
	                combinedQuery.append("?");
	                if (i < productIds.size() - 1) {
	                    combinedQuery.append(", ");
	                }
	            }
	            combinedQuery.append(")");

	            // Execute the SQL query with the dynamically constructed query string and product IDs
	            List<CombinedProduct> combinedProducts = jdbcTemplate.query(
	                    combinedQuery.toString(),
	                    new BeanPropertyRowMapper<>(CombinedProduct.class),
	                    productIds.toArray() // Pass the array of product IDs as arguments
	            );

	            // Print the retrieved combined products
	            System.out.println(combinedProducts);


	// Calculate total price and set total amount for each combined product
	for (CombinedProduct combinedProduct : combinedProducts) {
	    totalAmount = combinedProduct.getPrice() * productQuantities.get(combinedProducts.indexOf(combinedProduct));
	    combinedProduct.setQunatity(productQuantities.get(combinedProducts.indexOf(combinedProduct)));
	    totalPrice+=totalAmount;
	    combinedProduct.setTotalAmount(totalAmount);
	    combinedProduct.setTotalPrice(totalPrice);
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
	    combinedProduct.setTotalPrice(totalPrice); // Assuming you have calculated this earlier
	    combinedProduct.setTransaction_id(orders.getTransactionId()); // Example transaction ID
	    combinedProduct.setTransaction_date(orders.getTransaction_date()); // Example transaction date
	    combinedProduct.setTransaction_time(orders.getTransaction_time()); // Example transaction time
	}

	System.out.println(combinedProducts);
	return combinedProducts;

	        } catch (Exception e) {
	            // Handle exceptions
	            e.printStackTrace();
	            return null;
	        }
	    }
}
