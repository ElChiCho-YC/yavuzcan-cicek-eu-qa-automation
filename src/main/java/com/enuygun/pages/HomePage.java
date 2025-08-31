package com.enuygun.pages;

import com.enuygun.config.Configuration;
import com.enuygun.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    // Locators
    private By fromCityInput = By.xpath("//input[@data-testid='endesign-flight-origin-autosuggestion-input']");
    private By toCityInput = By.xpath("//input[@data-testid='endesign-flight-destination-autosuggestion-input']");
    private By departureDateInput = By.xpath("//input[@data-testid='enuygun-homepage-flight-departureDate-datepicker-input']");
    private By returnDateInput = By.xpath("//input[@data-testid='enuygun-homepage-flight-returnDate-datepicker-input']");
    private By tripTypeSelector = By.xpath("//label[@data-testid='search-round-trip-label']");
    private By submitButton = By.xpath("//button[@data-testid='enuygun-homepage-flight-submitButton']");
    private By logoElement = By.xpath("//a[@aria-label='Enuygun Anasayfa']");
    private By searchForm = By.xpath("//div[@data-testid='endesign-search-form-tab']");
    private By pageTitle = By.xpath("//h1[@data-testid='search-form-heading']");
    private By departureDatePicker = By.xpath("//label[@data-testid='enuygun-homepage-flight-departureDate-input-comp']");
    private By returnDatePicker = By.xpath("//label[@data-testid='enuygun-homepage-flight-returnDate-input-comp']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        try {
            String title = WaitUtil.waitForElementVisible(
                    driver, pageTitle, Configuration.getExplicitWait()).getText().trim();
            System.out.println("Page title is: " + title);
            return title;
        } catch (Exception e) {
            System.out.println("Error getting page title: " + e.getMessage());
            return "";
        }
    }

    public boolean waitForPageToLoad() {
        try {
            WaitUtil.waitForElementVisible(
                    driver, logoElement, Configuration.getExplicitWait());
            WaitUtil.waitForElementVisible(
                    driver, searchForm, Configuration.getExplicitWait());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickRoundTrip() {
        try {
            click(tripTypeSelector);
            System.out.println("Round-trip seçeneği tıklandı");
        } catch (Exception e) {
            System.err.println("Round-trip seçeneği tıklanamadı: " + e.getMessage());
            throw e;
        }
    }

    public SearchResultsPage searchFlights(String from, String fromIata, String to, String toIata,
                                           String departureDate, String returnDate) {
        // Şehirleri seç
        sendKeys(fromCityInput, from);
        try {
            WaitUtil.waitForElementVisible(driver,
                                           By.xpath("//span[@data-testid='flight-origin-istanbul-highlight-0' and text()='" + fromIata + "']"), 10).click();
        } catch (Exception e) {
            WaitUtil.waitForElementVisible(driver,
                                           By.xpath("//span[@data-testid='flight-origin-istanbul-highlight-0' and text()='" + fromIata + "']\""), 10).click();
        }

        sendKeys(toCityInput, to);
        try {
            WaitUtil.waitForElementVisible(driver,
                                           By.xpath("//span[@data-testid='flight-destination-ankara-esenboga-havalimani-highlight-0' and contains(., '" + toIata + "')]"), 10).click();
        } catch (Exception e) {
            WaitUtil.waitForElementVisible(driver,
                                           By.xpath("//span[@data-testid='flight-destination-ankara-esenboga-havalimani-highlight-0' and contains(., '" + toIata + "')]"), 10).click();
        }

        // Tarihleri seç
        click(departureDatePicker);
        try {
            WaitUtil.waitForElementVisible(driver, By.xpath("//button[@title='"+ departureDate +"']"),10).click();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        click(returnDatePicker);
        try {
            WaitUtil.waitForElementVisible(driver, By.xpath("//button[@title='"+ returnDate +"']"),10).click();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Ara butonuna tıkla
        click(submitButton);
        return new SearchResultsPage(driver);
    }
}