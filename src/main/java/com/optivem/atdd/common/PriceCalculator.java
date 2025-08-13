package com.optivem.atdd.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PriceCalculator {
    public static double calculatePrice(String erpUrl, String sku, int quantity) {
        var price = getPrice(erpUrl, sku);
        return price * quantity;
    }

    private static double getPrice(String erpUrl, String sku) {
        try {
            var url = erpUrl + "/products/" + sku;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.body());
            return node.get("price").asDouble();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch price", e);
        }
    }
}
