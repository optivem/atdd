package com.optivem.atdd.acceptancetests.shared.drivers.system;

import org.openqa.selenium.By;

public interface SystemDriver {

    void load();

    void setSku(String sku);

    void setQuantity(String quantity);

    void submitOrder();

    void assertTotalPriceEquals(String expectedTotalPrice);
}
