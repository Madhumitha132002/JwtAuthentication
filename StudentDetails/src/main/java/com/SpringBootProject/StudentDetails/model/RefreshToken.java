package com.SpringBootProject.StudentDetails.model;

import java.time.Instant;

import com.SpringBootProject.StudentDetails.Model.StudentModel;

public class RefreshToken {

    private int id;
    private String token;
    private Instant expiryDate;
    private StudentModel studentmodel;
    private int student_id;
    
    
  
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Instant getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
    public StudentModel getStudentmodel() {
        return studentmodel;
    }
    public void setStudentmodel(StudentModel studentmodel) {
        this.studentmodel = studentmodel;
        if(studentmodel != null) {
            this.id = studentmodel.getStudentId();
        }
    }
}

