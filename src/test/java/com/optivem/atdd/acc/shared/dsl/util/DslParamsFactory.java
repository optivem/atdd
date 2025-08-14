package com.optivem.atdd.acc.shared.dsl.util;

public class DslParamsFactory {
    private final DslContext context;

    public DslParamsFactory(DslContext context) {
        this.context = context;
    }

    public DslParams create(String... args) {
        return DslParams.from(context, args);
    }
}
