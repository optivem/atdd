package com.optivem.atdd.acceptancetests.shared.drivers.system.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShopPage {
    private static final String SKU_INPUT = "[aria-label='SKU']";
    private static final String QUANTITY_INPUT = "[aria-label='Quantity']";
    private static final String PLACE_ORDER_BUTTON = "[aria-label='Place Order']";
    private static final String ALERT_ROLE = "[role='alert']";

    private final WebDriver driver;
    private final String shopUrl;

    private final WebDriverWait wait;

    public ShopPage(WebDriver driver, String shopUrl) {
        this.driver = driver;
        this.shopUrl = shopUrl;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public void navigateToShop() {
        driver.get(shopUrl);
    }

    public void enterSku(String sku) {
        driver.findElement(By.cssSelector(SKU_INPUT)).sendKeys(sku);
    }

    public void enterQuantity(String quantity) {
        driver.findElement(By.cssSelector(QUANTITY_INPUT)).sendKeys(quantity);
    }

    public void clickPlaceOrder() {
        driver.findElement(By.cssSelector(PLACE_ORDER_BUTTON)).click();
    }

    public String getConfirmationMessage() {
        var confirmationElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ALERT_ROLE))
        );

        return confirmationElement.getText();
    }
}