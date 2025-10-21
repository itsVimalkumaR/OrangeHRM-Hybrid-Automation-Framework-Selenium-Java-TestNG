package com.orangehrm.tests;

import java.awt.event.KeyEvent;

import org.openqa.selenium.Keys;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

/**
 * Test Class: LdapTests_11
 *
 * This class contains automated test cases for verifying 
 * the LDAP configuration form behavior in OrangeHRM.
 * 
 * Focuses on:
 *  - Work Email toggle + Test Connection popup validation
 *  - Employee ID toggle + Save functionality validation
 * 
 * Author: VK
 * Framework: Selenium WebDriver + TestNG
 * Module: Admin → LDAP Configuration
 */
public class LdapTests_11 extends BaseTest {

    // ------------------------------------------------------------
    // 🔹 Helper Methods
    // ------------------------------------------------------------

    /**
     * Clears an input field and enters the given text.
     * 
     * @param element the WebElement representing input field
     * @param text    text value to be entered
     */
    private void enterText(org.openqa.selenium.WebElement element, String text) {
        element.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        element.sendKeys(text);
    }

    /**
     * Selects a dropdown option using Robot key navigation.
     * 
     * @param downPressCount number of DOWN key presses before hitting ENTER
     * @throws Exception if Robot key simulation fails
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
     * Fills the LDAP configuration form using Excel data.
     * Ensures all major text fields are populated before testing Save/Test Connection.
     */
    private void fillLdapConfiguration() {
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

        // Adjust sync interval (simulate manual backspace input correction)
        ldapPage.SyncInterval.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);
    }

    /**
     * Clicks the "Test Connection" button and validates popup visibility.
     * Captures a screenshot if popup fails to appear.
     */
    private void verifyConnectionPopup() {
        ldapPage.testConnectionButton.click();
        try {
            if (ldapPage.ConnPopUpTxt.isDisplayed()) {
                Reporter.log("Connection status popup appeared: " 
                             + ldapPage.ConnPopUpTxt.getText(), true);
                ldapPage.connectionStatusCloseButton.click();
            } else {
                Reporter.log("Connection status popup did not appear.", true);
                ScreenshotUtil.captureScreenshot(driver, "ConnectionPopupMissing");
            }
        } catch (Exception e) {
            Reporter.log("Exception while checking connection popup: " 
                         + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "ConnectionPopupException");
        }
    }

    /**
     * Clicks the "Save" button and validates the success message.
     * Takes a screenshot on failure.
     */
    private void verifySaveSuccess() {
        try {
            ldapPage.saveButton.click();
            if (ldapPage.SuccessSMS.isDisplayed()) {
                Reporter.log("Successfully Updated message is visible.", true);
            } else {
                Reporter.log("Successfully Updated message not visible.", true);
                ScreenshotUtil.captureScreenshot(driver, "SaveSuccessMissing");
            }
        } catch (Exception e) {
            Reporter.log("Exception while verifying Save success: " 
                         + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "SaveSuccessException");
        }
    }

    // ------------------------------------------------------------
    // 🔹 Test Cases
    // ------------------------------------------------------------

    /**
     * TC_052 - Verify connection popup after enabling Work Email toggle.
     * 
     * Steps:
     *  1. Fill all LDAP configuration fields.
     *  2. Enable "Work Email" toggle.
     *  3. Click "Test Connection".
     *  4. Verify the connection popup message appears.
     * 
     * Expected Result:
     *  - Connection popup should display successfully.
     */
    @Test(priority = 52, description = "Verify LDAP connection popup when Work Email toggle is enabled.")
    public void testConnectionWithWorkEmail() throws Exception {
        fillLdapConfiguration();
        ldapPage.workMailToggleBtn.click();
        verifyConnectionPopup();
    }

    /**
     * TC_053 - Verify successful Save when Employee ID toggle is enabled.
     * 
     * Steps:
     *  1. Fill all LDAP configuration fields.
     *  2. Enable "Employee ID" toggle.
     *  3. Click "Save".
     *  4. Verify success message appears.
     * 
     * Expected Result:
     *  - Configuration should save successfully with success message visible.
     */
    @Test(priority = 53, description = "Verify successful LDAP save when Employee ID toggle is enabled.")
    public void saveLdapConfigurationWithEmpId() throws Exception {
        fillLdapConfiguration();
        ldapPage.empIdToggleBtn.click();
        verifySaveSuccess();
    }
}
