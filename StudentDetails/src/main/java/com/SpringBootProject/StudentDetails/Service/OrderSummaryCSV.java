package com.SpringBootProject.StudentDetails.Service;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.SpringBootProject.StudentDetails.Model.CombinedProduct;

public class OrderSummaryCSV {

    public static ByteArrayInputStream OrdersToCSV(List<CombinedProduct> orders) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // Append headers
            StringBuilder csvData = new StringBuilder();
            csvData.append("Customer Details,");
            csvData.append(orders.get(0).getCustomerName()).append(",").append(orders.get(0).getEmail()).append(",").append(orders.get(0).getPhone()).append("\n");

            csvData.append("Transaction ID,Product Name,Quantity,Price,Total Amount\n");

            // Populate data rows
            for (CombinedProduct product : orders) {
                csvData.append(product.getProduct_id()).append(",")
                        .append(product.getProduct_name()).append(",")
                        .append(product.getQunatity()).append(",")
                        .append(product.getPrice()).append(",")
                        .append(product.getTotalAmount()).append("\n");
            }

            out.write(csvData.toString().getBytes());

            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	public static InputStream GenerateError(List<CombinedProduct> orderDetails2) {
		
		    try {
		        ByteArrayOutputStream out = new ByteArrayOutputStream();

		        // Append headers
		        StringBuilder csvData = new StringBuilder();
		        csvData.append("Error Report: Missing Products\n\n");
		        csvData.append("Product ID,Error Message\n");

		        // Populate data rows
		        for (Integer productId : orderDetails2.get(0).getMissingproducts()) {
		            csvData.append(productId).append(",");
		            csvData.append("Product ID ").append(productId).append(" is not found!\n");
		        }

		        out.write(csvData.toString().getBytes());

		        return new ByteArrayInputStream(out.toByteArray());
		    } catch (IOException e) {
		        e.printStackTrace();
		        return null;
		    }
	}
}

