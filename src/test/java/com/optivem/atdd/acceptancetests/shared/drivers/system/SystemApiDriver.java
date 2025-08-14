package com.optivem.atdd.acceptancetests.shared.drivers.system;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.fail;

public class SystemApiDriver implements SystemDriver {
    private HashMap<String, String> orderNumbers = new HashMap<>();

    private final ShopClient client;

    public SystemApiDriver(String baseUrl) {
        var webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        this.client = new ShopClient(webClient);
    }

    @Override
    public void load() {
        client.echo();
    }

    @Override
    public void submitOrder(String orderNumber, String sku, String quantity) {
        var orderRequest = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity(Integer.parseInt(quantity))
                .build();

        var response = client.placeOrder(orderRequest);

        if (response != null) {
            var externalOrderNumber = response.getOrderNumber();
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

        var response = client.getOrder(externalOrderNumber);
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
        private final WebClient webClient;

        public ShopClient(WebClient webClient) {
            this.webClient = webClient;
        }

        public void echo() {
            webClient.get()
                    .uri("/api/shop/echo")
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }

        public PlaceOrderResponse placeOrder(PlaceOrderRequest orderRequest) {
            var responseMono = webClient.post()
                    .uri("/api/shop/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(orderRequest)
                    .retrieve()
                    .bodyToMono(PlaceOrderResponse.class);

            var response = responseMono.block();
            return response;
        }

        public GetOrderResponse getOrder(String externalOrderNumber) {
            var responseMono = webClient.get()
                    .uri("/api/shop/order/{orderNumber}", externalOrderNumber)
                    .retrieve()
                    .bodyToMono(GetOrderResponse.class);

            var response = responseMono.block();
            return response;
        }
    }
}
