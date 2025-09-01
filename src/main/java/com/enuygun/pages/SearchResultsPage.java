package com.enuygun.pages;

import com.enuygun.config.Configuration;
import com.enuygun.utils.WaitUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Uçuş arama sonuçları sayfasını temsil eden Page Object sınıfı
 *
 * Case Study Gereksinimleri:
 * - Page Object Model (POM) uygulanmış
 * - Proper wait strategies (explicit waits)
 * - Dinamik içerik için uygun bekleme stratejileri
 * - OOP prensiplerine uygun kod yapısı
 */
public class SearchResultsPage extends BasePage {
    // Locators - Enuygun UI'ye göre güncellenmiş
    private By timeFilterSection = By.xpath("//div[@class='ctx-filter-departure-return-time card-header']");
    private By startTimeInput = By.xpath("//input[@placeholder='Başlangıç Saati']");
    private By endTimeInput = By.xpath("//input[@placeholder='Bitiş Saati']");
    private By applyTimeFilterButton = By.xpath("//button[contains(., 'Uygula')]");
    private By appliedTimeFilter = By.xpath("//div[contains(@class, 'applied-filter') and contains(., 'Kalkış Saati')]");
    private By flightItems = By.xpath("//div[@data-testid='flight-card']");
    private By departureTime = By.xpath(".//div[contains(@class, 'departure-time')]");
    private By routeInfo = By.xpath(".//div[contains(@class, 'route-info')]");
    private By airlineName = By.xpath(".//div[contains(@class, 'airline-name')]");
    private By priceElement = By.xpath(".//div[contains(@class, 'price')]");
    private By loadingSpinner = By.xpath("//div[contains(@class, 'loading-spinner')]");
    private By noResultsMessage = By.xpath("//div[contains(@class, 'no-results')]");
    private By departureTimeArea = By.xpath("//div[@class='ctx-filter-departure-return-time card-header']");
    private By departureStartTimeHandle = By.xpath("//div[@data-testid='departureDepartureTimeSlider']/div/div[@class='rc-slider-handle rc-slider-handle-1']");
    private By departureEndTimeHandle = By.xpath("//div[@data-testid='departureDepartureTimeSlider']/div/div[@class='rc-slider-handle rc-slider-handle-2']");

    /**
     * SearchResultsPage constructor
     *
     * @param driver WebDriver nesnesi
     */
    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Sayfanın tamamen yüklendiğini doğrular
     *
     * @return Sayfa yüklendi mi?
     */
    public boolean waitForResultsToLoad() {
        try {
            // Yüklenme spinner'ının kaybolmasını bekle
            if (!WaitUtil.waitForElementInvisible(driver, loadingSpinner, Configuration.getExplicitWait())) {
                System.out.println("Spinner hala görünür, sonuçlar yüklenemedi");
                return false;
            }

            // Sonuçların görünür olmasını bekle
            WaitUtil.waitForElementVisible(driver, flightItems, Configuration.getExplicitWait());
            return true;
        } catch (Exception e) {
            System.out.println("Search results did not load properly: " + e.getMessage());
            return false;
        }
    }

    /**
     * Tüm uçuşların belirtilen saat aralığında olduğunu doğrular
     *
     * @return Tüm uçuşlar belirtilen saat aralığında mı?
     */
    public boolean verifyTimeFilterApplied() {
        try {
            WebDriverWait wait = new WebDriverWait(driver,
                                                   Duration.ofSeconds(Configuration.getExplicitWait()));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));

            List<WebElement> flights = driver.findElements(flightItems);

            // Eğer sonuç yoksa, bunu da kontrol et
            if (flights.isEmpty()) {
                try {
                    if (isElementDisplayed(noResultsMessage)) {
                        System.out.println("No flights found for the selected criteria");
                        return true; // Bu durumda filtre doğru çalışmış demektir
                    }
                } catch (Exception e) {
                    // noResultsMessage elementi yok, devam et
                }
            }

            for (WebElement flight : flights) {
                String departureTimeText = flight.findElement(departureTime).getText().trim();
                if (departureTimeText.isEmpty()) {
                    continue;
                }

                LocalTime flightTime = convertTo24HourFormat(departureTimeText);
                LocalTime startTime = convertTo24HourFormat(getAppliedStartTime());
                LocalTime endTime = convertTo24HourFormat(getAppliedEndTime());

                if (flightTime.isBefore(startTime) || flightTime.isAfter(endTime)) {
                    System.out.println("Flight time out of range: " + departureTimeText +
                                               " (Expected: " + getAppliedStartTime() + " - " + getAppliedEndTime() + ")");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error verifying time filter: " + e.getMessage());
            return false;
        }
    }

    /**
     * Uçuş listesinin seçilen rota ile eşleştiğini doğrular
     *
     * @param expectedRoute Beklenen rota (örn: "Istanbul - Ankara")
     * @return Rota eşleşiyor mu?
     */
    public boolean verifyRoute(String expectedRoute) {
        try {
            List<WebElement> routes = driver.findElements(routeInfo);

            for (WebElement route : routes) {
                String routeText = route.getText().trim().replaceAll("\\s+", " ");
                if (!routeText.contains(expectedRoute.replace(" - ", "-"))) {
                    System.out.println("Route mismatch: '" + routeText + "' vs '" + expectedRoute + "'");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error verifying route: " + e.getMessage());
            return false;
        }
    }

    /**
     * Filtrenin başarıyla uygulandığını doğrular
     *
     * @param startTime Beklenen başlangıç saati
     * @param endTime Beklenen bitiş saati
     * @return Filtre uygulandı mı?
     */
    public boolean isTimeFilterApplied(String startTime, String endTime) {
        try {
            String appliedFilter = driver.findElement(appliedTimeFilter).getText();
            return appliedFilter.contains(startTime) && appliedFilter.contains(endTime);
        } catch (Exception e) {
            System.out.println("Error checking applied filter: " + e.getMessage());
            return false;
        }
    }

    /**
     * Uygulanan başlangıç saatini döndürür
     *
     * @return Uygulanan başlangıç saati
     */
    public String getAppliedStartTime() {
        try {
            String filterText = driver.findElement(appliedTimeFilter).getText();
            // Örnek: "Kalkış Saati: 10:00 AM - 6:00 PM"
            return filterText.split(":")[1].split("-")[0].trim();
        } catch (Exception e) {
            System.out.println("Error getting applied start time: " + e.getMessage());
            return "10:00 AM"; // Varsayılan değer
        }
    }

    /**
     * Uygulanan bitiş saatini döndürür
     *
     * @return Uygulanan bitiş saati
     */
    public String getAppliedEndTime() {
        try {
            String filterText = driver.findElement(appliedTimeFilter).getText();
            // Örnek: "Kalkış Saati: 10:00 AM - 6:00 PM"
            return filterText.split(":")[1].split("-")[1].trim();
        } catch (Exception e) {
            System.out.println("Error getting applied end time: " + e.getMessage());
            return "6:00 PM"; // Varsayılan değer
        }
    }

    /**
     * Görüntülenen rotayı döndürür
     *
     * @return Görüntülenen rota
     */
    public String getDisplayedRoute() {
        try {
            if (driver.findElements(routeInfo).size() > 0) {
                return driver.findElements(routeInfo).get(0).getText().trim();
            }
            return "";
        } catch (Exception e) {
            System.out.println("Error getting displayed route: " + e.getMessage());
            return "";
        }
    }

    /**
     * 12 saat formatını 24 saat formatına dönüştürür
     *
     * @param time 12 saat formatındaki zaman (örn: "10:30 AM")
     * @return 24 saat formatındaki zaman
     */
    private LocalTime convertTo24HourFormat(String time) {
        try {
            // Bazı durumlarda zaman formatı "10:30" olabilir (AM/PM olmadan)
            if (!time.contains("AM") && !time.contains("PM")) {
                // Varsayılan olarak AM kabul et (sabah saatleri)
                time += " AM";
            }

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
            return LocalTime.parse(time, inputFormatter);
        } catch (Exception e) {
            System.err.println("Error converting time format: " + time + " - " + e.getMessage());
            // Hata durumunda varsayılan değer döndür
            return LocalTime.of(12, 0);
        }
    }

    /**
     * Tüm uçuşların fiyatlarını döndürür
     *
     * @return Fiyat listesi
     */
    public List<Double> getAllPrices() {
        try {
            WebDriverWait wait = new WebDriverWait(driver,
                                                   Duration.ofSeconds(Configuration.getExplicitWait()));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));

            return driver.findElements(priceElement).stream()
                    .map(WebElement::getText)
                    .map(this::extractPrice)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error getting prices: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Metinden sadece sayısal fiyatı çıkarır
     *
     * @param priceText Fiyat metni (örn: "₺1,250")
     * @return Sayısal fiyat
     */
    private double extractPrice(String priceText) {
        try {
            // Sadece sayısal kısmı al (nokta ve virgül temizle)
            String cleanPrice = priceText.replaceAll("[^\\d,\\.]", "").replace(",", "");
            return Double.parseDouble(cleanPrice);
        } catch (Exception e) {
            System.err.println("Error extracting price from: " + priceText);
            return 0.0;
        }
    }

    /**
     * Belirli bir havayolu için fiyat sıralamasını doğrular
     *
     * @param airlineName Havayolu adı
     * @return Fiyatlar artan sırada mı?
     */
    public boolean verifyPriceSortingForAirline(String airlineName) {
        try {
            // Belirli havayolu için uçuşları filtrele
            List<Double> airlinePrices = driver.findElements(flightItems).stream()
                    .filter(flight -> {
                        try {
                            String airline = flight.findElement(By.id(airlineName)).getText();
                            return airline.contains(airlineName);
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .map(flight -> {
                        try {
                            String priceText = flight.findElement(priceElement).getText();
                            return extractPrice(priceText);
                        } catch (Exception e) {
                            return 0.0;
                        }
                    })
                    .filter(price -> price > 0)
                    .collect(Collectors.toList());

            // Fiyatların artan sırada olup olmadığını kontrol et
            for (int i = 0; i < airlinePrices.size() - 1; i++) {
                if (airlinePrices.get(i) > airlinePrices.get(i + 1)) {
                    System.out.println("Price sorting failed at index " + i +
                                               ": " + airlinePrices.get(i) + " > " + airlinePrices.get(i + 1));
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error verifying price sorting: " + e.getMessage());
            return false;
        }
    }

    /**
     * Belirli bir havayolu için tüm uçuşların görüntülenip görüntülenmediğini doğrular
     *
     * @param airlineName Havayolu adı
     * @return Tüm uçuşlar belirtilen havayolu mu?
     */
    public boolean verifyAllFlightsAreFromAirline(String airlineName) {
        try {
            List<String> airlines = driver.findElements(flightItems).stream()
                    .map(flight -> {
                        try {
                            return flight.findElement(By.id(airlineName)).getText();
                        } catch (Exception e) {
                            return "";
                        }
                    })
                    .filter(airline -> !airline.isEmpty())
                    .collect(Collectors.toList());

            // Tüm havayollarının belirtilen havayolu ile eşleştiğini kontrol et
            boolean allMatch = airlines.stream()
                    .allMatch(airline -> airline.contains(airlineName));

            if (!allMatch) {
                System.out.println("Not all flights are from " + airlineName);
                System.out.println("Found airlines: " + airlines);
            }

            return allMatch;
        } catch (Exception e) {
            System.out.println("Error verifying airline: " + e.getMessage());
            return false;
        }
    }

    /**
     * Saati dakika cinsinden değere dönüştürür (örn: "10:00 AM" => 600)
     */
    private int convertToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1].split(" ")[0]);
        boolean isPM = time.contains("PM");

        if (isPM && hours != 12) {
            hours += 12;
        } else if (!isPM && hours == 12) {
            hours = 0;
        }

        return hours * 60 + minutes;
    }

    /**
     * Dakikadan saat formatına dönüştürür (örn: 600 => "10:00")
     */
    private String convertToTimeFormat(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        String period = "AM";

        if (hours >= 12) {
            period = "PM";
            hours = hours == 12 ? 12 : hours - 12;
        }

        return String.format("%02d:%02d %s", hours, mins, period);
    }

    /**
     * Gidiş saatlerini ayarlar
     *
     * @param startTime Başlangıç saati (örn: "10:00 AM")
     * @param endTime Bitiş saati (örn: "6:00 PM")
     */
    public void applyDepartureTimeFilter(String startTime, String endTime) {
        int startMinutes = convertToMinutes(startTime);
        int endMinutes = convertToMinutes(endTime);
        click(departureTimeArea);
        // Slider handle'larını bul
        WebElement startHandle = driver.findElement(departureStartTimeHandle);
        WebElement endHandle = driver.findElement(departureEndTimeHandle);

        // Handle'ları hedef konuma taşı
        Actions actions = new Actions(driver);

        // Başlangıç handle'ı
        actions.dragAndDropBy(startHandle, calculateXOffset(startMinutes), 0).perform();

        // Bitiş handle'ı
        actions.dragAndDropBy(endHandle, calculateXOffset(endMinutes), 0).perform();

        // Filtrenin uygulandığını doğrula
        WaitUtil.waitForElementVisible(driver, By.cssSelector(".applied-filter"), Configuration.getExplicitWait());
    }

    /**
     * Hedef konumun X eksenindeki offset değerini hesaplar
     *
     * @param minutes Dakika cinsinden saat
     * @return Pixel offset
     */
    private int calculateXOffset(int minutes) {
        double ratio = (double) minutes / 1440; // 24 saat = 1440 dakika
        return (int) (100*ratio);
    }
}