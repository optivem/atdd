package com.optivem.atdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtddApplication {

    public static void main(String[] args) {
        String erpUrl = System.getenv("ERP_URL");
        System.out.println("ERP_URL: " + erpUrl);
        SpringApplication.run(AtddApplication.class, args);
    }
}