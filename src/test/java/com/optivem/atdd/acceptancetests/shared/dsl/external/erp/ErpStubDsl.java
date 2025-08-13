package com.optivem.atdd.acceptancetests.shared.dsl.external.erp;

import com.optivem.atdd.acceptancetests.shared.drivers.external.erp.ErpStubDriver;
import com.optivem.atdd.acceptancetests.shared.dsl.util.DslParamsFactory;

public class ErpStubDsl {
    private final DslParamsFactory paramsFactory;
    private final ErpStubDriver erpStubDriver;

    public ErpStubDsl(DslParamsFactory paramsFactory, ErpStubDriver erpStubDriver) {
        this.paramsFactory = paramsFactory;
        this.erpStubDriver = erpStubDriver;
    }

    public void setupProduct(String... args) {
        var params = paramsFactory.create(args);
        var sku = params.getAlias("sku");
        var price = params.getValue("price", "0");
        erpStubDriver.setupProduct(sku, price);
    }
}