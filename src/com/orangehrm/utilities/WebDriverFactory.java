package com.orangehrm.utilities;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * WebDriverFactory handles browser creation for different environments.
 * Compatible with manual JAR setup (non-Maven).
 */
public class WebDriverFactory {

    private static final Logger logger = Logger.getLogger(WebDriverFactory.class);

    /**
     * Creates and returns a WebDriver instance based on browser name.
     *
     * @param browserName (chrome | firefox | edge)
     * @return WebDriver instance
     */
    public static WebDriver createDriver(String browserName) {
        if (browserName == null || browserName.isEmpty()) {
            browserName = "chrome"; // default browser
        }

        WebDriver driver = null;
        String driverDir = System.getProperty("user.dir") + "\\drivers\\";
        System.out.println("Driver paths: " + driverDir);

        try {
            switch (browserName.toLowerCase()) {

                case "chrome":
                    System.setProperty("webdriver.chrome.driver", driverDir + "chromedriver.exe");
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--start-maximized");
                    driver = new ChromeDriver(chromeOptions);
                    logger.info("Chrome browser launched successfully.");
                    break;

                case "firefox":
                    System.setProperty("webdriver.gecko.driver", driverDir + "geckodriver.exe");
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--width=1366");
                    firefoxOptions.addArguments("--height=768");
                    driver = new FirefoxDriver(firefoxOptions);
                    logger.info("Firefox browser launched successfully.");
                    break;

                case "edge":
                    System.setProperty("webdriver.edge.driver", driverDir + "msedgedriver.exe");
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized");
                    driver = new EdgeDriver(edgeOptions);
                    logger.info("Edge browser launched successfully.");
                    break;

                default:
                    throw new IllegalArgumentException("Invalid browser name: " + browserName);
            }

            // Apply default waits and setup
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();

        } catch (Exception e) {
            logger.error("Failed to initialize browser driver: " + e.getMessage(), e);
            throw new RuntimeException("Browser initialization failed for: " + browserName);
        }

        return driver;
    }
}
