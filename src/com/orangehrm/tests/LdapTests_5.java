package com.orangehrm.tests;

import java.awt.event.KeyEvent;
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
 * LdapTests_5
 *
 * LDAP Configuration: toggles, dropdowns, disclaimers, additional settings.
 */
public class LdapTests_5 extends BaseTest {

    // TC_023 - Click all toggles and Save
    @Test(priority = 23, description = "Verify all LDAP toggle buttons and Save behavior")
    public void verifyAllTogglesAndSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.ldapToggleBtn)).click();
        List<WebElement> toggles = ldapPage.allTogglesBtn;
        Reporter.log("Clicking all toggle buttons: " + toggles.size(), true);

        try {
            for (WebElement toggle : toggles) {
                wait.until(ExpectedConditions.elementToBeClickable(toggle)).click();
            }
        } catch (Exception e) {
            Reporter.log("Toggle clicking interrupted, verifying Save button", true);
            wait.until(ExpectedConditions.elementToBeClickable(ldapPage.saveButton)).click();
            if (ldapPage.requiredText.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400)");
                ScreenshotUtil.captureScreenshot(driver, "saveButtonNotWorking");
            }
        }
    }

    // TC_024 - Encryption dropdown + Save
    @Test(priority = 24, description = "Verify encryption dropdown and Save functionality")
    public void verifyEncryptionDropdownAndSave() throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.encryptionDropdown)).click();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.saveButton)).click();
        if (ldapPage.requiredText.isDisplayed()) {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400)");
            ScreenshotUtil.captureScreenshot(driver, "saveButtonFailed");
        } else {
            Reporter.log("Save button worked successfully", true);
        }
    }

    // TC_025 - Test Connection after encryption
    @Test(priority = 25, description = "Verify Test Connection button after selecting encryption")
    public void verifyTestConnectionButtonAfterEncryption() throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.encryptionDropdown)).click();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ldapPage.testConnectionButton);
        Thread.sleep(500);
        ScreenshotUtil.captureScreenshot(driver, "testConnectionAfterEncryption");
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.testConnectionButton)).click();
    }

    // TC_026 - Disclaimer visibility
    @Test(priority = 26, description = "Verify Disclaimer Message visibility")
    public void verifyDisclaimerMessage() {
        boolean isDisplayed = ldapPage.disclaimerMessage.isDisplayed();
        Reporter.log("Disclaimer Message visible: " + isDisplayed, true);
        Assert.assertTrue(isDisplayed, "Disclaimer Message not visible");
    }

    // TC_028 - Work Email toggle required behavior
    @Test(priority = 28, description = "Verify Work Email toggle and required text")
    public void verifyWorkEmailToggle() {
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.workMailToggleBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.saveButton)).click();
        Assert.assertTrue(ldapPage.workEmailRequiredText.isDisplayed(), "Work Email required text not visible");
    }

    // TC_029 - Employee ID toggle required behavior
    @Test(priority = 29, description = "Verify Employee ID toggle and required text")
    public void verifyEmployeeIdToggle() {
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.empIdToggleBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.saveButton)).click();
        Assert.assertTrue(ldapPage.empIdRequiredText.isDisplayed(), "Employee ID required text not visible");
    }

    // TC_030 - Add Settings button
    @Test(priority = 30, description = "Verify Add Settings button functionality")
    public void verifyAddSettingsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(ldapPage.addSettingsBtn)).click();
        Reporter.log("Clicked Add Settings button", true);
    }
}
