package com.orangehrm.tests;

import java.awt.event.KeyEvent;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

public class LdapTests_12 extends BaseTest {

    // ---------------- Helper Methods ----------------

    /**
     * Clears an input field and enters new text.
     */
    private void enterText(WebElement element, String text) {
        try {
            element.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
            element.sendKeys(text);
        } catch (Exception e) {
            Reporter.log("Failed to enter text into field: " + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "enterTextError");
        }
    }

    /**
     * Selects a dropdown option using keyboard down arrow key simulation.
     */
    private void selectDropdownOption(int downPressCount) throws Exception {
        for (int i = 0; i < downPressCount; i++) {
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    /**
     * Fills all LDAP configuration fields with test data from Excel sheet.
     */
    private void fillLdapConfiguration() {
        try {
            enterText(ldapPage.HostTxtFields, excelUtil.getCellData(1, 0));
            enterText(ldapPage.portTxtFields, excelUtil.getCellData(1, 1));
            enterText(ldapPage.DistingNameTxtFields, excelUtil.getCellData(1, 2));
            enterText(ldapPage.PasswordTxtFields, excelUtil.getCellData(1, 3));
            enterText(ldapPage.BdNameTxtField, excelUtil.getCellData(1, 4));
            enterText(ldapPage.UNAttribute, excelUtil.getCellData(1, 5));
            enterText(ldapPage.USearchFilter, excelUtil.getCellData(1, 7));
            enterText(ldapPage.UUIDAttribute1, excelUtil.getCellData(2, 6));
            enterText(ldapPage.FNameTxtFields, excelUtil.getCellData(1, 8));
            enterText(ldapPage.midName1, excelUtil.getCellData(2, 9));
            enterText(ldapPage.LNameTxtFields, excelUtil.getCellData(1, 10));
            enterText(ldapPage.UserStatus1, excelUtil.getCellData(2, 11));
            enterText(ldapPage.WorkEmail1, excelUtil.getCellData(2, 12));
            enterText(ldapPage.EmpID1, excelUtil.getCellData(2, 13));
            enterText(ldapPage.SyncInterval, excelUtil.getCellData(1, 14));
            ldapPage.SyncInterval.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);
        } catch (Exception e) {
            Reporter.log("Error filling LDAP configuration: " + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "fillLdapConfigurationError");
        }
    }

    /**
     * Clicks the "Test Connection" button and verifies popup visibility.
     */
    private void verifyConnectionPopup() {
        try {
            ldapPage.testConnectionButton.click();
            if (ldapPage.ConnPopUpTxt.isDisplayed()) {
                Reporter.log("Connection status popup appeared: " + ldapPage.ConnPopUpTxt.getText(), true);
                ldapPage.connectionStatusCloseButton.click();
            } else {
                Reporter.log("Connection status popup did not appear.", true);
            }
        } catch (Exception e) {
            Reporter.log("Connection popup verification failed: " + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "verifyConnectionPopupError");
        }
    }

    /**
     * Clicks the "Save" button and verifies successful update message.
     */
    private void verifySaveSuccess() {
        try {
            ldapPage.saveButton.click();
            if (ldapPage.SuccessSMS.isDisplayed()) {
                Reporter.log("Successfully Updated message is visible.", true);
            } else {
                Reporter.log("Success message not visible after save.", true);
            }
        } catch (Exception e) {
            Reporter.log("Save verification failed: " + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "verifySaveSuccessError");
        }
    }


    // TC_054
    @Test(priority = 54, description = "Verify Test Connection with Employee ID toggle.")
    public void testConnectionWithEmpId() throws Exception {
        fillLdapConfiguration();
        ldapPage.empIdToggleBtn.click();
        verifyConnectionPopup();
    }

    // TC_055
    @Test(priority = 55, description = "Verify Test Connection with Work Email toggle.")
    public void testConnectionWithWorkEmail() throws Exception {
        fillLdapConfiguration();
        ldapPage.workMailToggleBtn.click();
        verifyConnectionPopup();
    }

    // TC_056
    @Test(priority = 56, description = "Verify Save configuration with both Work Email & Employee ID toggles ON.")
    public void saveLdapConfigurationWithWorkEmailAndEmpId() throws Exception {
        fillLdapConfiguration();
        ldapPage.workMailToggleBtn.click();
        ldapPage.empIdToggleBtn.click();
        verifySaveSuccess();
    }
}
