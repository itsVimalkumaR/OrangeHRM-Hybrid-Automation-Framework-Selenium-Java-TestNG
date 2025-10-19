// ConfigReader.java

package com.orangehrm.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Loads configuration values from config/config.properties
 */
public class ConfigReader {
    private final Properties prop = new Properties();
    private static final Logger logger = Logger.getLogger(WebDriverFactory.class);

    public ConfigReader() throws IOException {
        String path = System.getProperty("user.dir") + "/config/config.properties";
        try (InputStream is = new FileInputStream(path)) {
            prop.load(is);
            System.out.println("[INFO] The config.properties file was loaded.");
        } catch (IOException e) {
            System.err.println("Unable to load config.properties from: " + path);
            throw e;
        }
    }

    public String getUrl() {
        return prop.getProperty("url");
    }

    public String getUsername() {
        return prop.getProperty("username");
    }

    public String getPassword() {
        return prop.getProperty("password");
    }

    public String getBrowser() {
        return prop.getProperty("browser", "chrome");
    }

    public int getImplicitWait() {
        try {
            return Integer.parseInt(prop.getProperty("implicit.wait", "10"));
        } catch (NumberFormatException e) {
            return 10;
        }
    }
}