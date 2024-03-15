package com.SpringBootProject.StudentDetails.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.SpringBootProject.StudentDetails.Model.StudentModel;

public interface IStudentService {
	

	ResponseEntity<String> addStudentDetails(StudentModel studentmodel);
	int deleteStudentDetails(int Student_id);
	int updateStudentDetails(StudentModel studentModel);
	StudentModel findById(int student_id) ;
	List<StudentModel> getAllStudentDetails();
	
	
	

}