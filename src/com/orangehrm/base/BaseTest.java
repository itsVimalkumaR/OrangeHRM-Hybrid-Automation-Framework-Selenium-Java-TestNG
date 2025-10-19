package com.orangehrm.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;

import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.LdapConfigPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.reports.ExtentManager;
import com.orangehrm.utilities.ConfigReader;
import com.orangehrm.utilities.ExcelUtil;
import com.orangehrm.utilities.WebDriverFactory;

import org.testng.asserts.SoftAssert;

/**
 * Base test class to initialize driver, config, reports, and login/logout hooks.
 * Ensures proper lifecycle control for TestNG test execution.
 */
public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ConfigReader config;
    protected LoginPage loginPage;
    protected SoftAssert softAssert;
    protected LdapConfigPage ldapPage;
	protected Robot robot;
	protected Actions actions;
	protected ExcelUtil excelUtil;
    
    public static Logger logger = Logger.getLogger(BaseTest.class);

    // -------------------- SUITE LEVEL --------------------
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        PropertyConfigurator.configure("config/log4j.properties");
        logger.info("Log4j configured from: config/log4j.properties");
        
        ExtentManager.getInstance();
        logger.info("Extent report initialized via ExtentManager");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        try {
            ExtentManager.getInstance().flush();
            logger.info("Extent report flushed successfully.");
        } catch (Exception e) {
            logger.error("Error while flushing Extent report: " + e.getMessage());
        }
    }

    // -------------------- CLASS LEVEL --------------------
    @BeforeClass(alwaysRun = true)
    public void setupClass() throws IOException, AWTException {
    	config = new ConfigReader();
        driver = WebDriverFactory.createDriver(config.getBrowser());
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Add this line
        driver.get(config.getUrl());

        softAssert = new SoftAssert();
        loginPage = new LoginPage(driver);
        ldapPage = new LdapConfigPage(driver);
        robot = new Robot();
        actions = new Actions(driver);
        
        // Initialize Excel with better error handling
        try {
            String excelPath = System.getProperty("user.dir") + "/testdata/OrgHrmData.xlsx";
            File file = new File(excelPath);
            System.out.println("File exists: " + file.exists());
            System.out.println("File path: " + file.getAbsolutePath());
            System.out.println("File size: " + file.length());
            
            // Change sheet name from "testdata" to "Data"
            excelUtil = new ExcelUtil(excelPath, "Data"); // Updated sheet name
            logger.info("Excel initialized successfully from: " + excelPath);
        } catch (Exception e) {
            logger.error("Excel initialization failed: " + e.getMessage());
            e.printStackTrace(); // Add this for better debugging
            logger.warn("Tests will continue without Excel data");
        }
        
        logger.info("Browser launched and navigated to: " + config.getUrl());
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        try {
            if (driver != null) {
                driver.quit();
                softAssert.assertAll();
                logger.info("Browser closed successfully.");
            }
        } catch (Exception e) {
            logger.error("Error during browser quit: " + e.getMessage());
        } finally {
            if (softAssert != null) softAssert.assertAll();
        }
    }

    // -------------------- METHOD LEVEL --------------------
 // In BaseTest.java - Update the @BeforeMethod
    @BeforeMethod(alwaysRun = true)
    public void testLoginAndNavigateToLDAP() {
        try {
            // Check if we're already logged in and on the right page
            try {
                if (driver.getCurrentUrl().contains("dashboard")) {
                    // Already logged in, just navigate to LDAP
                    AdminPage adminPage = new AdminPage(driver);
                    adminPage.navigateToLdapConfig();
                    LdapConfigPage ldapPage = new LdapConfigPage(driver);
                    if (ldapPage.isOnLdapPage()) {
                        Reporter.log("User successfully navigated to LDAP Configuration page", true);
                        return;
                    }
                }
            } catch (Exception e) {
                // If check fails, proceed with fresh login
            }
            
            // Fresh login
            loginPage.login(config.getUsername(), config.getPassword());
            logger.info("Logged in successfully using provided credentials.");
            AdminPage adminPage = new AdminPage(driver);
            adminPage.navigateToLdapConfig();
            LdapConfigPage ldapPage = new LdapConfigPage(driver);
            assert ldapPage.isOnLdapPage(): "LDAP configuration page is not displayed!";
            Reporter.log("User successfully navigated to LDAP Configuration page", true);
        } catch (Exception e) {
            logger.error("Login failed: " + e.getMessage());
            
            // Try to recover by refreshing and logging in again
            try {
                driver.navigate().refresh();
                Thread.sleep(2000);
                loginPage.login(config.getUsername(), config.getPassword());
                AdminPage adminPage = new AdminPage(driver);
                adminPage.navigateToLdapConfig();
                Reporter.log("Recovered and navigated to LDAP Configuration page", true);
            } catch (Exception ex) {
                logger.error("Recovery also failed: " + ex.getMessage());
                throw e;
            }
        }
    }
    
    @AfterMethod(alwaysRun = true)
    public void logout() {
        try {
            if (loginPage != null && loginPage.isLoggedIn()) {
                loginPage.logout();
                logger.info("Logged out successfully.");
            }
        } catch (Exception e) {
            logger.warn("Logout failed or not applicable: " + e.getMessage());
        }
    }

    // -------------------- GETTER --------------------
    public WebDriver getDriver() {
        return driver;
    }

	public void setUp() throws AWTException, Exception {
		// TODO Auto-generated method stub
		
	}
}
