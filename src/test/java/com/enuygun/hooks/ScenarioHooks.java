package com.enuygun.hooks;

import com.enuygun.config.DriverManager;
import com.enuygun.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class ScenarioHooks {
    private WebDriver driver;

    @Before
    public void setup(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        driver = DriverManager.getDriver();
        driver.get("https://www.enuygun.com");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            ScreenshotUtil.takeScreenshot(driver, scenario.getName());
            System.out.println("Scenario failed: " + scenario.getName());
        }

        if (driver != null) {
            driver.quit();
        }

        System.out.println("Finished scenario: " + scenario.getName());
    }
}
