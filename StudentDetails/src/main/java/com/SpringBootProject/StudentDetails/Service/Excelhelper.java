package com.SpringBootProject.StudentDetails.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.SpringBootProject.StudentDetails.Model.StudentModel;

@Service
public class Excelhelper {
	public static String TYPE =
			"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	static String[] HEADERS= {"StudentId","name","registerNo","gender","age","phoneNumber","currentStatus","emailId","course","batch","fees"};
   
	static String SHEET="Student";
	
	public static ByteArrayInputStream studentToExcel (List<StudentModel> studentmodel) {
		
		try(
				Workbook workbook= new XSSFWorkbook();
				ByteArrayOutputStream out=new ByteArrayOutputStream();
				){
			//Create the sheet with the sheet name Student
			Sheet sheet = workbook.createSheet(SHEET);
			// Header
			Row headerRow = sheet.createRow(0);
			
			for (int col = 0; col < HEADERS.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERS[col]);
				}
            
			int rowIdx = 1;
			for (StudentModel studentmodel1 : studentmodel) {
			Row row = sheet.createRow(rowIdx++);
			
			
			row.createCell(0).setCellValue(studentmodel1.getStudentId());
			row.createCell(1).setCellValue(studentmodel1.getName());
			row.createCell(2).setCellValue(studentmodel1.getRegisterNo());
			row.createCell(3).setCellValue(studentmodel1.getGender());
			row.createCell(4).setCellValue(studentmodel1.getAge());
			row.createCell(5).setCellValue(studentmodel1.getPhoneNumber());
			row.createCell(6).setCellValue(studentmodel1.getCurrentStatus());
			row.createCell(7).setCellValue(studentmodel1.getEmailId());
			row.createCell(8).setCellValue(studentmodel1.getCourse());
			row.createCell(9).setCellValue(studentmodel1.getBatch());
			row.createCell(10).setCellValue(studentmodel1.getFees());
			}
			workbook.write(out);
			
			return new ByteArrayInputStream(out.toByteArray());
		
		}catch(IOException e) {
			
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
		
	}

	
}
