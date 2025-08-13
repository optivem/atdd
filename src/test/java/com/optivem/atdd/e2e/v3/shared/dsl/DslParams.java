package com.optivem.atdd.e2e.v3.shared.dsl;

import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DslParams {
    private final Map<String, String> paramMap;

    private DslParams(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public static DslParams from(String... args) {
        var paramMap = Arrays.stream(args)
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(a -> a[0].trim(), a -> a[1].trim()));

        return new DslParams(paramMap);
    }

    public String getValue(String key, String defaultValue) {
        return paramMap.getOrDefault(key, defaultValue);
    }
}