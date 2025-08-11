package com.optivem.atdd.acceptancetests;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("e2e")
public class ShopAccTest {

    @LocalServerPort
    private int port;

    private WebDriver seleniumDriver;
    private String baseUrl;

    private WireMockServer erpWireMockStub;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        seleniumDriver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + port;

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
//        erpWireMockStub.stubFor(get(urlPathEqualTo("/products"))
//                .withQueryParam("sku", equalTo("APPLE1001"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("{\"price\": 2.50}")));

        var shop = new ShopDsl(new UiDriver(seleniumDriver, baseUrl + "/shop"));

        shop.placeOrder("sku: 8", "quantity: 5");
        shop.assertConfirmation();
    }
}