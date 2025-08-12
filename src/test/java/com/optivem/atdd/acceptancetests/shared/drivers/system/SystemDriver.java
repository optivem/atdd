package com.optivem.atdd.acceptancetests.shared.drivers.system;

import org.openqa.selenium.By;

public interface SystemDriver {

    void load();

    void submitOrder(String sku, String quantity);

    void assertTotalPriceEquals(String expectedTotalPrice);
}
