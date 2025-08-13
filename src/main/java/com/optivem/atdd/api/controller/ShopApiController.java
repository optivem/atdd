package com.optivem.atdd.api.controller;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShopApiController {

    @GetMapping("/api/shop/echo")
    public ResponseEntity<Void> echo() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/shop/order")
    public ResponseEntity<OrderConfirmation> placeOrder(@RequestBody OrderRequest orderRequest) {
        // TODO: Implement order processing logic
        OrderConfirmation confirmation = new OrderConfirmation();
        confirmation.totalPrice = 12.50;
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