package com.optivem.atdd.acceptancetests.shared.dsl.system;

import com.optivem.atdd.acceptancetests.shared.drivers.system.SystemDriverContext;
import com.optivem.atdd.acceptancetests.shared.dsl.util.DslParamsFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class ShopDsl {
    private final DslParamsFactory paramsFactory;
    private final SystemDriverContext driver;

    public ShopDsl(DslParamsFactory paramsFactory, SystemDriverContext driver) {

        this.paramsFactory = paramsFactory;
        this.driver = driver;
    }

    public void placeOrder(String... args) {
        driver.load();
        var params = paramsFactory.create(args);
        var orderNumber = params.getAlias("orderNumber");
        var sku = params.getAlias("sku");
        var quantity = params.getValue("quantity", "20");
        driver.submitOrder(orderNumber, sku, quantity);
    }

    public void confirmOrder(String... args) {
        var params = paramsFactory.create(args);
        var orderNumber = params.getAlias("orderNumber");
        var expectedPrice = params.getValue("totalPrice", "150.00");
        driver.confirmTotalPriceEquals(orderNumber, expectedPrice);
    }

    public void confirmOrderNumberGenerated(String... args) {
        var params = paramsFactory.create(args);
        var orderNumber = params.getAlias("orderNumber");

        driver.confirmOrderNumberGenerated(orderNumber);
    }
}