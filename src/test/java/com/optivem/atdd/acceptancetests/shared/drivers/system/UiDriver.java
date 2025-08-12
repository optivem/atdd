package com.optivem.atdd.acceptancetests.shared.drivers.system;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UiDriver {
    private final WebDriver driver;
    private final String shopUrl;
    private final WebDriverWait wait;

    public UiDriver(WebDriver driver, String shopUrl) {
        this.driver = driver;
        this.shopUrl = shopUrl;

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void load() {
        driver.get(shopUrl);
    }

    public void setSku(String sku) {
        driver.findElement(By.cssSelector("[aria-label='SKU']")).sendKeys(sku);
    }

    public void setQuantity(String quantity) {
        driver.findElement(By.cssSelector("[aria-label='Quantity']")).sendKeys(quantity);
    }

    public void submitOrder() {
        driver.findElement(By.cssSelector("[aria-label='Place Order']")).click();
    }

    public String getConfirmationMessage() {
        WebElement confirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[role='alert']"))
        );
        return confirmation.getText();
    }
}