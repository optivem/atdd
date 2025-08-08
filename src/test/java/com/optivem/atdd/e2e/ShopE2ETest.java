// src/test/java/com/optivem/atdd/e2e/ShopE2ETest.java
package com.optivem.atdd.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShopE2ETest {

    @LocalServerPort
    private int port;

    private WebDriver seleniumDriver;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        seleniumDriver = new ChromeDriver();
        baseUrl = "http://localhost:" + port; // NOTE: VJ: In real life we'd want to set the actual string
        // Optionally: set up ERP test instance price for APPLE1001 to $2.50
        // e.g., call ERP REST API here
    }

    @AfterEach
    void tearDown() {
        seleniumDriver.quit();
    }

    @Test
    void shouldCompletePurchaseSuccessfully() {
        // Arrange: Set price via ERP API (pseudo-code)
        // erpApi.setPrice("APPLE1001", 2.50);

        // Act
        seleniumDriver.get(baseUrl + "/shop");

        // TODO: VJ: Add later
        var skuInput = seleniumDriver.findElement(By.cssSelector("[aria-label='SKU']"));
        skuInput.sendKeys("APPLE1001");

        var quantityInput = seleniumDriver.findElement(By.cssSelector("[aria-label='Quantity']"));
        quantityInput.sendKeys("5");

        var placeOrderButton = seleniumDriver.findElement(By.cssSelector("[aria-label='Place Order']"));
        placeOrderButton.click();

        // Assert
//        var confirmationMessage = seleniumDriver.findElement(By.cssSelector("[role='alert']"));
//        assertThat(confirmationMessage.getText())
//                .isEqualTo("Success! Total price is $12.50");
    }
}