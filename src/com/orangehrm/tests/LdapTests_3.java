package com.orangehrm.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

/**
 * LdapTests_3
 *
 * Verifies Admin configuration headings, external links, titles, and connection button.
 */
public class LdapTests_3 extends BaseTest {

    private WebDriverWait wait;

    private void initWait() {
        if (wait == null) wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // TC_011
    @Test(priority = 11, description = "Verify Admin/Configuration page heading")
    public void verifyAdminConfigurationHeading() throws IOException {
        String heading = ldapPage.adminHeading.getText().trim() + "/" + ldapPage.configHeading.getText().trim();
        String expected = "Admin/Configuration";

        try {
            Assert.assertEquals(heading, expected, "Heading mismatch.");
            Reporter.log("Verified heading: " + heading, true);
        } catch (AssertionError e) {
            ScreenshotUtil.captureScreenshot(driver, "AdminConfigHeading");
            Assert.fail("Heading verification failed: " + heading);
        }
    }

    // TC_012
    @Test(priority = 12, description = "Verify OrangeHRM external link URL")
    public void verifyOrangeHrmExternalLink() throws IOException {
        initWait();
        wait.until(ExpectedConditions.visibilityOf(ldapPage.OrgLink));

        String actualUrl = ldapPage.OrgLink.getAttribute("href").trim();
        String expectedUrl = "http://www.orangehrm.com/";
        try {
            Assert.assertEquals(actualUrl, expectedUrl, "External link URL mismatch.");
            Reporter.log("Verified external link: " + actualUrl, true);
        } catch (AssertionError e) {
            ScreenshotUtil.captureScreenshot(driver, "OrangeHrmExternalLink");
            Assert.fail("External link verification failed.");
        }
    }

    // TC_013
    @Test(priority = 13, description = "Verify current page title")
    public void verifyCurrentPageTitle() {
        String title = driver.getTitle();
        Reporter.log("Current page title: " + title, true);
        Assert.assertTrue(title != null && !title.isEmpty(), "Page title empty.");
    }

    // TC_014
    @Test(priority = 14, description = "Verify redirect link and window handling")
    public void verifyRedirectLinkAndTitle() throws IOException {
        initWait();
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.HRSoftawre)).click();

        Set<String> windows = driver.getWindowHandles();
        ArrayList<String> winList = new ArrayList<>(windows);

        if (winList.size() > 1) {
            driver.switchTo().window(winList.get(1));
            String newTitle = driver.getTitle();
            Reporter.log("New window title: " + newTitle, true);
            Assert.assertEquals(newTitle, "Human Resources Management Software | HRMS | OrangeHRM");

            driver.close();
            driver.switchTo().window(winList.get(0));
            Reporter.log("Back to main window title: " + driver.getTitle(), true);
        } else {
            ScreenshotUtil.captureScreenshot(driver, "RedirectLink");
            Assert.fail("Redirect window did not open.");
        }
    }

    // TC_015
    @Test(priority = 15, description = "Verify 'Test Connection' button functionality")
    public void verifyTestConnectionButton() {
        ldapPage.testConnectionButton.click();
        Reporter.log("'Test Connection' button clicked.", true);
        Assert.assertTrue(ldapPage.testConnectionButton.isEnabled(), "Button not clickable.");
    }
}