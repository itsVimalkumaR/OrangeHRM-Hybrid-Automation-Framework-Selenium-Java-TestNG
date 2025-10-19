package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.Reporter;
import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.LdapConfigPage;
import com.orangehrm.pages.LoginPage;

public class LoginTests extends BaseTest{

//	@Test(enabled = false, description = "Verify user can login to OrangeHRM", priority = 0)
	public void testLogin() {
		// Login happens in @BeforeMethod from BaseTest
		LoginPage lp = new LoginPage(driver);
		boolean loggedIn = lp.isLoggedIn();
		Assert.assertTrue(loggedIn, "Expected user to be logged in after login attempt");
	}
	
//	@Test(enabled = false, priority = 0, description = "Login and navigate to LDAP Configuration page")
	public void testLoginAndNavigateToLDAP() {
		// Navigate to Admin -> configuration -> LDAP Configuration
		testLogin();
		AdminPage adminPage = new AdminPage(driver);
		adminPage.navigateToLdapConfig();
		
		// Verify LDAP page
		LdapConfigPage ldapPage = new LdapConfigPage(driver);
		assert ldapPage.isOnLdapPage() : "LDAP Configuration page is not displayed!";
		Reporter.log("User successfully navigated to LDAP Configuration page", true);
	}
}
	