package com.SpringBootProject.StudentDetails.Service;


import com.SpringBootProject.StudentDetails.Model.CombinedProduct;
import com.SpringBootProject.StudentDetails.Model.Product;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PDFGenerator1 {

    public static ByteArrayInputStream generateBill(List<CombinedProduct> orderDetails) {

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // Create table for customer details
            PdfPTable customerTable = new PdfPTable(1);
            customerTable.setWidthPercentage(30);
            customerTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

            PdfPCell customerCell = new PdfPCell();
            customerCell.setBorder(PdfPCell.NO_BORDER);
            customerCell.setPadding(5);

            customerCell.addElement(new Paragraph("Customer Name: " + orderDetails.get(0).getCustomerName()));
            customerCell.addElement(new Paragraph("Phone: " + orderDetails.get(0).getPhone()));
            customerCell.addElement(new Paragraph("Email: " + orderDetails.get(0).getEmail()));

            customerTable.addCell(customerCell);

            // Create table for transaction details
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

            // Create table for total price
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

            // Add customer details table
            document.add(customerTable);

            // Add transaction details table
            document.add(new Paragraph(" "));
            document.add(transactionTable);

            // Add total price table
            document.add(new Paragraph(" "));
            document.add(totalTable);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
