package com.optivem.atdd.acceptancetests.shared.dsl.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class DslParams {
    private final DslContext context;
    private final Map<String, String> paramMap;

    private DslParams(DslContext context, Map<String, String> paramMap)
    {
        this.context = context;
        this.paramMap = paramMap;
    }

    public static DslParams from(DslContext context, String... args) {
        var paramMap = Arrays.stream(args)
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(a -> a[0].trim(), a -> a[1].trim()));

        return new DslParams(context, paramMap);
    }

    public String getValue(String key, String defaultValue) {
        return paramMap.getOrDefault(key, defaultValue);
    }

    public String alias(String sku) {
        return null;

        // TODO: VJ: Implement alias resolution logic
//        var value = getValue(key);
//        if (value == null) {
//            fail("No alias found for key: " + sku);
//        }
//
//        return context.alias(value);
    }

    private String getValue(String key) {
        return paramMap.get(key);
    }
}