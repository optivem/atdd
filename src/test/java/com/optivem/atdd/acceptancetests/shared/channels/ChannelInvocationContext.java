package com.optivem.atdd.acceptancetests.shared.channels;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import java.util.Collections;
import java.util.List;

public class ChannelInvocationContext implements TestTemplateInvocationContext {

    private final ChannelType channel;

    public ChannelInvocationContext(ChannelType channel) {
        this.channel = channel;
    }

    @Override
    public String getDisplayName(int invocationIndex) {
        return "Channel: " + channel;
    }

    @Override
    public List<org.junit.jupiter.api.extension.Extension> getAdditionalExtensions() {
        return Collections.singletonList((BeforeTestExecutionCallback) (context) -> ChannelContext.set(channel));
    }
}