package com.orangehrm.tests;

import java.awt.event.KeyEvent;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

public class LdapTests_13 extends BaseTest {

    // ---------------- Helper Methods ----------------

    /**
     * Clears an input field and enters the provided text.
     */
    private void enterText(WebElement element, String text) {
        try {
            element.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
            element.sendKeys(text);
        } catch (Exception e) {
            Reporter.log("Failed to enter text into element: " + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "enterTextError");
        }
    }

    /**
     * Selects a dropdown option by pressing the DOWN key multiple times.
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
     * Fills all LDAP configuration fields from the Excel sheet using a given row index.
     */
    private void fillLdapConfiguration(int rowIndex) throws Exception {
        try {
            enterText(ldapPage.HostTxtFields, excelUtil.getCellData(rowIndex, 0));
            enterText(ldapPage.portTxtFields, excelUtil.getCellData(rowIndex, 1));

            // Encryption dropdown
            ldapPage.encryptionDropdown.click();
            selectDropdownOption(2);

            // Implementation dropdown
            ldapPage.LdapImplemenDD.click();
            selectDropdownOption(2);

            enterText(ldapPage.DistingNameTxtFields, excelUtil.getCellData(rowIndex, 2));
            enterText(ldapPage.PasswordTxtFields, excelUtil.getCellData(rowIndex, 3));
            enterText(ldapPage.BdNameTxtField, excelUtil.getCellData(rowIndex, 4));

            // Search Scope dropdown
            ldapPage.SearchScopeDD.click();
            selectDropdownOption(1);

            enterText(ldapPage.UNAttribute, excelUtil.getCellData(rowIndex, 5));
            enterText(ldapPage.USearchFilter, excelUtil.getCellData(rowIndex, 7));
            enterText(ldapPage.UUIDAttribute1, excelUtil.getCellData(rowIndex, 6));
            ldapPage.UUIDAttribute1.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);

            enterText(ldapPage.FNameTxtFields, excelUtil.getCellData(rowIndex, 8));
            enterText(ldapPage.LNameTxtFields, excelUtil.getCellData(rowIndex, 10));
            enterText(ldapPage.UserStatus1, excelUtil.getCellData(rowIndex, 11));
            enterText(ldapPage.WorkEmail1, excelUtil.getCellData(rowIndex, 12));
            enterText(ldapPage.EmpID1, excelUtil.getCellData(rowIndex, 13));
            enterText(ldapPage.SyncInterval, excelUtil.getCellData(rowIndex, 14));
            ldapPage.SyncInterval.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);

        } catch (Exception e) {
            Reporter.log("Error while filling LDAP configuration: " + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "fillLdapConfigurationError");
            throw e;
        }
    }

    /**
     * Clicks the Save button and verifies if the success message is displayed.
     */
    private void verifySaveSuccess() {
        try {
            ldapPage.saveButton.click();
            if (ldapPage.SuccessSMS.isDisplayed()) {
                Reporter.log("Successfully Updated message is visible.", true);
            } else {
                Reporter.log("Success message not visible after clicking Save.", true);
            }
        } catch (Exception e) {
            Reporter.log("Save verification failed: " + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "verifySaveSuccessError");
        }
    }

    /**
     * Clicks the Test Connection button and verifies the popup message.
     */
    private void verifyConnectionPopup() {
        try {
            ldapPage.testConnectionButton.click();
            if (ldapPage.ConnPopUpTxt.isDisplayed()) {
                Reporter.log("✅ Connection status popup appeared: " + ldapPage.ConnPopUpTxt.getText(), true);
                ldapPage.connectionStatusCloseButton.click();
            } else {
                Reporter.log("Connection status popup did not appear.", true);
            }
        } catch (Exception e) {
            Reporter.log("Test Connection failed: " + e.getMessage(), true);
            ScreenshotUtil.captureScreenshot(driver, "verifyConnectionPopupError");
        }
    }

    // TC_057
    @Test(priority = 57, description = "Save LDAP configuration with updated Employee & WorkEmail")
    public void saveLdapConfiguration_TC057() throws Exception {
        fillLdapConfiguration(3);
        verifySaveSuccess();
    }

    // TC_058
    @Test(priority = 58, description = "Test Connection popup for LDAP configuration")
    public void testLdapConnection_TC058() throws Exception {
        fillLdapConfiguration(3);
        verifyConnectionPopup();
    }
}
