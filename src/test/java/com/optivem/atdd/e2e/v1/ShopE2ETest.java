// src/test/java/com/optivem/atdd/e2e/ShopE2ETest.java
package com.optivem.atdd.e2e.v1;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShopE2ETest {

    @LocalServerPort
    private int port;

    private WebDriver seleniumDriver;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        seleniumDriver = new ChromeDriver(options);

        baseUrl = "http://localhost:" + port; // NOTE: VJ: In real life we'd want to set the actual string
    }

    @AfterEach
    void tearDown() {
        seleniumDriver.quit();
    }

    @Test
    void shouldCompletePurchaseSuccessfully() {
        // Act
        seleniumDriver.get(baseUrl + "/shop");

        // TODO: VJ: Add later
        var skuInput = seleniumDriver.findElement(By.cssSelector("[aria-label='SKU']"));
        skuInput.sendKeys("8");

        var quantityInput = seleniumDriver.findElement(By.cssSelector("[aria-label='Quantity']"));
        quantityInput.sendKeys("5");

        var placeOrderButton = seleniumDriver.findElement(By.cssSelector("[aria-label='Place Order']"));
        placeOrderButton.click();

        // Assert
        // var confirmationMessage = seleniumDriver.findElement(By.cssSelector("[role='alert']"));

        var wait = new WebDriverWait(seleniumDriver, Duration.ofSeconds(10));
        var confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[role='alert']")));

        var text = confirmationMessage.getText();

        assertThat(text).matches("Success! Total price is \\$\\d+(\\.\\d{2})?");

//        assertThat(text)
//                .isEqualTo("Success! Total price is $12.50");
    }
}