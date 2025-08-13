package com.optivem.atdd.acceptancetests.shared.channels;

import org.junit.jupiter.api.extension.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelInvocationContext implements TestTemplateInvocationContext {

    private final ChannelType channel;
    private final Object[] arguments;

    public ChannelInvocationContext(ChannelType channel, Object[] arguments) {
        this.channel = channel;
        this.arguments = arguments;
    }

    @Override
    public String getDisplayName(int invocationIndex) {
        String argsString = Arrays.stream(arguments)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        return "Channel: " + channel + " | Args: [" + argsString + "]";
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        return Arrays.asList(
            (BeforeTestExecutionCallback) context -> ChannelContext.set(channel),
            (AfterTestExecutionCallback) context -> ChannelContext.clear(),
            new ParameterResolver() {
                @Override
                public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
                    return parameterContext.getIndex() < arguments.length;
                }

                @Override
                public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
                    return arguments[parameterContext.getIndex()];
                }
            }
        );
    }
}