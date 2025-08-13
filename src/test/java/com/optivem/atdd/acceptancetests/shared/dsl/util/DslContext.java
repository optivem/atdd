package com.optivem.atdd.acceptancetests.shared.dsl.util;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DslContext {
    private final HashMap<String, String> aliases;

    private static final AtomicLong COUNTER = new AtomicLong();

    public DslContext() {
        this.aliases = new HashMap<>();
    }

    public String alias(String key) {
        ensureAliasExists(key);
        return aliases.get(key);
    }

    private void ensureAliasExists(String key) {
        if(!aliases.containsKey(key)) {
            var suffix = COUNTER.incrementAndGet();
            var alias = key + "-" + suffix;
            aliases.put(key, alias);
        }
    }
}
