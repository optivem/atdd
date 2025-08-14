package com.optivem.atdd.common;

import java.util.HashMap;

public class OrderStorage {
    // TODO: VJ: Replace with actual database
    private static final HashMap<String, Double> orderPrices = new HashMap<>();

    public static void saveOrder(String orderNumber, double totalPrice) {
        orderPrices.put(orderNumber, totalPrice);
    }

    public static double getOrderPrice(String orderNumber) {
        return orderPrices.get(orderNumber);
    }
}
