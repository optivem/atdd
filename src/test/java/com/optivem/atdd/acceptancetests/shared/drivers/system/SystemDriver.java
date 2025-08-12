package com.optivem.atdd.acceptancetests.shared.drivers.system;

import org.openqa.selenium.By;

public interface SystemDriver {

    public void load();

    public void setSku(String sku);

    public void setQuantity(String quantity);

    public void submitOrder();

    public void assertTotalPriceEquals(String expectedTotalPrice);
}
