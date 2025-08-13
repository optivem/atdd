package com.optivem.atdd.e2e.v3.shared.dsl;

import com.optivem.atdd.acceptancetests.shared.dsl.util.DslParams;
import com.optivem.atdd.e2e.v3.shared.drivers.UiDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ShopDsl {
    private final UiDriver driver;

    public ShopDsl(UiDriver driver) {
        this.driver = driver;
    }

    public void placeOrder(String... args) {
        var params = DslParams.from(args);
        driver.load();
        var sku = params.getValue("sku", "99");
        var quantity = params.getValue("quantity", "1");
        driver.submitOrder(sku, quantity);
    }

    public void confirmTotalPriceIsPositive() {
        driver.confirmTotalPriceIsPositive();
    }
}