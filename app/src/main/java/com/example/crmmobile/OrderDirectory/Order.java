package com.example.crmmobile.OrderDirectory;

public class Order {

    private int id;

    private String orderCode;
    private String company;
    private String price;
    private String date;
    private String paymentStatus;
    private String orderType;

    public Order(int id,
                 String orderCode,
                 String company,
                 String price,
                 String date,
                 String paymentStatus,
                 String orderType) {
        this.id = id;
        this.orderCode = orderCode;
        this.company = company;
        this.price = price;
        this.date = date;
        this.paymentStatus = paymentStatus;
        this.orderType = orderType;
    }

    public Order(String orderCode,
                 String company,
                 String price,
                 String date,
                 String paymentStatus,
                 String orderType) {
        this(0, orderCode, company, price, date, paymentStatus, orderType);
    }

    // Getter / Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getCompany() {
        return company;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getOrderType() {
        return orderType;
    }
}
