package com.optivem.atdd.acc.shared.channels;

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