package com.orangehrm.tests;

import java.awt.event.KeyEvent;
import org.openqa.selenium.Keys;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

public class LdapTests_11 extends BaseTest{

	// ---------------- Helper Methods ----------------

    // Clear a field and enter text
    private void enterText(org.openqa.selenium.WebElement element, String text) {
        element.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        element.sendKeys(text);
    }

    // Select dropdown option using Robot key down
    @SuppressWarnings("unused")
	private void selectDropdownOption(int downPressCount) throws Exception {
        for (int i = 0; i < downPressCount; i++) {
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    // Fill all LDAP fields from Excel
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
        ldapPage.SyncInterval.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);
    }

    // Verify connection popup
    private void verifyConnectionPopup() {
        ldapPage.testConnectionButton.click();
        try {
            if (ldapPage.ConnPopUpTxt.isDisplayed()) {
                Reporter.log("Connection status popup appeared: " + ldapPage.ConnPopUpTxt.getText(), true);
                ldapPage.connectionStatusCloseButton.click();
            } else {
                Reporter.log("Connection status popup did not appear.", true);
            }
        } catch (Exception e) {
            Reporter.log("Connection status popup not found.", true);
        }
    }

    // Verify save success
    private void verifySaveSuccess() {
        try {
            ldapPage.saveButton.click();
            if (ldapPage.SuccessSMS.isDisplayed()) {
                Reporter.log("Successfully Updated message is visible.", true);
            }
        } catch (Exception e) {
        	ScreenshotUtil.captureScreenshot(driver, "verifySaveSuccessFailed");
            Reporter.log("Successfully Updated message is NOT visible.", true);
        }
    }

    // ---------------- Test Cases ----------------

    // TC_055: Test Connection popup with Work Email toggle
    @Test
    public void testConnectionWithWorkEmail() throws Exception {
        fillLdapConfiguration();
        ldapPage.workMailToggleBtn.click();
        verifyConnectionPopup();
    }

    // TC_056: Save LDAP configuration with Employee ID toggle
    @Test
    public void saveLdapConfigurationWithEmpId() throws Exception {
        fillLdapConfiguration();
        ldapPage.empIdToggleBtn.click();
        verifySaveSuccess();
    }
}
