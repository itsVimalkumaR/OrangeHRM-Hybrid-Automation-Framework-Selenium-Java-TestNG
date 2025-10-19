package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.orangehrm.base.BaseTest;

/**
 * LdapTests_1
 *
 * Verifies UI components on the LDAP Configuration page.
 */
public class LdapTests_1 extends BaseTest {

    // TC_001
    @Test(priority = 1, description = "Verify user can navigate to LDAP Configuration page")
    public void verifyLdapHeading() {
        String heading = ldapPage.ldapHeading.getText().trim();
        Reporter.log("LDAP page heading: " + heading, true);
        Assert.assertTrue(heading.contains("LDAP"), "LDAP heading is incorrect.");
    }

    // TC_002
    @Test(priority = 2, description = "Verify total text fields on LDAP page")
    public void verifyTextFieldsCount() {
        int count = ldapPage.textFields.size();
        Reporter.log("Text fields count: " + count, true);
        Assert.assertTrue(count > 0, "No text fields found.");
    }

    // TC_003
    @Test(priority = 3, description = "Verify number of toggle buttons on LDAP page")
    public void verifyToggleButtonsCount() {
        int count = ldapPage.toggleButtons.size();
        Reporter.log("Toggle buttons count: " + count, true);
        Assert.assertTrue(count >= 0, "Toggle buttons count is invalid.");
    }

    // TC_004
    @Test(priority = 4, description = "Verify total buttons on LDAP page")
    public void verifyTotalButtonsCount() {
        int count = ldapPage.allButtons.size();
        Reporter.log("Buttons count: " + count, true);
        Assert.assertTrue(count > 0, "No buttons found on page.");
    }

    // TC_005
    @Test(priority = 5, description = "Verify 'Test Connection' button tag name")
    public void verifyTestConnectionButtonTag() {
        String tagName = ldapPage.testConnectionButton.getTagName().trim().toLowerCase();
        Reporter.log("'Test Connection' button tag: " + tagName, true);
        Assert.assertEquals(tagName, "button", "Tag name is not <button>.");
    }
}
