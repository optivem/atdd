package com.optivem.atdd.acceptancetests.shared.channels;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

public class ChannelContext {
    private static final ThreadLocal<ChannelType> current = new ThreadLocal<>();

    public static void set(ChannelType channel) {
        current.set(channel);
    }

    public static ChannelType get() {
        return current.get();
    }

    public static void clear() {
        current.remove();
    }
}