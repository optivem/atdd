package com.optivem.atdd.acceptancetests.shared.drivers.system.api.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceOrderRequest {
    private String sku;
    private int quantity;
}