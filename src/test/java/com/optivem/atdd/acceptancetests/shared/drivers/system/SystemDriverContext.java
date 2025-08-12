package com.optivem.atdd.acceptancetests.shared.drivers.system;

import com.optivem.atdd.acceptancetests.shared.channels.ChannelContext;
import com.optivem.atdd.acceptancetests.shared.channels.ChannelType;

public class SystemDriverContext implements SystemDriver {
    private final SystemUiDriver uiDriver;
    private final SystemApiDriver apiDriver;

    public SystemDriverContext(SystemUiDriver uiDriver, SystemApiDriver apiDriver) {
        this.uiDriver = uiDriver;
        this.apiDriver = apiDriver;
    }

    private SystemDriver getActiveDriver() {
        ChannelType channel = ChannelContext.get();
        if (channel == ChannelType.UI) {
            return uiDriver;
        } else if (channel == ChannelType.API) {
            return apiDriver;
        }
        throw new RuntimeException("Current channel is null or unsupported!");
    }

    @Override
    public void load() {
        getActiveDriver().load();
    }

    @Override
    public void setSku(String sku) {
        getActiveDriver().setSku(sku);
    }

    @Override
    public void setQuantity(String quantity) {
        getActiveDriver().setQuantity(quantity);
    }

    @Override
    public void submitOrder() {
        getActiveDriver().submitOrder();
    }

    @Override
    public void assertTotalPriceEquals(String expectedTotalPrice) {
        getActiveDriver().assertTotalPriceEquals(expectedTotalPrice);
    }
}