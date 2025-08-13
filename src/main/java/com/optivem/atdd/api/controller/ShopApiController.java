package com.optivem.atdd.api.controller;

import com.optivem.atdd.common.PriceCalculator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShopApiController {

    @Value("${erp.url}")
    private String erpUrl;

    @GetMapping("/api/shop/echo")
    public ResponseEntity<Void> echo() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/shop/order")
    public ResponseEntity<OrderConfirmation> placeOrder(@RequestBody OrderRequest orderRequest) {
        var confirmation = new OrderConfirmation();
        var sku = orderRequest.getSku();
        var quantity = orderRequest.getQuantity();
        confirmation.totalPrice = PriceCalculator.calculatePrice(erpUrl, sku, quantity);
        return ResponseEntity.ok(confirmation);
    }

    @Data
    public static class OrderRequest {
        public String sku;
        public int quantity;
    }

    @Data
    public static class OrderConfirmation {
        public double totalPrice;
    }
}