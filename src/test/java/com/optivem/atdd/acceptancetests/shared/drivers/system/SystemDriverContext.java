package com.optivem.atdd.acceptancetests.shared.drivers.system;

import com.optivem.atdd.acceptancetests.shared.channels.ChannelContext;
import com.optivem.atdd.acceptancetests.shared.channels.ChannelType;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.fail;

public class SystemDriverContext implements SystemDriver {
    private final HashMap<ChannelType, SystemDriver> drivers;

    private ChannelType cachedActiveChannel;
    private SystemDriver cachedActiveDriver;

    public SystemDriverContext(HashMap<ChannelType, SystemDriver> drivers) {
        this.drivers = drivers;
    }

    private ChannelType getActiveChannel() {
        if(cachedActiveChannel != null) {
            return cachedActiveChannel;
        }

        cachedActiveChannel = ChannelContext.get();

        if(cachedActiveChannel == null) {
            fail("Active channel is null");
        }

        return cachedActiveChannel;
    }

    // NOTE: This is used in the case that it's not set by the context
    public void setActiveChannel(ChannelType channel) {
        cachedActiveChannel = channel;
    }

    private SystemDriver getActiveDriver() {
        if(cachedActiveDriver != null) {
            return cachedActiveDriver;
        }

        var activeChannel = getActiveChannel();

        if(!drivers.containsKey(activeChannel)) {
            fail("Current channel is not recognized: " + activeChannel);
        }

        cachedActiveDriver = drivers.get(activeChannel);
        return cachedActiveDriver;
    }

    @Override
    public void load() {
        getActiveDriver().load();
    }

    @Override
    public void submitOrder(String orderNumber, String sku, String quantity) {
        getActiveDriver().submitOrder(orderNumber, sku, quantity);
    }

    @Override
    public void confirmTotalPriceEquals(String orderNumber, String expectedTotalPrice) {
        getActiveDriver().confirmTotalPriceEquals(orderNumber, expectedTotalPrice);
    }

    @Override
    public void confirmOrderNumberGenerated(String orderNumber) {
        getActiveDriver().confirmOrderNumberGenerated(orderNumber);
    }


}