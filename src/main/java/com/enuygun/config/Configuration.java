package com.enuygun.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private static Configuration instance;
    private static final Properties properties = new Properties();

    private Configuration() {
        loadProperties();
    }

    public static synchronized Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties file not found in resources");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Error loading configuration file", ex);
        }
    }

    /**
     * Belirtilen key'e ait değeri integer olarak döndürür
     * Varsayılan değer kullanılarak güvenli bir şekilde dönüşüm yapar
     */
    public static int getIntProperty(String key) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Configuration property is not a valid integer: " + key, e);
        }
    }

    /**
     * Belirtilen key'e ait değeri integer olarak döndürür,
     * eğer yoksa veya geçersizse varsayılan değeri döndürür
     */
    public static int getIntProperty(String key, int defaultValue) {
        try {
            String value = properties.getProperty(key);
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            System.err.println("Invalid integer value for property: " + key + ", using default: " + defaultValue);
            return defaultValue;
        }
    }

    // Browser ayarları
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    // Timeout ayarları
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "15"));
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Configuration property not found: " + key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getBaseUrl() {
        return getProperty("base.url", "https://www.enuygun.com");
    }

    public static String getScreenshotDirectory() {
        return getProperty("screenshot.dir", "reports/screenshots");
    }
}