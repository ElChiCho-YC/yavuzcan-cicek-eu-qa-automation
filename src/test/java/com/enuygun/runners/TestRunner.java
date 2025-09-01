package com.enuygun.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/ui",
        glue = {"com.enuygun.stepdefinitions", "com.enuygun.hooks"},
        plugin = {
                "pretty", // Konsol çıktısını okunabilir yap
                "html:reports/cucumber-reports/cucumber.html", // HTML rapor
                "json:reports/cucumber-reports/cucumber.json", // JSON rapor
                "junit:reports/cucumber-reports/cucumber.xml", // JUnit rapor
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // ExtentReports entegrasyonu
        },
        monochrome = true,
        dryRun = false,
        tags = "@basic_flight_search or @price_sorting or @critical_path"
)
public class TestRunner {
}
