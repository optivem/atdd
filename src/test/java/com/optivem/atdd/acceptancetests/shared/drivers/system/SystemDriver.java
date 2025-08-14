package com.optivem.atdd.acceptancetests.shared.drivers.system;

public interface SystemDriver {

    void load();

    void submitOrder(String key, String sku, String quantity);

    void confirmTotalPriceEquals(String expectedTotalPrice);

    void confirmOrderNumberGenerated(String key);
}
