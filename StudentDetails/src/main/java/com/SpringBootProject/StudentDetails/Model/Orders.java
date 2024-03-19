package com.SpringBootProject.StudentDetails.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Orders {
    private int transactionId;
    private int customerId;
    private List<Product> products;
    private float total_amount;
    private LocalDate transaction_date ;
    private LocalTime transaction_time;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
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

    public Orders(int transactionId, int customerId, List<Product> products, float total_amount,
                  LocalDate transaction_date, LocalTime transaction_time) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.products = products;
        this.total_amount = total_amount;
        this.transaction_date = transaction_date;
        this.transaction_time = transaction_time;
    }

    public Orders() {
    }

    @Override
    public String toString() {
        return "Orders{" +
                "transactionId=" + transactionId +
                ", customerId=" + customerId +
                ", products=" + products +
                ", total_amount=" + total_amount +
                ", transaction_date=" + transaction_date +
                ", transaction_time=" + transaction_time +
                '}';
    }
}
