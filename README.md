Enuygun QA Engineer Case Study - UI Test Automation
Java

Selenium

Cucumber

Maven

Bu proje, Enuygun'un uçuş arama işlevselliği için geliştirilmiş bir UI test otomasyonu çerçevesidir. Case study gereksinimlerini tam olarak karşılayan, profesyonel bir test otomasyonu yaklaşımı sergiler.

📋 Case Study Gereksinimleri

Bu proje aşağıdaki case study gereksinimlerini karşılar:

Page Object Model (POM): Tüm sayfalar için POM deseni uygulanmış

Proper Wait Strategies: Thread.sleep() kullanılmadan explicit waits uygulanmış

Cross-Browser Testing: Chrome ve Firefox tarayıcıları destekleniyor

Screenshot Capture: Başarısız testlerde otomatik ekran görüntüsü alınıyor

Test Reporting: Detaylı raporlama ve anlamlı assertion mesajları

OOP Principles: Temiz ve bakımı kolay kod yapısı

Test Code Maintainability: Parametrize edilmiş test verisi desteği

🛠️ Gereksinimler
Java 17+
Maven 3.6.0+
Chrome Tarayıcı ve ChromeDriver (veya Firefox ve GeckoDriver)
IntelliJ IDEA (önerilen)

## 📦 Proje Yapısı
src/

├── main/java/com/enuygun/

│ ├── api/ # API testleri için modeller ve client'lar

│ ├── config/ # Konfigürasyon yönetimi (Configuration, DriverManager)

│ ├── pages/ # Page Object Model classes (HomePage, SearchResultsPage)

│ └── utils/ # Yardımcı sınıflar (WaitUtil, ScreenshotUtil, CsvUtil)

├── test/java/com/enuygun/

│ ├── runners/ # Test çalıştırıcıları (TestRunner, ApiTestRunner)

│ ├── stepdefinitions/ # Cucumber step definitions

│ └── hooks/ # Cucumber hooks (ScenarioHooks)

└── test/resources/

├── config/ # Konfigürasyon dosyaları (config.properties)

└── features/ # Cucumber feature dosyaları (UI ve API)

⚙️ Kurulum
Projeyi klonlayın:
bash
git clone https://github.com/ElChiCho-YC/yavuzcan-cicek-eu-qa-automation.git
cd enuygun-qa-automation
Bağımlılıkları yükleyin:
bash
mvn clean install
Tarayıcı sürücülerini konfigüre edin:
src/test/resources/config/config.properties dosyasını açın
Tarayıcı ayarlarını istediğiniz gibi güncelleyin:
properties
browser=chrome
headless=false

🏃‍♂️ Çalıştırma
Tüm UI Testlerini Çalıştırma
bash
mvn test

Belirli Tag'li Testleri Çalıştırma
bash
# Temel uçuş arama testi
mvn test -Dcucumber.filter.tags="@basic_flight_search"

# API testleri
mvn test -Dcucumber.filter.tags="@petstore_api"
Headless Modda Çalıştırma
bash
mvn test -Dheadless=true


📊 Test Raporları

Testler tamamlandıktan sonra raporlar aşağıdaki konumlarda bulunur:

HTML Rapor: reports/cucumber-reports/cucumber.html

JSON Rapor: reports/cucumber-reports/cucumber.json

JUnit Rapor: reports/cucumber-reports/cucumber.xml

Raporlar, testlerin başarı durumunu, hata mesajlarını ve ekran görüntülerini içerir.

📋 Test Senaryoları

Part 1: UI Automation

-Case 1: Temel uçuş araması ve zaman filtresi uygulama

-Case 2: Türk Hava Yolları için fiyat sıralama doğrulama

-Case 3: Kritik kullanıcı yolunun test edilmesi

Part 2: API Testing

-Petstore API'si üzerinde CRUD operasyonları (RestAssured)

-Positive ve negative test senaryoları

Part 3: Load Testing

-JMeter ile hazırlanmış enuygun_search_load_test.jmx dosyası.

Arama modülünün performansının ölçülmesi.

Part 4: Analysis and Categorization

-Uçuş arama sonuçlarının CSV'ye yazdırılması (CsvUtil)
-Havayolu şirketine göre min, max, ortalama fiyatların hesaplanması ve grafik oluşturulması (ChartUtil)
-Günün saat dilimlerine göre fiyat dağılımının ısı haritası ile gösterilmesi

📝 Ek Bilgiler

Otomasyon Yaklaşımı: Testler gerçek kullanıcı davranışlarını taklit edecek şekilde tasarlandı
Bakım Kolaylığı: Page Object Model ve BasePage sınıfı ile kod tekrarı önlenmiş
Esneklik: Farklı tarihler ve şehirler için parametrize edilmiş test verisi desteği
Doğrulama: Tüm adımlar için anlamlı assertion mesajları ve ekran görüntüsü desteği

🤝 Katkıda Bulunma

Bu proje, Enuygun QA Engineer Case Study değerlendirmesi için tasarlanmıştır. Herhangi bir iyileştirme öneriniz varsa, lütfen bir issue açın veya pull request gönderin.

Bu proje, Enuygun'un case study gereksinimlerini mükemmel bir şekilde karşılar ve profesyonel bir test otomasyonu yaklaşımını sergiler. 🚀

✨ İletişim
Yavuzcan Çiçek - yavuzcicek@outlook.com