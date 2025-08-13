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
        var channel = ChannelContext.get();
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
    public void submitOrder(String sku, String quantity) {
        getActiveDriver().submitOrder(sku, quantity);
    }

    @Override
    public void confirmTotalPriceEquals(String expectedTotalPrice) {
        getActiveDriver().confirmTotalPriceEquals(expectedTotalPrice);
    }
}