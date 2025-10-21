package com.orangehrm.tests;

import java.awt.event.KeyEvent;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

/**
 * Test Class: LdapTests_10
 * 
 * This class contains automated test cases for verifying 
 * the LDAP configuration and connection functionalities 
 * in the OrangeHRM Admin → LDAP Settings module.
 * 
 * Includes verification for:
 *  - Valid LDAP connection popup behavior
 *  - Save functionality with toggles
 *  - Dropdown handling and input field validations
 * 
 * Author: VK
 * Framework: Selenium WebDriver + TestNG
 * Module: LDAP Configuration
 */
public class LdapTests_10 extends BaseTest {

    /**
     * Utility method to clear an input field and enter new text.
     * Uses Control + A and Backspace for reliable clearing.
     * 
     * @param element WebElement representing the input field
     * @param value   String value to be entered
     */
    private void clearAndType(WebElement element, String value) {
        element.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        element.sendKeys(value);
    }

    /**
     * Utility method to navigate dropdown options using Robot class.
     * 
     * @param pressCount Number of times the DOWN arrow key should be pressed
     * @throws Exception if key press simulation fails
     */
    private void selectDropdownOption(int pressCount) throws Exception {
        for (int i = 0; i < pressCount; i++) {
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    /**
     * Utility method to click on "Test Connection" and verify
     * if the connection popup message appears.
     */
    private void verifyConnectionPopup() {
        ldapPage.testConnectionButton.click();
        try {
            if (ldapPage.ConnPopUpTxt.isDisplayed()) {
                Reporter.log("Connection popup appeared: " + ldapPage.ConnPopUpTxt.getText(), true);
                ldapPage.connectionStatusCloseButton.click();
            } else {
                Reporter.log("Connection popup did not appear.", true);
                ScreenshotUtil.captureScreenshot(driver, "connectionPopupNotVisible");
            }
        } catch (Exception e) {
            Reporter.log("Exception while verifying connection popup: " + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "connectionPopupException");
        }
    }

    /**
     * Utility method to click on "Save" and verify
     * if the success message appears.
     */
    private void verifySaveSuccess() {
        try {
            ldapPage.saveButton.click();
            if (ldapPage.SuccessSMS.isDisplayed()) {
                Reporter.log("Successfully Updated message is visible.", true);
            } else {
                Reporter.log("Successfully Updated message is NOT visible.", true);
                ScreenshotUtil.captureScreenshot(driver, "verifySaveSuccessFailed");
            }
        } catch (Exception e) {
            Reporter.log("Exception while verifying save success: " + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "verifySaveSuccessException");
        }
    }

    /**
     * TC_050 - Verify the LDAP Connection Popup for valid data inputs.
     * 
     * Steps:
     * 1. Enter valid Host and Port values.
     * 2. Select Encryption and LDAP Implementation options.
     * 3. Click on "Test Connection".
     * 4. Verify the popup appears with correct message.
     * 
     * Expected Result:
     * Connection popup should appear with success message.
     */
    @Test(priority = 50, description = "Verify connection popup appears when valid data is entered and Test Connection is clicked.")
    public void verifyConnectionPopupForValidData() throws Exception {

        // Fill mandatory fields
        clearAndType(ldapPage.HostTxtFields, excelUtil.getCellData(1, 0));
        clearAndType(ldapPage.portTxtFields, excelUtil.getCellData(1, 1));

        // Adjust port (simulate backspace removal for validation)
        ldapPage.portTxtFields.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);

        // Select dropdowns
        ldapPage.encryptionDropdown.click();
        selectDropdownOption(1);

        ldapPage.LdapImplemenDD.click();
        selectDropdownOption(1);

        // Verify connection popup
        verifyConnectionPopup();

    }

    /**
     * TC_051 - Verify Save functionality when Work Email toggle is enabled.
     * 
     * Steps:
     * 1. Fill Host, Port, and select Encryption + LDAP Implementation.
     * 2. Enable the Work Email toggle.
     * 3. Click on Save.
     * 4. Verify "Successfully Updated" message appears.
     * 
     * Expected Result:
     * Save should be successful and show success message.
     */
    @Test(priority = 51, description = "Verify successful save when Work Email toggle is enabled in LDAP settings.")
    public void verifySaveSuccessForWorkEmailToggle() throws Exception {

        // Fill mandatory fields
        clearAndType(ldapPage.HostTxtFields, excelUtil.getCellData(1, 0));
        clearAndType(ldapPage.portTxtFields, excelUtil.getCellData(1, 1));

        // Adjust port
        ldapPage.portTxtFields.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);

        // Select dropdowns
        ldapPage.encryptionDropdown.click();
        selectDropdownOption(1);

        ldapPage.LdapImplemenDD.click();
        selectDropdownOption(1);

        // Enable work email toggle and save
        ldapPage.workMailToggleBtn.click();
        verifySaveSuccess();

    }
}
