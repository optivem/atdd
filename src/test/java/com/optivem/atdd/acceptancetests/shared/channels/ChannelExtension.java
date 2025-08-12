package com.optivem.atdd.acceptancetests.shared.channels;

import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;

public class ChannelExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        Channel channel = testMethod.getAnnotation(Channel.class);
        if (channel != null && channel.value().length > 0) {
            ChannelContext.set(channel.value()[0]); // Set the first channel
        } else {
            ChannelContext.clear();
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        ChannelContext.clear();
    }
}