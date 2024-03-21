package com.SpringBootProject.StudentDetails.Repository;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.SpringBootProject.StudentDetails.Model.CombinedProduct;
import com.SpringBootProject.StudentDetails.Model.CustomerModel;
import com.SpringBootProject.StudentDetails.Model.Orders;
import com.SpringBootProject.StudentDetails.Model.Product;


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
	            float totalPrice = 0;

	            System.out.println("inside the repo");

	            String customerDetailsQuery = "SELECT * FROM customerDetails WHERE customerid = ?";
	            List<CustomerModel> customermodel = jdbcTemplate.query(
	                    customerDetailsQuery,
	                    new BeanPropertyRowMapper<>(CustomerModel.class),
	                    orders.getCustomerId()
	            );

	            System.out.println(customermodel);
	            List<Product> products = orders.getProducts();
	            List<Integer> productIds = new ArrayList<>();
	            Map<Integer, Integer> productQuantityMap = new HashMap<>();

	            for (Product product : products) {
	                productIds.add(product.getProductId());
	                productQuantityMap.put(product.getProductId(), product.getQuantity());
	            }

	            System.out.println("Product IDs: " + productIds);
	            System.out.println("Product Quantities: " + productQuantityMap);

	            // Check if all product IDs exist in the database
	            boolean allProductIdsExist = true;
	            List<Integer> missingProductIds = new ArrayList<>();

	            String checkProductIdsQuery = "SELECT product_id FROM products WHERE product_id IN (";
	            for (int i = 0; i < productIds.size(); i++) {
	                checkProductIdsQuery += "?";
	                if (i < productIds.size() - 1) {
	                    checkProductIdsQuery += ", ";
	                }
	            }
	            checkProductIdsQuery += ")";

	            List<Integer> existingProductIds = jdbcTemplate.queryForList(checkProductIdsQuery, Integer.class, productIds.toArray());

	            // Iterate through productIds to find missing ones
	            for (Integer productId : productIds) {
	                if (!existingProductIds.contains(productId)) {
	                    missingProductIds.add(productId);
	                }
	            }

	            if (!missingProductIds.isEmpty()) {
	                allProductIdsExist = false;
	                System.out.println("Missing Product IDs: " + missingProductIds);

	                CombinedProduct combinedProductWithMissing = new CombinedProduct();
	                combinedProductWithMissing.setMissingproducts(missingProductIds);
	                List<CombinedProduct> combinedProducts = new ArrayList<>();
	                combinedProducts.add(combinedProductWithMissing);

	                return combinedProducts; // Return combined product with missing IDs
	            }


	            if (allProductIdsExist) {
	                // Construct the SQL query with the correct 
	                StringBuilder combinedQuery = new StringBuilder("SELECT p.product_id, p.product_name, p.price, COALESCE(s.shopName, 'Product Not Found') AS shopName " +
	                        "FROM products p " +
	                        "LEFT JOIN Shops s ON p.shop_id = s.shopId " +
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

	                // Iterate through the combined products
	                for (CombinedProduct combinedProduct : combinedProducts) {
	                    int productId = combinedProduct.getProduct_id();
	                    int quantity = productQuantityMap.getOrDefault(productId, 0); // Get quantity from productQuantities map

	                    if (productQuantityMap.containsKey(productId)) {
	                        float totalAmount = combinedProduct.getPrice() * quantity;

	                        combinedProduct.setQunatity(quantity); // Corrected method name to setQuantity
	                        combinedProduct.setTotalAmount(totalAmount);
	                        combinedProduct.setTotalPrice(totalPrice);

	                        totalPrice += totalAmount;
	                    } else {
	                        // Handle the case where the product ID is not found
	                        System.out.println("Product ID " + productId + " not found in the database.");
	                        combinedProduct.setQunatity(0); // Corrected method name to setQuantity
	                        combinedProduct.setTotalAmount(0);
	                        combinedProduct.setTotalPrice(0);
	                    }
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
	            } else {
	                // At least one product ID does not exist in the database, display an error message or handle it accordingly
	                System.out.println(" Product ID does not exist in the database.");
	                return null; // Return null to indicate failure
	            }
	        } catch (Exception e) {
	            // Handle exceptions
	            e.printStackTrace();
	            return null; // Return null to indicate failure
	        }
	    }


}
