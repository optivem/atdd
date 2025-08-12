package com.optivem.atdd.acceptancetests.shared.channels;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

public class ChannelContext {
    private static final ThreadLocal<ChannelType> currentChannel = new ThreadLocal<>();

    public static void set(ChannelType channel) {
        currentChannel.set(channel);
    }

    public static ChannelType get() {
        return currentChannel.get();
    }

    public static void clear() {
        currentChannel.remove();
    }
}