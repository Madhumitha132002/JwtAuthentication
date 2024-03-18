package com.SpringBootProject.StudentDetails.Model;

public class CustomerModel {
	
	
	private  int customerId;
	private String customerName;
	private String password;
	private String phonenumber;
	private String email;
	private int age;
	private String gender;
    private String role;
    
    
    
    
    
    
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
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
	public CustomerModel(int customerId, String customerName, String password, String phonenumber, String email,
			int age, String gender, String role) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.password = password;
		this.phonenumber = phonenumber;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.role = role;
	}
	public CustomerModel() {
		super();
	}
	@Override
	public String toString() {
		return "CustomerModel [customerId=" + customerId + ", customerName=" + customerName + ", password=" + password
				+ ", phonenumber=" + phonenumber + ", email=" + email + ", age=" + age + ", gender=" + gender
				+ ", role=" + role + "]";
	}
	
	

}
