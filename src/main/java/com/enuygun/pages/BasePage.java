package com.enuygun.pages;

import com.enuygun.config.Configuration;
import com.enuygun.utils.WaitUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Calendar;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
                                      Duration.ofSeconds(Configuration.getExplicitWait()));
    }

    protected void click(By locator) {
        try {
            WaitUtil.waitForElementClickable(driver, locator, 10).click();
        } catch (ElementClickInterceptedException e) {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void sendKeys(By locator, String text) {
        WaitUtil.waitForElementClickable(driver, locator, 10).sendKeys(text);
    }

    protected String getText(By locator) {
        return WaitUtil.waitForElementVisible(driver, locator, 10).getText();
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element
        );
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return WaitUtil.waitForElementVisible(driver, locator, 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}