package com.optivem.atdd.acceptancetests.shared.drivers.system;

public interface SystemDriver {

    void load();

    void submitOrder(String orderNumber, String sku, String quantity);

    void confirmOrderTotalPrice(String orderNumber, String expectedTotalPrice);

    void confirmOrderNumberGenerated(String orderNumber);
}
