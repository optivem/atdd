package com.optivem.atdd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class ShopRestController {

    @GetMapping("/api/shop/echo")
    public ResponseEntity<Void> echo() {
        return ResponseEntity.ok().build();
    }
}