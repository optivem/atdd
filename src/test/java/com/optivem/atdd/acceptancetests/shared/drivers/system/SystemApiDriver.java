package com.optivem.atdd.acceptancetests.shared.drivers.system;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.fail;

public class SystemApiDriver implements SystemDriver {
    private final WebClient webClient;
    private final String baseUrl;
    private double lastTotalPrice;

    private HashSet<String> orderNumbersGenerated = new HashSet<>();

    public SystemApiDriver(String baseUrl) {
        this.baseUrl = baseUrl;
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void load() {
        webClient.get()
                .uri("/api/shop/echo")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void submitOrder(String key, String sku, String quantity) {
        var orderRequest = OrderRequest.builder()
                .sku(sku)
                .quantity(Integer.parseInt(quantity))
                .build();

        var responseMono = webClient.post()
                .uri("/api/shop/order")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(orderRequest)
                .retrieve()
                .bodyToMono(OrderResponse.class);

        var response = responseMono.block();
        if (response != null) {
            // TODO: VJ, should actually inspect the order number non-null
            orderNumbersGenerated.add(key);
            lastTotalPrice = response.getTotalPrice();
        }
    }

    @Override
    public void confirmOrderNumberGenerated(String key) {
        var isOrderNumberGenerated = orderNumbersGenerated.contains(key);

        if(!isOrderNumberGenerated) {
            fail("Order number was not generated for key: " + key);
        }
    }

    @Override
    public void confirmTotalPriceEquals(String expectedTotalPrice) {
        var expected = Double.parseDouble(expectedTotalPrice);
        if (Double.compare(expected, lastTotalPrice) != 0) {
            fail("Expected total price: " + expected + ", but was: " + lastTotalPrice);
        }
    }



    @Data
    @Builder
    public static class OrderRequest {
        private String sku;
        private int quantity;
    }

    @Data
    public static class OrderResponse {
        private double totalPrice;
    }
}
