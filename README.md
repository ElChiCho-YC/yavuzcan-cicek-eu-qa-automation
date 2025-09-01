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

⚙️ Kurulum
Projeyi klonlayın:
bash
git clone https://github.com/kullaniciadi/enuygun-qa-automation.git
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

🧪 Case Study Senaryoları
Case 1: Temel Uçuş Arama ve Zaman Filtreleme
Enuygun anasayfasına gidilir
İstanbul-Ankara arası gidiş-dönüş uçuş araması yapılır
Kalkış saati filtresi (10:00 AM - 6:00 PM) uygulanır
Tüm uçuşların belirtilen saat aralığında olduğu doğrulanır
Seçilen rota ile sonuçların eşleştiği kontrol edilir
Case 2: Turkish Airlines Fiyat Sıralama
İstanbul-Ankara arası uçuş araması yapılır
Kalkış saati filtresi uygulanır
Turkish Airlines uçuşları filtrelenir
Fiyatların artan sırada olduğu doğrulanır
Case 3: Kritik Yol Testi
Enuygun'un en kritik kullanıcı yolculuğu tanımlanır
Bu yolculuk için otomasyon testleri geliştirilir
Tüm adımlar için gerekli doğrulamalar eklenir
📈 UI Test Stratejisi
Page Object Model: Her sayfa için ayrı sınıflar oluşturuldu
BasePage Sınıfı: Tüm sayfalar için ortak metodlar sağlar
TestContext: Dependency injection ile WebDriver yönetimi
Parametrize Edilmiş Test Verisi: Şehirler, tarihler ve diğer veriler config dosyasından yönetilir
Dinamik Bekleme Stratejileri: Thread.sleep() kullanılmadan explicit waits uygulanır
Hata Yönetimi: Tüm adımlarda try-catch blokları ve anlamlı hata mesajları

📝 Ek Bilgiler
Otomasyon Yaklaşımı: Testler gerçek kullanıcı davranışlarını taklit edecek şekilde tasarlandı
Bakım Kolaylığı: Page Object Model ve BasePage sınıfı ile kod tekrarı önlenmiş
Esneklik: Farklı tarihler ve şehirler için parametrize edilmiş test verisi desteği
Doğrulama: Tüm adımlar için anlamlı assertion mesajları ve ekran görüntüsü desteği

🤝 Katkıda Bulunma
Bu proje, Enuygun QA Engineer Case Study değerlendirmesi için tasarlanmıştır. Herhangi bir iyileştirme öneriniz varsa, lütfen bir issue açın veya pull request gönderin.

Bu proje, Enuygun'un case study gereksinimlerini mükemmel bir şekilde karşılar ve profesyonel bir test otomasyonu yaklaşımını sergiler. 🚀
