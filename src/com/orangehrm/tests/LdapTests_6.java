package com.orangehrm.tests;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

/**
 * LdapTests_6
 *
 * This class validates:
 * - Required field messages after Save/Test actions
 * - OrangeHRM logo & link validation
 * - Bind settings hide/show behaviors
 */
public class LdapTests_6 extends BaseTest {

    /* ----------------------------------------------------------------------
     * Utility method to check required messages visibility
     * ---------------------------------------------------------------------- */
    private boolean checkRequiredMessages(String screenshotName) {
        boolean visible = false;
        for (WebElement elem : ldapPage.AllRequiredTxt) {
            if (elem.isDisplayed()) {
                visible = true;
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
                ScreenshotUtil.captureScreenshot(driver, screenshotName);
                Reporter.log("Required message is visible", true);
                break;
            }
        }
        return visible;
    }

    /* ----------------------------------------------------------------------
     * SECTION 1: Validation of Required Field Messages on Save
     * ---------------------------------------------------------------------- */

    @Test(priority = 31, description = "Verify required field messages appear after clicking Save button")
    public void verifyRequiredMessageOnSave() {
        ldapPage.saveButton.click();
        Assert.assertTrue(checkRequiredMessages("RequiredMessageOnSave"), 
                "No required message displayed after Save");
    }

    @Test(priority = 32, description = "Count total number of required messages after Save click")
    public void countRequiredMessagesOnSave() {
        ldapPage.saveButton.click();
        int count = ldapPage.AllRequiredTxt.size();
        Reporter.log("Total required messages displayed after Save: " + count, true);
    }

    /* ----------------------------------------------------------------------
     * SECTION 2: OrangeHRM Logo & Link Verification
     * ---------------------------------------------------------------------- */

 // TC_033
    @Test(priority = 33, description = "Verify OrangeHRM image and footer link URLs are same (protocol agnostic)")
    public void verifyOrangeHRMLogoAndLinkSame() {
        // Click on the logo to get its URL
        ldapPage.OrgHrmImage.click();
        String expectedUrl = driver.getCurrentUrl().trim();
        
        // Navigate back to main page
        driver.navigate().back();
        
        // Get the href from footer link
        String actualUrl = ldapPage.OrgLink.getAttribute("href").trim();

        // Normalize URLs by removing protocol (http:// or https://)
        expectedUrl = expectedUrl.replaceFirst("^https?://", "");
        actualUrl = actualUrl.replaceFirst("^https?://", "");

        // Compare normalized URLs
        if (expectedUrl.equals(actualUrl)) {
            Reporter.log("Both the links are same: " + expectedUrl, true);
        } else {
            Reporter.log("Links are not same. Logo URL: " + expectedUrl + ", Footer URL: " + actualUrl, true);
            Assert.fail("OrangeHRM logo and footer link URLs do not match");
        }
    }


    @Test(priority = 34, description = "Verify OrangeHRM image and link open the same URL in new tab")
    public void verifyOrangeHRMLogoAndLinkNewTab() {
        ldapPage.OrgHrmImage.click();
        String expectedUrl = driver.getCurrentUrl();
        driver.navigate().back();

        ldapPage.OrgLink.click();
        Set<String> windowHandles = driver.getWindowHandles();
        ArrayList<String> tabs = new ArrayList<>(windowHandles);

        // Switch to new tab
        driver.switchTo().window(tabs.get(1));
        String actualUrl = driver.getCurrentUrl();
        driver.close();

        // Switch back to main window
        driver.switchTo().window(tabs.get(0));

        Assert.assertEquals(actualUrl, expectedUrl, "OrangeHRM link opened different URL in new tab");
        Reporter.log("OrangeHRM logo and link open the same URL in new tab", true);
    }

    /* ----------------------------------------------------------------------
     * SECTION 3: Test Connection Button - Required Message Validation
     * ---------------------------------------------------------------------- */

    @Test(priority = 35, description = "Verify required messages appear after clicking Test Connection button")
    public void verifyRequiredMessageOnTestConnection() {
        ldapPage.testConnectionButton.click();
        Assert.assertTrue(checkRequiredMessages("RequiredMessageOnTestConnection"), 
                "No required message displayed after Test Connection");
    }

    @Test(priority = 36, description = "Count total number of required messages after clicking Test Connection button")
    public void countRequiredMessagesOnTestConnection() {
        ldapPage.testConnectionButton.click();
        int count = ldapPage.AllRequiredTxt.size();
        Reporter.log("Total required messages after Test Connection: " + count, true);
    }

    /* ----------------------------------------------------------------------
     * SECTION 4: Bind Settings Hide/Show Behavior
     * ---------------------------------------------------------------------- */

    @Test(priority = 37, description = "Verify Save behavior after hiding Bind Settings")
    public void verifySaveAfterHidingBindSettings() {
        ldapPage.bindButton.click();
        ldapPage.saveButton.click();
        int count = ldapPage.ReqCount.size();
        Reporter.log("After hiding Bind Settings, Save shows " + count + " required messages", true);
    }

    @Test(priority = 38, description = "Verify Test Connection behavior after hiding Bind Settings")
    public void verifyTestConnectionAfterHidingBindSettings() {
        ldapPage.bindButton.click();
        ldapPage.testConnectionButton.click();
        int count = ldapPage.ReqCount.size();
        Reporter.log("After hiding Bind Settings, Test Connection shows " + count + " required messages", true);
    }
}
