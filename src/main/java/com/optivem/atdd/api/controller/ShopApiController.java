package com.optivem.atdd.api.controller;

import com.optivem.atdd.common.PriceCalculator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
public class ShopApiController {
    // TODO: VJ: Replace with actual database
    private final HashMap<String, Double> orderPrices = new HashMap<>();

    @Value("${erp.url}")
    private String erpUrl;

    @GetMapping("/api/shop/echo")
    public ResponseEntity<Void> echo() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/shop/order")
    public ResponseEntity<PlaceOrderResponse> placeOrder(@RequestBody PlaceOrderRequest request) {
        var orderNumber = "ORD-" + UUID.randomUUID().toString();
        var sku = request.getSku();
        var quantity = request.getQuantity();
        var totalPrice = PriceCalculator.calculatePrice(erpUrl, sku, quantity);
        orderPrices.put(orderNumber, totalPrice);

        var response = new PlaceOrderResponse();
        response.setOrderNumber(orderNumber);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/shop/order/{orderNumber}")
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable String orderNumber) {
        var totalPrice = orderPrices.get(orderNumber);

        var response = new GetOrderResponse();
        response.setOrderNumber(orderNumber);
        response.setTotalPrice(totalPrice);

        return ResponseEntity.ok(response);
    }

    @Data
    public static class PlaceOrderRequest {
        private String sku;
        private int quantity;
    }

    @Data
    public static class PlaceOrderResponse {
        private String orderNumber;
    }

    @Data
    public static class GetOrderResponse {
        private String orderNumber;
        private double totalPrice;
    }
}