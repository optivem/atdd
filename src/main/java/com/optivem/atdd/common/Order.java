package com.optivem.atdd.common;

import lombok.Data;

@Data
public class Order {
    private String orderNumber;
    private String sku;
    private int quantity;
    private double totalPrice;

    public Order(String orderNumber, String sku, int quantity, double totalPrice) {
        this.orderNumber = orderNumber;
        this.sku = sku;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
