package com.SpringBootProject.StudentDetails.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class CombinedProduct {
	
	private int product_id;
	private String product_name;
	private float price;
	private String shopName;
	private float TotalAmount;
	private String customerName;
    private String phone;
    private String email;
    private float  totalPrice;
    private int transaction_id;
    private LocalDate transaction_date ;
    private LocalTime transaction_time;
    private int qunatity;
    
    
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public float getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		TotalAmount = totalAmount;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
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
	public int getQunatity() {
		return qunatity;
	}
	public void setQunatity(int qunatity) {
		this.qunatity = qunatity;
	}
	public CombinedProduct(int product_id, String product_name, float price, String shopName, float totalAmount,
			String customerName, String phone, String email, float totalPrice, int transaction_id,
			LocalDate transaction_date, LocalTime transaction_time, int qunatity) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.price = price;
		this.shopName = shopName;
		TotalAmount = totalAmount;
		this.customerName = customerName;
		this.phone = phone;
		this.email = email;
		this.totalPrice = totalPrice;
		this.transaction_id = transaction_id;
		this.transaction_date = transaction_date;
		this.transaction_time = transaction_time;
		this.qunatity = qunatity;
	}
	public CombinedProduct() {
		super();
	}
	@Override
	public String toString() {
		return "CombinedProduct [product_id=" + product_id + ", product_name=" + product_name + ", price=" + price
				+ ", shopName=" + shopName + ", TotalAmount=" + TotalAmount + ", customerName=" + customerName
				+ ", phone=" + phone + ", email=" + email + ", totalPrice=" + totalPrice + ", transaction_id="
				+ transaction_id + ", transaction_date=" + transaction_date + ", transaction_time=" + transaction_time
				+ ", qunatity=" + qunatity + "]";
	}
    
   
    
    
    

	

}
