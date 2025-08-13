package com.optivem.atdd.acceptancetests.shared.dsl.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class DslParams {
    private final Map<String, String> paramMap;

    public DslParams(String... args) {
        paramMap = Arrays.stream(args)
            .map(s -> s.split(":"))
            .collect(Collectors.toMap(a -> a[0].trim(), a -> a[1].trim()));
    }

    public String getString(String key, String defaultValue) {
        return paramMap.getOrDefault(key, defaultValue);
    }
}