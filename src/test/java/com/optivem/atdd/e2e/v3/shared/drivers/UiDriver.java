package com.optivem.atdd.e2e.v3.shared.drivers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class UiDriver {
    private final WebDriver driver;
    private final String baseUrl;
    private final WebDriverWait wait;

    public UiDriver(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void load() {
        driver.get(baseUrl);
    }

    public void submitOrder(String sku, String quantity) {
        driver.findElement(By.cssSelector("[aria-label='SKU']")).sendKeys(sku);
        driver.findElement(By.cssSelector("[aria-label='Quantity']")).sendKeys(quantity);
        driver.findElement(By.cssSelector("[aria-label='Place Order']")).click();
    }

    public void confirmTotalPriceIsPositive() {
        var confirmationElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[role='alert']"))
        );

        var confirmation = confirmationElement.getText();

        assertThat(confirmation).matches("Success! Total Price is \\$\\d+(\\.\\d{2})?");
    }
}