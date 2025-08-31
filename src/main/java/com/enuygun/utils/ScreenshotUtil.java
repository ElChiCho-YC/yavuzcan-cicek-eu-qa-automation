package com.enuygun.utils;

import com.enuygun.config.Configuration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Ekran görüntüsü alma işlemlerini yöneten utility sınıfı
 *
 * Case Study Gereksinimleri:
 * - Testler başarısız olduğunda ekran görüntüsü alma
 * - Ekran görüntülerini anlamlı isimlerle kaydetme
 * - Dizin oluşturma ve hata yönetimi
 * - Raporlama için dosya yolu sağlama
 */
public class ScreenshotUtil {

    /**
     * WebDriver'dan ekran görüntüsü alır ve belirtilen test adına göre kaydeder
     *
     * @param driver  WebDriver instance
     * @param testName Test/Scenario adı (Cucumber'da scenario.getName() kullanılabilir)
     * @return Kaydedilen ekran görüntüsünün tam yolu
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            // Ekran görüntüsü dizinini oluştur veya kontrol et
            String screenshotDir = createScreenshotDirectory();

            // Dosya adını oluştur (özel karakterleri temizle ve zaman damgası ekle)
            String safeTestName = sanitizeFileName(testName);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = safeTestName + "_" + timestamp + ".png";

            // Ekran görüntüsünü al ve kaydet
            File screenshot = takeScreenshotAsFile(driver);
            Path destinationPath = Paths.get(screenshotDir, fileName);
            Files.copy(screenshot.toPath(), destinationPath);

            System.out.println("Ekran görüntüsü başarıyla kaydedildi: " + destinationPath.toAbsolutePath());
            return destinationPath.toAbsolutePath().toString();

        } catch (Exception e) {
            throw new RuntimeException("Ekran görüntüsü alma işlemi başarısız oldu: " + e.getMessage(), e);
        }
    }

    /**
     * WebDriver'dan ekran görüntüsü alır ve geçici bir dosyaya kaydeder
     *
     * @param driver WebDriver instance
     * @return Ekran görüntüsü dosyası
     */
    private static File takeScreenshotAsFile(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (WebDriverException e) {
            throw new RuntimeException("Tarayıcı ekran görüntüsü alınamadı: " + e.getMessage(), e);
        }
    }

    /**
     * Ekran görüntüsü dizinini oluşturur veya mevcut dizini döndürür
     *
     * @return Ekran görüntüsü dizininin yolu
     */
    private static String createScreenshotDirectory() {
        String screenshotDir = Configuration.getScreenshotDirectory();
        Path path = Paths.get(screenshotDir);

        try {
            Files.createDirectories(path);
            return path.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Ekran görüntüsü dizini oluşturulamadı: " + screenshotDir, e);
        }
    }

    /**
     * Test adını dosya adı olarak kullanılabilecek formata dönüştürür
     * Özel karakterleri temizler ve uzun isimleri kısaltır
     *
     * @param testName Orijinal test adı
     * @return Temizlenmiş dosya adı
     */
    private static String sanitizeFileName(String testName) {
        // Özel karakterleri temizle ve boşlukları alt çizgi ile değiştir
        String cleanName = testName.replaceAll("[^a-zA-Z0-9\\s]", "")
                .replaceAll("\\s+", "_");

        // Uzun isimleri kısalt (dosya sistemi limitlerine karşı)
        final int MAX_LENGTH = 50;
        if (cleanName.length() > MAX_LENGTH) {
            cleanName = cleanName.substring(0, MAX_LENGTH);
        }

        // Boş veya geçersiz isimleri varsayılan ile değiştir
        if (cleanName.isEmpty() || cleanName.matches("^[._\\-]*$")) {
            cleanName = "screenshot";
        }

        return cleanName;
    }

    /**
     * Cucumber Scenario nesnesi ile doğrudan entegre çalışmak için ek metod
     *
     * @param driver   WebDriver instance
     * @param scenario Cucumber Scenario nesnesi
     * @return Kaydedilen ekran görüntüsünün tam yolu
     */
    public static String takeScreenshotForScenario(WebDriver driver, io.cucumber.java.Scenario scenario) {
        String scenarioName = scenario.getName();
        String featureName = scenario.getSourceTagNames().isEmpty() ?
                "unknown_feature" :
                scenario.getSourceTagNames().iterator().next().replace("@", "");

        String testName = featureName + "_" + scenarioName;
        return takeScreenshot(driver, testName);
    }
}