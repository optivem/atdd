package com.optivem.atdd.acceptancetests.shared.drivers.external.erp;

public class ProductResponse {
    private String sku;
    private String price;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}