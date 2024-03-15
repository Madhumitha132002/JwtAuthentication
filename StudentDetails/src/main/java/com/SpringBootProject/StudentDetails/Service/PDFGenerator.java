package com.SpringBootProject.StudentDetails.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import com.SpringBootProject.StudentDetails.Model.StudentModel;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDFGenerator {

    public static InputStream generator(List<StudentModel> studentList){

        Document document = new Document(PageSize.A3);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, outputStream);

            document.open();
            
            String imagePath = "/home/madhumitha/Downloads/Record.png";
	         // Add image to the right corner
	         InputStream imageStream = new FileInputStream(imagePath); // Create InputStream from file path
	         Image image = Image.getInstance(ImageIO.read(imageStream), null);
	         image.scaleToFit(100, 100); // Adjust width and height as needed
	         float x = PageSize.A3.getWidth() - 100; // Adjust x coordinate as needed
	         float y = PageSize.A3.getHeight() - 60; // Adjust y coordinate to move the image slightly higher
	         image.setAbsolutePosition(x, y);
	         document.add(image);

	         
	         
	         Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontTitle.setSize(16);

            Paragraph paragraph = new Paragraph("List Of Students", fontTitle);

            paragraph.setAlignment(Paragraph.ALIGN_CENTER);

            document.add(paragraph);

            PdfPTable table = new PdfPTable(11); // Adjusted to 11 columns

            table.setWidthPercentage(100f);
            table.setWidths(new float[] { 2, 3, 3, 2, 2, 3, 3, 3, 2, 2, 2 }); // Adjusted widths for 11 columns
            table.setSpacingBefore(10);

            // Adding table headers
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            font.setColor(new java.awt.Color(0, 0, 128));
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(new java.awt.Color(173, 216, 230));
            cell.setPadding(5);
            cell.setPhrase(new Phrase("StudentId", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Student Name", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("RegisterNo", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Gender", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Age", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("PhoneNumber", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("CurrentStatus", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("EmailId", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Course", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Batch", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Fees", font));
            table.addCell(cell);

         // Adding cells to the table
            for (StudentModel student : studentList) {
                PdfPCell cell1 = new PdfPCell();
                cell1.setBackgroundColor(new java.awt.Color(255, 255, 255)); // Set background color to white
                cell1.setPadding(5);
                
                Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN);
                font1.setColor(new java.awt.Color(0, 0, 128)); // Set font color to blue

                cell1.setPhrase(new Phrase(String.valueOf(student.getStudentId()), font1));
                table.addCell(cell1);
                cell1.setPhrase(new Phrase(student.getName(), font1));
                table.addCell(cell1);
                cell1.setPhrase(new Phrase(student.getRegisterNo(), font1));
                table.addCell(cell1);
                cell1.setPhrase(new Phrase(student.getGender(), font1));
                table.addCell(cell1);
                cell1.setPhrase(new Phrase(String.valueOf(student.getAge()), font1));
                table.addCell(cell1);
                cell1.setPhrase(new Phrase(student.getPhoneNumber(), font1));
                table.addCell(cell1);
                cell1.setPhrase(new Phrase(student.getCurrentStatus(), font1));
                table.addCell(cell1);
                cell1.setPhrase(new Phrase(student.getEmailId(), font1));
                table.addCell(cell1);
                cell1.setPhrase(new Phrase(student.getCourse(), font1));
                table.addCell(cell1);
                cell1.setPhrase(new Phrase(student.getBatch(), font1));
                table.addCell(cell1);
                cell1.setPhrase(new Phrase(String.valueOf(student.getFees()), font1));
                table.addCell(cell1);
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
