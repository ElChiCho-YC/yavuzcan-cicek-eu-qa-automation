package com.enuygun.stepdefinitions;

import com.enuygun.config.Configuration;
import com.enuygun.context.TestContext;
import com.enuygun.pages.SearchResultsPage;
import com.enuygun.utils.WaitUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.PendingException;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.util.Map;
import static org.testng.Assert.assertTrue;

public class FlightSearchSteps {
    private final TestContext testContext;
    private SearchResultsPage resultsPage;

    public FlightSearchSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I am on Enuygun homepage {string}")
    public void navigateToHomepage(String expectedPageTitle) {
        testContext.getDriver().get("https://www.enuygun.com");
        assertTrue(testContext.getHomePage().waitForPageToLoad(),
                   "Enuygun homepage did not load within the expected time");

        String actualPageTitle = testContext.getHomePage().getTitle();
        assertTrue(actualPageTitle.contains(expectedPageTitle),
                   "Page title does not match expected value. Expected: " + expectedPageTitle +
                                   ", Actual: " + actualPageTitle);
    }

    @When("I search for round-trip flights from {string}, {string} to {string}, {string} {string} {string}")
    public void ıSearchForRoundTripFlightsFromToDepartureDateReturnDate(String from, String fromIata, String to, String toIata, String departureDate, String returnDate) {
        testContext.getHomePage().clickRoundTrip();
        resultsPage = testContext.getHomePage().searchFlights(from, fromIata, to, toIata, departureDate,returnDate);
    }


    @And("I apply departure time filter between {string} and {string}")
    public void apply_time_filter(String startTime, String endTime) {
        // Spinner'in kapanmasını bekleyin
        WaitUtil.waitForElementInvisible(testContext.getDriver(),
                                         By.cssSelector(".loading-spinner"),
                                         Configuration.getExplicitWait());
        resultsPage.applyDepartureTimeFilter(startTime, endTime);
    }

    @Then("all displayed flights should have departure times within the specified range")
    public void verifyDepartureTimes() {
        assertTrue(resultsPage.verifyTimeFilterApplied(),
                   "Not all flights are within the specified time range");
    }

    @And("flight list should match the selected route {string}")
    public void verifyRoute(String route) {
        assertTrue(resultsPage.verifyRoute(route),
                   "Flight list does not match the selected route");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // ScreenshotUtil.takeScreenshotForScenario(testContext.getDriver(), scenario);
        }
        testContext.quitDriver();
    }

}