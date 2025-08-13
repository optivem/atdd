package com.optivem.atdd.acceptancetests.shared.drivers.system;

import com.optivem.atdd.acceptancetests.shared.channels.ChannelContext;
import com.optivem.atdd.acceptancetests.shared.channels.ChannelType;

import static org.junit.jupiter.api.Assertions.fail;

public class SystemDriverContext implements SystemDriver {
    private final SystemUiDriver uiDriver;
    private final SystemApiDriver apiDriver;

    public SystemDriverContext(SystemUiDriver uiDriver, SystemApiDriver apiDriver) {
        this.uiDriver = uiDriver;
        this.apiDriver = apiDriver;
    }

    private SystemDriver getActiveDriver() {
        var channel = ChannelContext.get();

        if(channel == null) {
            fail("Current channel is null");
        }

        if (channel == ChannelType.UI) {
            return uiDriver;
        } else if (channel == ChannelType.API) {
            return apiDriver;
        }

        fail("Current channel is not recognized: " + channel);

        throw new RuntimeException();
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