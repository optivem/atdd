package com.optivem.atdd.acceptancetests.shared.channels;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Method;

public class ChannelExtension implements BeforeEachCallback, ParameterResolver {

    @Override
    public void beforeEach(ExtensionContext context) {
        // No-op, but could be used for setup if needed
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Method testMethod = extensionContext.getRequiredTestMethod();
        Channel channel = testMethod.getAnnotation(Channel.class);
        return channel != null && parameterContext.getParameter().getType().equals(ChannelType.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Method testMethod = extensionContext.getRequiredTestMethod();
        Channel channel = testMethod.getAnnotation(Channel.class);
        // Return the first channel for simplicity
        return channel.value()[0];
    }
}