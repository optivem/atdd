package com.optivem.atdd.acceptancetests.shared.drivers;

public class ProductResponse {
    private String sku;
    private double price;

    // Getters and setters
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}