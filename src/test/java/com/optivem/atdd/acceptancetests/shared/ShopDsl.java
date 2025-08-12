package com.optivem.atdd.acceptancetests.shared;

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
        var quantity = params.getInteger("quantity", "1");
        driver.setSku(sku);
        driver.setQuantity(quantity);
        driver.submitOrder();
    }

    public void assertConfirmation() {
        var message = driver.getConfirmationMessage();
        assertThat(message).matches("Success! Total price is \\$\\d+(\\.\\d{2})?");
    }

    public void assertConfirmation(String... args) {
        var params = new com.optivem.atdd.e2e.v3.shared.dsl.Params(args);
        var message = driver.getConfirmationMessage();
        var expectedMessage = params.getString("message", "");
        assertThat(message).isEqualTo(expectedMessage);
    }
}