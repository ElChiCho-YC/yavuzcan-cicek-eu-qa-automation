package com.enuygun.context;

import com.enuygun.config.DriverManager;
import com.enuygun.pages.HomePage;
import com.enuygun.pages.SearchResultsPage;
import org.openqa.selenium.WebDriver;

// TestContext.java
public class TestContext {
    private final WebDriver driver;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;

    public TestContext() {
        this.driver = DriverManager.getDriver();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage(driver);
        }
        return homePage;
    }

    public SearchResultsPage getSearchResultsPage() {
        if (searchResultsPage == null) {
            searchResultsPage = new SearchResultsPage(driver);
        }
        return searchResultsPage;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}