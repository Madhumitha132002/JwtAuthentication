package com.SpringBootProject.StudentDetails.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class CustomerModel {
	
	
	private  int customerId;
	private String customerName;
	private String password;
	private String phonenumber;
	private String email;
	private int age;
	private String gender;
    private String role;
    private int transaction_id;
    private double totalPrice;
    private LocalDate transaction_date ;
    private LocalTime transaction_time;
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public LocalDate getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(LocalDate transaction_date) {
		this.transaction_date = transaction_date;
	}
	public LocalTime getTransaction_time() {
		return transaction_time;
	}
	public void setTransaction_time(LocalTime transaction_time) {
		this.transaction_time = transaction_time;
	}
	public CustomerModel(int customerId, String customerName, String password, String phonenumber, String email,
			int age, String gender, String role, int transaction_id, double totalPrice, LocalDate transaction_date,
			LocalTime transaction_time) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.password = password;
		this.phonenumber = phonenumber;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.role = role;
		this.transaction_id = transaction_id;
		this.totalPrice = totalPrice;
		this.transaction_date = transaction_date;
		this.transaction_time = transaction_time;
	}
	public CustomerModel() {
		super();
	}
	@Override
	public String toString() {
		return "CustomerModel [customerId=" + customerId + ", customerName=" + customerName + ", password=" + password
				+ ", phonenumber=" + phonenumber + ", email=" + email + ", age=" + age + ", gender=" + gender
				+ ", role=" + role + ", transaction_id=" + transaction_id + ", totalPrice=" + totalPrice
				+ ", transaction_date=" + transaction_date + ", transaction_time=" + transaction_time + "]";
	}
    
    
    
    
    
   
	

}
