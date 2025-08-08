package com.optivem.atdd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShopController {

    @GetMapping("/shop")
    @ResponseBody
    public String shopPage() {
        return """
            <html>
            <body>
                <input aria-label='SKU' />
                <input aria-label='Quantity' />
                <button aria-label='Place Order'>Place Order</button>
                <div role='alert'></div>
            </body>
            </html>
            """;
    }
}