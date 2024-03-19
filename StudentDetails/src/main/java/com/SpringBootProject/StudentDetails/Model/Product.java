package com.SpringBootProject.StudentDetails.Model;

public class Product {
    private int productId;
    private int quantity;

    // Getters and setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

	public Product(int productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public Product() {
		super();
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", quantity=" + quantity + "]";
	}
    
    
}
