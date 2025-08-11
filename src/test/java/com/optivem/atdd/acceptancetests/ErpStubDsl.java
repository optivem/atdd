package com.optivem.atdd.acceptancetests;

public class ErpStubDsl {
    private final ErpStubDriver erpStubDriver;

    public ErpStubDsl(ErpStubDriver erpStubDriver) {
        this.erpStubDriver = erpStubDriver;
    }

    public void setupProduct(String... args) {
        var params = new Params(args);
        var sku = params.getString("sku", "DEFAULT_SKU");
        var price = params.getDecimal("price", "0");
        erpStubDriver.setupProduct(sku, price);
    }
}