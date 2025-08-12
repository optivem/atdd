package com.optivem.atdd.acceptancetests.shared.channels;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.lang.reflect.Method;
import java.util.stream.Stream;

public class ChannelExtension implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getTestMethod()
                .map(method -> method.isAnnotationPresent(Channel.class))
                .orElse(false);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        Method testMethod = context.getTestMethod().orElseThrow();
        Channel channelAnnotation = testMethod.getAnnotation(Channel.class);

        return Stream.of(channelAnnotation.value())
                .map(channel -> new ChannelInvocationContext(channel));
    }
}