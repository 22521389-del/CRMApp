package com.example.crmmobile;

public class Opportunity {
    private String title;
    private String price;
    private String date;
    private String status;
    private int callCount;
    private int messageCount;
    private String exchangeText;

    public Opportunity(String title, String price, String date, String status, int callCount, int messageCount, String exchangeText) {
        this.title = title;
        this.price = price;
        this.date = date;
        this.status = status;
        this.callCount = callCount;
        this.messageCount = messageCount;
        this.exchangeText = exchangeText;
    }

    public String getTitle() { return title; }
    public String getPrice() { return price; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public int getCallCount() { return callCount; }
    public int getMessageCount() { return messageCount; }
    public String getExchangeText() { return exchangeText; }
}
