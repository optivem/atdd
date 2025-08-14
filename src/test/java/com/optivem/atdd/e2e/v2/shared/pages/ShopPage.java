package com.optivem.atdd.e2e.v2.shared.pages;

import com.optivem.atdd.TestConfiguration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class ShopPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ShopPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfiguration.WAIT_SECONDS));
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/shop");
    }

    public void enterSku(String sku) {
        driver.findElement(By.cssSelector("[aria-label='SKU']")).sendKeys(sku);
    }

    public void enterQuantity(String quantity) {
        driver.findElement(By.cssSelector("[aria-label='Quantity']")).sendKeys(quantity);
    }

    public void placeOrder() {
        driver.findElement(By.cssSelector("[aria-label='Place Order']")).click();
    }

    public String getConfirmationMessage() {
        WebElement confirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[role='alert']"))
        );
        return confirmation.getText();
    }
}