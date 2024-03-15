package com.SpringBootProject.StudentDetails.Model;


public class StudentModel {
    private int studentId;
    private String name;
    private String registerNo;
    private String gender;
    private int age;
    private String phoneNumber;
    private String currentStatus;
    private String emailId;
    private String course;
    private String batch;
    private int fees;
    private String role;
    private String password;
    
    
    
    
    public StudentModel() {
		super();
	}

    
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public int getFees() {
		return fees;
	}
	public void setFees(int fees) {
		this.fees = fees;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "StudentModel [studentId=" + studentId + ", name=" + name + ", registerNo=" + registerNo + ", gender="
				+ gender + ", age=" + age + ", phoneNumber=" + phoneNumber + ", currentStatus=" + currentStatus
				+ ", emailId=" + emailId + ", course=" + course + ", batch=" + batch + ", fees=" + fees + ", role="
				+ role + ", password=" + password + "]";
	}
	public StudentModel(int studentId, String name, String registerNo, String gender, int age, String phoneNumber,
			String currentStatus, String emailId, String course, String batch, int fees, String role, String password) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.registerNo = registerNo;
		this.gender = gender;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.currentStatus = currentStatus;
		this.emailId = emailId;
		this.course = course;
		this.batch = batch;
		this.fees = fees;
		this.role = role;
		this.password = password;
	}
	
    
}