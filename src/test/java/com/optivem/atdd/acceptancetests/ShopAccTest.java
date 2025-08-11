package com.optivem.atdd.acceptancetests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import org.wiremock.spring.EnableWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("acc")
@EnableWireMock
public class ShopAccTest {

    @LocalServerPort
    private int port;

    @Value("${erp.url}")
    private String wireMockUrl;

    private WebDriver seleniumDriver;
    private String baseUrl;

//    private WireMockServer erpWireMockStub;

//    private WebClient erpStubWebClient;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        seleniumDriver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + port;

//        erpStubWebClient = WebClient.create("http://localhost:8089");


//        erpWireMockStub = new WireMockServer(8089);
//        erpWireMockStub.start();
//        configureFor("localhost", 8089);
    }

    @AfterEach
    void tearDown() {
        seleniumDriver.quit();

//        erpWireMockStub.stop();
    }

    @Test
    public void shouldCompletePurchaseSuccessfully() {
//        stubFor(get(urlPathEqualTo("/products/8"))
////                .withQueryParam("sku", equalTo("APPLE1001"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("{\"price\": 2.50}")));
//
        WebClient webClient = WebClient.create("http://localhost:8089");

        String stubJson = """
{
  "request": {
    "method": "GET",
    "urlPath": "/products/8"
  },
  "response": {
    "status": 200,
    "headers": {
      "Content-Type": "application/json"
    },
    "jsonBody": {
      "price": 2.50
    }
  }
}
""";

        webClient.post()
                .uri("/__admin/mappings")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(stubJson)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        var shop = new ShopDsl(new UiDriver(seleniumDriver, baseUrl + "/shop"));

        shop.placeOrder("sku: 8", "quantity: 5");
        shop.assertConfirmation("message: Success! Total price is $12.50");
    }
}