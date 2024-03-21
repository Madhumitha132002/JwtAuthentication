package com.SpringBootProject.StudentDetails.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.SpringBootProject.StudentDetails.Model.CombinedProduct;

public class OrderSummary {

    public static ByteArrayInputStream OrdersToExcel(List<CombinedProduct> orders) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // Create a new sheet
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Order Details");

            // Create customer details row
            Row customerRow = sheet.createRow(0);
            Cell customerCell = customerRow.createCell(0);
            customerCell.setCellValue("Customer Details: " + orders.get(0).getCustomerName() + ", " + orders.get(0).getEmail() + ", " + orders.get(0).getPhone());

            // Create header row
            Row headerRow = sheet.createRow(1);
            String[] columns = {"Transaction ID", "Product Name", "Quantity", "Price", "Total Amount"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Populate data rows
            int rowNum = 2; // Start from the third row (after customer details and header)
            for (CombinedProduct product : orders) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getProduct_id());
                row.createCell(1).setCellValue(product.getProduct_name());
                row.createCell(2).setCellValue(product.getQunatity());
                row.createCell(3).setCellValue(product.getPrice());
                row.createCell(4).setCellValue(product.getTotalAmount());
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            workbook.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream GenerateError(List<CombinedProduct> orderDetails1) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // Create a new sheet
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("GenerateErrorMessage");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Product ID", "Error Message"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Populate data rows with missing product IDs
            int rowNum = 1; // Start from the second row (after the header)
            for (CombinedProduct product : orderDetails1) {
                List<Integer> missingProductIds = product.getMissingproducts();
                for (Integer productId : missingProductIds) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(productId);
                    row.createCell(1).setCellValue("Product ID " + productId + " is not found!");
                }
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            workbook.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

	} 
	}

