package com.optivem.atdd.e2e.v3;

public class ShopDsl {
    private final UiDriver driver;

    public ShopDsl(UiDriver driver) {
        this.driver = driver;
    }

    public void placeOrder(String... args) {
        driver.load();
        var params = new Params(args);
        var sku = params.getString("sku", "DEFAULT_SKU");
        var quantity = params.getInteger("quantity", "1");
        driver.setSku(sku);
        driver.setQuantity(quantity);
        driver.submitOrder();
    }

    public void assertConfirmation(String... args) {
        var params = new Params(args);
        var expectedMessage = params.getString("message", "");
        var actualMessage = driver.getConfirmationMessage();
        if (!actualMessage.equals(expectedMessage)) {
            throw new AssertionError("Expected: " + expectedMessage + ", but was: " + actualMessage);
        }
    }
}