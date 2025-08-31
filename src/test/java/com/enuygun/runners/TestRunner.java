package com.enuygun.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        // Feature dosyalarının bulunduğu klasör
        features = "src/test/resources/features/ui",

        // Step definition'ların bulunduğu paket
        glue = {"com.enuygun.stepdefinitions", "com.enuygun.hooks"},

        // Raporlama için pluginler
        plugin = {
                "pretty", // Konsol çıktısını okunabilir yap
                "html:reports/cucumber-reports/cucumber.html", // HTML rapor
                "json:reports/cucumber-reports/cucumber.json", // JSON rapor
                "junit:reports/cucumber-reports/cucumber.xml", // JUnit rapor
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // ExtentReports entegrasyonu
        },

        // Monochrome: Konsol çıktısında renkleri kapatır
        monochrome = true,

        // Dry run: Sadece step'lerin eşleşip eşleşmediğini kontrol eder, testleri çalıştırmaz
        dryRun = false,

        // Tag filtreleme (opsiyonel, komut satırından da yapılabilir)
        tags = "@basic_flight_search or @price_sorting or @critical_path"
)
public class TestRunner {
    // Bu sınıf sadece annotation'lar ve Cucumber ayarları içerir
    // İçerisinde hiçbir metod veya kod olmamalıdır
}
