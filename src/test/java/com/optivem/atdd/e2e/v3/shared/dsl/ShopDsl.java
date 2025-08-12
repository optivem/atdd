package com.optivem.atdd.e2e.v3.shared.dsl;

import com.optivem.atdd.e2e.v3.shared.drivers.UiDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ShopDsl {
    private final UiDriver driver;

    public ShopDsl(UiDriver driver) {
        this.driver = driver;
    }

    public void placeOrder(String... args) {
        driver.load();
        var params = new Params(args);
        var sku = params.getString("sku", "99");
        var quantity = params.getString("quantity", "1");
        driver.setSku(sku);
        driver.setQuantity(quantity);
        driver.submitOrder();
    }

    public void assertTotalPriceIsPositive() {
        driver.assertTotalPriceIsPositive();
    }
}