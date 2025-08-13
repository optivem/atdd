package com.optivem.atdd.acceptancetests.shared.drivers.system;

public interface SystemDriver {

    void load();

    void submitOrder(String sku, String quantity);

    void confirmTotalPriceEquals(String expectedTotalPrice);
}
