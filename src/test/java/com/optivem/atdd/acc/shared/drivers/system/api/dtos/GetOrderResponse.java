package com.optivem.atdd.acc.shared.drivers.system.api.dtos;

import lombok.Data;

@Data
public class GetOrderResponse {
    private String orderNumber;
    private double totalPrice;
}
