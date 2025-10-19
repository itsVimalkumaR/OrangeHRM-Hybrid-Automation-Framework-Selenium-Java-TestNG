package com.orangehrm.tests;

import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

/**
 * LdapTests_4
 *
 * LDAP Configuration: Save/Test Connection button & toggle interactions.
 */
public class LdapTests_4 extends BaseTest {

    // TC_016 - Verify Save button
    @Test(priority = 16, description = "Verify Save button functionality")
    public void verifySaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.saveButton)).click();
        Reporter.log("Clicked on Save button", true);
        Assert.assertTrue(ldapPage.saveButton.isEnabled(), "Save button not functional");
    }

    // TC_018 - Verify Bind Settings button
    @Test(priority = 18, description = "Verify Bind Settings button visibility")
    public void verifyBindButton() {
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.bindButton)).click();
        Reporter.log("Clicked Bind Settings button", true);
        Assert.assertTrue(ldapPage.bindButton.isDisplayed(), "Bind button hidden");
    }

    // TC_020 - Click LDAP toggle and Save
    @Test(priority = 20, description = "Click LDAP toggle and then Save button")
    public void clickLdapToggleAndSave() {
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.ldapToggleBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.saveButton)).click();
        Reporter.log("Clicked LDAP toggle and Save button", true);
    }

    // TC_021 - Click LDAP toggle and Test Connection
    @Test(priority = 21, description = "Click LDAP toggle and Test Connection button")
    public void clickLdapToggleAndTestConnection() {
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.ldapToggleBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.testConnectionButton)).click();
        Reporter.log("Clicked Test Connection button", true);
        Assert.assertTrue(ldapPage.testConnectionButton.isEnabled(), "Test Connection button not working");
    }

    // TC_022 - Click all toggles and handle exceptions
    @Test(priority = 22, description = "Click all toggle buttons and handle exceptions")
    public void clickAllTogglesAndTestConnection() {
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.ldapToggleBtn)).click();
        List<WebElement> toggles = ldapPage.allTogglesBtn;
        Reporter.log("Clicking all toggle buttons: " + toggles.size(), true);

        try {
            for (WebElement toggle : toggles) {
                wait.until(ExpectedConditions.elementToBeClickable(toggle)).click();
            }
        } catch (Exception e) {
            Reporter.log("Toggle clicking failed, trying Test Connection button", true);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(ldapPage.testConnectionButton)).click();
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400)");
                ScreenshotUtil.captureScreenshot(driver, "testConnectionAfterToggleFailure");
            } catch (Exception ex) {
                Assert.fail("Failed to click Test Connection button after toggle failure", ex);
            }
        }
    }
}