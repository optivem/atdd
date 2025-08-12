package com.optivem.atdd.acceptancetests.shared.dsl.system;

import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemDriver;
import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemDriverContext;
import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemUiDriver;
import com.optivem.atdd.acceptancetests.shared.dsl.util.Params;

import static org.assertj.core.api.Assertions.assertThat;

public class ShopDsl {
    private final SystemDriverContext driver;

    public ShopDsl(SystemDriverContext driver) {
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

    public void assertConfirmation(String... args) {
        var params = new Params(args);
        var expectedPrice = params.getString("totalPrice", "");
        driver.assertTotalPriceEquals(expectedPrice);
    }
}