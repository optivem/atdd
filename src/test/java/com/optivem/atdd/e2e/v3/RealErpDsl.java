package com.optivem.atdd.e2e.v3;

public class RealErpDsl {
    private final RealErpDriver realErpDriver;

    public RealErpDsl(RealErpDriver realErpDriver) {
        this.realErpDriver = realErpDriver;
    }

    public ProductResponse getProduct(String sku) {
        return realErpDriver.getProduct(sku);
    }
}