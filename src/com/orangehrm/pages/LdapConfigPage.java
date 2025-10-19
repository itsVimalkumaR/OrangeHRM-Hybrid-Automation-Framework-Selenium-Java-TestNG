package com.orangehrm.pages;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseOfPOM;
import com.orangehrm.utilities.Log;

/**
 * Page object for LDAP Configuration page.
 */
public class LdapConfigPage extends BaseOfPOM {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LdapConfigPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Open LDAP configuration from wherever the current page is (assumes admin nav must be used).
     * This method will attempt to click the ldapConfigLink from BaseOfPOM.
     */
    public void openLdapConfiguration() {
        wait.until(ExpectedConditions.elementToBeClickable(ldapConfigLink)).click();
        wait.until(ExpectedConditions.visibilityOf(ldapHeading));
    }

    public boolean isOnLdapPage() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(saveButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

 // In LdapConfigPage.java - Add these methods
    public boolean waitForSuccessMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Try multiple success message patterns
            List<By> successLocators = Arrays.asList(
                By.xpath("//div[contains(@class, 'oxd-toast')]//p[contains(@class, 'oxd-text')]"),
                By.xpath("//div[contains(@class, 'oxd-toast')]"),
                By.xpath("//*[contains(text(), 'Success') or contains(text(), 'success')]"),
                By.xpath("//*[contains(text(), 'Updated') or contains(text(), 'updated')]"),
                By.xpath("//*[contains(text(), 'Saved') or contains(text(), 'saved')]")
            );
            
            for (By locator : successLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    if (element.isDisplayed()) {
                        Log.info("Success message found with locator: " + locator);
                        return true;
                    }
                } catch (Exception e) {
                    // Continue to next locator
                }
            }
            return false;
        } catch (Exception e) {
            Log.error("Error waiting for success message: " + e.getMessage());
            return false;
        }
    }

    public boolean waitForConnectionPopup() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Try multiple popup patterns
            List<By> popupLocators = Arrays.asList(
                By.xpath("//*[contains(text(), 'Connection Status')]"),
                By.xpath("//div[contains(@class, 'oxd-dialog')]"),
                By.xpath("//div[contains(@class, 'modal')]"),
                By.xpath("//div[@role='dialog']"),
                By.xpath("//*[contains(text(), 'Test Connection')]")
            );
            
            for (By locator : popupLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    if (element.isDisplayed()) {
                        Log.info("Connection popup found with locator: " + locator);
                        return true;
                    }
                } catch (Exception e) {
                    // Continue to next locator
                }
            }
            return false;
        } catch (Exception e) {
            Log.error("Error waiting for connection popup: " + e.getMessage());
            return false;
        }
    }

    public boolean isSaveEnabled() {
        try {
            return saveButton.isEnabled() && !saveButton.getAttribute("class").contains("disabled");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTestConnectionEnabled() {
        try {
            return testConnectionButton.isEnabled() && !testConnectionButton.getAttribute("class").contains("disabled");
        } catch (Exception e) {
            return false;
        }
    }
}
