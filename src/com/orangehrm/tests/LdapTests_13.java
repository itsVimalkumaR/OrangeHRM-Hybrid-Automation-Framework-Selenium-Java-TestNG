package com.orangehrm.tests;

import java.awt.event.KeyEvent;
import org.openqa.selenium.Keys;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utilities.ScreenshotUtil;

public class LdapTests_13 extends BaseTest{

	 // ---------------- Helper Methods ----------------

    private void enterText(org.openqa.selenium.WebElement element, String text) {
        element.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        element.sendKeys(text);
    }

    private void selectDropdown(int downPressCount) throws Exception {
        for (int i = 0; i < downPressCount; i++) {
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    private void fillLdapFields(int rowIndex) throws Exception {
        enterText(ldapPage.HostTxtFields, excelUtil.getCellData(rowIndex, 0));
        enterText(ldapPage.portTxtFields, excelUtil.getCellData(1, 1));

        ldapPage.encryptionDropdown.click();
        selectDropdown(2);

        ldapPage.LdapImplemenDD.click();
        selectDropdown(2);

        enterText(ldapPage.DistingNameTxtFields, excelUtil.getCellData(rowIndex, 2));
        enterText(ldapPage.PasswordTxtFields, excelUtil.getCellData(rowIndex, 3));
        enterText(ldapPage.BdNameTxtField, excelUtil.getCellData(rowIndex, 4));

        ldapPage.SearchScopeDD.click();
        selectDropdown(1);

        enterText(ldapPage.UNAttribute, excelUtil.getCellData(rowIndex, 5));
        enterText(ldapPage.USFilter, excelUtil.getCellData(2, 7));
        enterText(ldapPage.UUIDAttribute1, excelUtil.getCellData(rowIndex, 6));
        ldapPage.UUIDAttribute1.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);

        enterText(ldapPage.FNameTxtFields, excelUtil.getCellData(2, 8));
        enterText(ldapPage.LNameTxtFields, excelUtil.getCellData(2, 10));
        enterText(ldapPage.UserStatus1, excelUtil.getCellData(rowIndex, 11));
        enterText(ldapPage.WorkEmail1, excelUtil.getCellData(rowIndex, 12));
        enterText(ldapPage.EmpID1, excelUtil.getCellData(rowIndex, 13));
        enterText(ldapPage.SyncInterval, excelUtil.getCellData(2, 14));
        ldapPage.SyncInterval.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE);
    }

    private void verifySave() {
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
            Reporter.log("Test Connection button is not working.", true);
        }
    }

    // ---------------- Test Cases ----------------

    // TC_060: Save LDAP config with updated Employee & WorkEmail
    @Test
    public void saveLdapConfig_TC060() throws Exception {
        fillLdapFields(3);
        verifySave();
    }

    // TC_061: Test Connection popup for LDAP config
    @Test
    public void testLdapConnection_TC061() throws Exception {
        fillLdapFields(3);
        verifyConnectionPopup();
    }
}
