package com.SpringBootProject.StudentDetails.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import com.SpringBootProject.StudentDetails.Model.StudentModel;

public class CSVHelper {
	
public static ByteArrayInputStream studentToCSV(List<StudentModel> studentmodel) {
	final CSVFormat format =
			CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
			CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

				for (StudentModel studentmodel1 : studentmodel) {
					List<Object> data = Arrays.asList(
					studentmodel1.getStudentId(),
					studentmodel1.getName(),
					studentmodel1.getRegisterNo(),
					studentmodel1.getGender(),
					studentmodel1.getAge(),
					studentmodel1.getPhoneNumber(),
					studentmodel1.getCurrentStatus(),
					studentmodel1.getEmailId(),
					studentmodel1.getCourse(),
					studentmodel1.getBatch(),
					studentmodel1.getFees()
					);
					csvPrinter.printRecord(data);
				}
				
				csvPrinter.flush();
			
				return new ByteArrayInputStream(out.toByteArray());
		
		} catch (IOException e) {

			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());

		} 
		
}

}