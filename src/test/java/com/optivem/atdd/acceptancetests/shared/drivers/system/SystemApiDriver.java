package com.optivem.atdd.acceptancetests.shared.drivers.system;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.fail;

public class SystemApiDriver implements SystemDriver {
    private final WebClient webClient;
    private final String baseUrl;

    private HashMap<String, String> orderNumbers = new HashMap<>();

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
    public void submitOrder(String orderNumber, String sku, String quantity) {
        var orderRequest = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity(Integer.parseInt(quantity))
                .build();

        var responseMono = webClient.post()
                .uri("/api/shop/order")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(orderRequest)
                .retrieve()
                .bodyToMono(PlaceOrderResponse.class);

        var response = responseMono.block();
        if (response != null) {
            var externalOrderNumber = response.orderNumber;
            // TODO: VJ, should actually inspect the order number non-null
            orderNumbers.put(orderNumber, externalOrderNumber);
        }
    }

    @Override
    public void confirmOrderNumberGenerated(String orderNumber) {
        var isOrderNumberGenerated = orderNumbers.containsKey(orderNumber);

        if(!isOrderNumberGenerated) {
            fail("Order number was not generated for orderNumber: " + orderNumber);
        }
    }

    @Override
    public void confirmOrderTotalPrice(String orderNumber, String expectedTotalPrice) {
        var expected = Double.parseDouble(expectedTotalPrice);

        var externalOrderNumber = orderNumbers.get(orderNumber);

        var responseMono = webClient.get()
                .uri("/api/shop/order/{orderNumber}", externalOrderNumber)
                .retrieve()
                .bodyToMono(GetOrderResponse.class);

        var response = responseMono.block();
        if (response == null || Double.compare(expected, response.getTotalPrice()) != 0) {
            fail("Expected total price: " + expected + ", but was: " + (response != null ? response.getTotalPrice() : "null"));
        }
    }


    @Data
    @Builder
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

    public class ShopClient {

    }
}
