package com.SpringBootProject.StudentDetails.Service;

import com.SpringBootProject.StudentDetails.Model.CombinedProduct;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PDFGenerator1 {

	 private final DataSource dataSource;

	    public PDFGenerator1(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }

    public ByteArrayInputStream generateBill(List<CombinedProduct> orderDetails) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfPTable customerTable = new PdfPTable(1);
            customerTable.setWidthPercentage(30);
            customerTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

            PdfPCell customerCell = new PdfPCell();
            customerCell.setBorder(PdfPCell.NO_BORDER);
            customerCell.setPadding(5);

            customerCell.addElement(new Paragraph("Customer Name: " + orderDetails.get(0).getCustomerName()));
            customerCell.addElement(new Paragraph("Phone: " + orderDetails.get(0).getPhone()));
            customerCell.addElement(new Paragraph("Email: " + orderDetails.get(0).getEmail()));
            customerCell.addElement(new Paragraph("Date : "+orderDetails.get(0).getTransaction_date()));
            customerCell.addElement(new Paragraph("Time :"+orderDetails.get(0).getTransaction_time()));

            customerTable.addCell(customerCell);

            PdfPTable transactionTable = new PdfPTable(5);
            transactionTable.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell();
            cell.setPadding(5);

            cell.setPhrase(new Paragraph("Transaction ID"));
            transactionTable.addCell(cell);

            cell.setPhrase(new Paragraph("Product Name"));
            transactionTable.addCell(cell);

            cell.setPhrase(new Paragraph("Quantity"));
            transactionTable.addCell(cell);

            cell.setPhrase(new Paragraph("Price"));
            transactionTable.addCell(cell);

            cell.setPhrase(new Paragraph("Total Amount"));
            transactionTable.addCell(cell);

            for (CombinedProduct product : orderDetails) {
                transactionTable.addCell(String.valueOf(product.getProduct_id()));
                transactionTable.addCell(product.getProduct_name());
                transactionTable.addCell(String.valueOf(product.getQunatity()));
                transactionTable.addCell(String.valueOf(product.getPrice()));
                transactionTable.addCell(String.valueOf(product.getTotalAmount()));
            }

            PdfPTable totalTable = new PdfPTable(1);
            totalTable.setWidthPercentage(30);
            totalTable.setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);

            PdfPCell totalCell = new PdfPCell();
            totalCell.setBorder(PdfPCell.NO_BORDER);
            totalCell.setPadding(5);

            totalCell.addElement(new Paragraph("Total Price: " +orderDetails.get(0).getTotalPrice()));

            totalTable.addCell(totalCell);

            PdfWriter.getInstance(document, out);
            document.open();

            document.add(customerTable);
            document.add(new Paragraph(" "));
            document.add(transactionTable);
            document.add(new Paragraph(" "));
            document.add(totalTable);
         // Get the required details from the first order detail (assuming all orders have the same details)
            String customerName = orderDetails.get(0).getCustomerName();
            String phoneNumber = orderDetails.get(0).getPhone();
            String email = orderDetails.get(0).getEmail();
            LocalDate date = orderDetails.get(0).getTransaction_date();
            LocalTime time = orderDetails.get(0).getTransaction_time();
            double totalPrice = orderDetails.get(0).getTotalPrice();
            document.close();
            storePDFToDatabase(out.toByteArray(), customerName, phoneNumber, email, date, time, totalPrice,orderDetails);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    private void storePDFToDatabase(byte[] pdfBytes, String customerName, String phoneNumber, String email, LocalDate date, LocalTime time, double totalPrice, List<CombinedProduct> orderDetails) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlSummary = "INSERT INTO ordersummary (pdf_content, customer_name, phone_number, email, transaction_date, transaction_time, total_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String sqlDetails = "INSERT INTO orderdetails (order_id, product_name, quantity, price, total_amount) VALUES (?, ?, ?, ?, ?)";
            
            // Insert order summary
            try (PreparedStatement statementSummary = connection.prepareStatement(sqlSummary, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement statementDetails = connection.prepareStatement(sqlDetails)) {
                statementSummary.setBytes(1, pdfBytes);
                statementSummary.setString(2, customerName);
                statementSummary.setString(3, phoneNumber);
                statementSummary.setString(4, email);
                statementSummary.setDate(5, java.sql.Date.valueOf(date));
                statementSummary.setTime(6, java.sql.Time.valueOf(time));
                statementSummary.setDouble(7, totalPrice);
                statementSummary.executeUpdate();
                
                // Retrieve the generated order ID
                ResultSet generatedKeys = statementSummary.getGeneratedKeys();
                int orderId = -1;
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                }
                
                // Insert order details
                for (CombinedProduct product : orderDetails) {
                    statementDetails.setInt(1, orderId);
                    statementDetails.setString(2, product.getProduct_name());
                    statementDetails.setInt(3, product.getQunatity());
                    statementDetails.setDouble(4, product.getPrice());
                    statementDetails.setDouble(5, product.getTotalAmount());
                    statementDetails.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ByteArrayInputStream generateErrorBill(List<CombinedProduct> orderDetails) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);

            // Add title
            PdfPCell titleCell = new PdfPCell();
            titleCell.setBorder(PdfPCell.NO_BORDER);
            Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontTitle.setSize(20);
            fontTitle.setColor(0,0,128);
            Paragraph title = new Paragraph("Error Report: Missing Products",fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            titleCell.addElement(title);
            titleCell.addElement(new Paragraph("\n")); // Add some space
            table.addCell(titleCell);

            // Add missing product IDs
            for (Integer productId : orderDetails.get(0).getMissingproducts()) {
                PdfPCell cell = new PdfPCell();
                cell.setBorder(PdfPCell.NO_BORDER);
                Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
                font.setColor(new java.awt.Color(255,0,0));
                font.setSize(16);
                Paragraph paragraph = new Paragraph("Product ID " + productId + " is not found!",font);
                paragraph.setAlignment(Paragraph.ALIGN_LEFT);
                cell.addElement(paragraph);
                table.addCell(cell);
            }

            // Add the table to the document
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }

        return new ByteArrayInputStream(out.toByteArray());
    }



}
