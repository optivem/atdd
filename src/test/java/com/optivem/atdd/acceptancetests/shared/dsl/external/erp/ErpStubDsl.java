package com.optivem.atdd.acceptancetests.shared.dsl.external.erp;

import com.optivem.atdd.acceptancetests.shared.drivers.external.erp.ErpStubDriver;
import com.optivem.atdd.acceptancetests.shared.dsl.util.DslParams;

public class ErpStubDsl {
    private final ErpStubDriver erpStubDriver;

    public ErpStubDsl(ErpStubDriver erpStubDriver) {
        this.erpStubDriver = erpStubDriver;
    }

    public void setupProduct(String... args) {
        var params = DslParams.from(args);
        var sku = params.getValue("sku", "DEFAULT_SKU");
        var price = params.getValue("price", "0");
        erpStubDriver.setupProduct(sku, price);
    }
}