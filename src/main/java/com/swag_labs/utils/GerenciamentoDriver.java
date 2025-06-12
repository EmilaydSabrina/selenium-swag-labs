package com.swag_labs.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
    import io.github.bonigarcia.wdm.WebDriverManager;

    public class GerenciamentoDriver {

        private static WebDriver driver;

        
        public static WebDriver getDriver(String browser) {
            if (driver == null) {
                switch (browser.toLowerCase()) {
                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments("--incognito"); 
                        driver = new ChromeDriver(chromeOptions);
                        break;
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        driver = new FirefoxDriver();
                        break;
                    default:
                        throw new IllegalArgumentException("Navegador não suportado: " + browser);
                }
            }
            return driver;
        }

        // Encerra o driver (caso esteja ativo)
        public static void quitDriver() {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        }
    }
