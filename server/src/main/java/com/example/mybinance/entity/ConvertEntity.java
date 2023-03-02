package com.example.mybinance.entity;

public class ConvertEntity extends ConvertRequest {

    private double price;

    public ConvertEntity(String from, String to, double price) {
        super(from, to);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double updatePrice) {
        price = updatePrice;
    }

}
