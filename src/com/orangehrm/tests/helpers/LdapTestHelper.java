package com.orangehrm.tests.helpers;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.LdapConfigPage;
import com.orangehrm.utilities.ScreenshotUtil;

public class LdapTestHelper extends BaseTest{

	private WebDriver driver;
    private WebDriverWait wait;
    private Robot robot;
    protected LdapConfigPage ldapPage;


    // Primary constructor - ensures driver and wait are set
    public LdapTestHelper(WebDriver driver, Robot robot, LdapConfigPage ldapPage, int timeoutSeconds) {
        this.driver = driver;
        this.robot = robot;
        this.ldapPage = ldapPage;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public LdapTestHelper(WebDriver driver, WebDriverWait wait, Robot robot, LdapConfigPage ldapPage) {
        this.driver = driver;
        this.wait = (wait != null) ? wait : new WebDriverWait(driver, Duration.ofSeconds(30));
        this.robot = robot;
        this.ldapPage = ldapPage;
    }
    
    /** Clear field and enter text */
    public void updateField(WebElement field, String value) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = '';", field);
            if (value != null && !value.trim().isEmpty()) {
                js.executeScript("arguments[0].value = arguments[1];", field, value);
            }
            js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", field);
            Thread.sleep(300);
        } catch (Exception e) {
            try {
                field.sendKeys(Keys.CONTROL + "a");
                field.sendKeys(Keys.DELETE);
                if (value != null && !value.trim().isEmpty()) {
                    field.sendKeys(value);
                }
            } catch (Exception ex) {
                Reporter.log("Failed to update field: " + ex.getMessage(), true);
            }
        }
    }

    /** Select dropdown using Robot */
    public void selectDropdownOption(WebElement dropdown, int downPressCount) {
        try {
            dropdown.click();
            Thread.sleep(500);
            for (int i = 0; i < downPressCount; i++) {
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                Thread.sleep(200);
            }
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(500);
        } catch (Exception e) {
            Reporter.log("Dropdown selection failed: " + e.getMessage(), true);
        }
    }

    /** Click save and verify success */
    public void verifySave() {
        try {
            ldapPage.saveButton.click();
            wait.until(ExpectedConditions.visibilityOf(ldapPage.SuccessSMS));
            Reporter.log("✓ Successfully Updated message is visible: " + ldapPage.SuccessSMS.getText(), true);
        } catch (Exception e) {
            ScreenshotUtil.captureScreenshot(driver, "verifySaveFailed");
            Reporter.log("✗ Success message not displayed.", true);
            throw new RuntimeException("Save verification failed", e);
        }
    }

    /** Click Test Connection and verify popup */
    public void verifyTestConnection() {
        try {
            ldapPage.testConnectionButton.click();
            wait.until(ExpectedConditions.visibilityOf(ldapPage.ConnPopUpTxt));
            Reporter.log("✓ Connection popup displayed: " + ldapPage.ConnPopUpTxt.getText(), true);
            ldapPage.connectionStatusCloseButton.click();
        } catch (Exception e) {
            ScreenshotUtil.captureScreenshot(driver, "verifyTestConnectionFailed");
            Reporter.log("✗ Connection popup did not appear", true);
            throw new RuntimeException("Test Connection verification failed", e);
        }
    }

    /** Fill LDAP fields from given values */
    public void fillLdapFields(String host, String port, String distinguishedName, String password,
                               String baseDN, String unAttr, String uuidAttr, String searchFilter,
                               String fName, String midName, String lName, String status,
                               String email, String empId, String syncInterval) {
        updateField(ldapPage.HostTxtFields, host);
        updateField(ldapPage.portTxtFields, port);
        updateField(ldapPage.DistingNameTxtFields, distinguishedName);
        updateField(ldapPage.PasswordTxtFields, password);
        updateField(ldapPage.BdNameTxtField, baseDN);
        updateField(ldapPage.UNAttribute, unAttr);
        updateField(ldapPage.UUIDAttribute1, uuidAttr);
        updateField(ldapPage.USearchFilter, searchFilter);
        updateField(ldapPage.FNameTxtFields, fName);
        updateField(ldapPage.midName1, midName);
        updateField(ldapPage.LNameTxtFields, lName);
        updateField(ldapPage.UserStatus1, status);
        updateField(ldapPage.WorkEmail1, email);
        updateField(ldapPage.EmpID1, empId);
        updateField(ldapPage.SyncInterval, syncInterval);
    }
	
	/** Get cell data with fallback values when excelUtil is null */
	public String getCellData(int row, int col, String fallbackValue) {
		if (excelUtil != null) {
			String data = excelUtil.getCellData(row, col);
			return data != null && !data.trim().isEmpty() ? data : fallbackValue;
		}
		return fallbackValue;
	}

	/** Selects a dropdown option using JavaScript for reliability */
	public void selectDropdownOption(WebElement dropdown, String dropdownName) {
		try {
			// Use JavaScript to click the dropdown
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", dropdown);

			Thread.sleep(1000); // Wait for dropdown to open

			// Use Robot for selection
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			Thread.sleep(500);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(1000); // Wait for selection to apply

		} catch (Exception e) {
			Reporter.log("Error selecting '"+ dropdownName +"' dropdown option: " + e.getMessage(), true);
		}
	}

	/** Check for success message with multiple strategies */
	private boolean checkForSuccessMessage() {
		try {
			// Wait for the success message with longer timeout
			WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));

			// Try multiple possible success message locators
			try {
				// Strategy 1: Toast message
				WebElement successToast = longWait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//div[contains(@class, 'oxd-toast')]//p[contains(@class, 'oxd-text')]")));
				if (successToast.isDisplayed()) {
					Reporter.log("Success toast message found: " + successToast.getText(), true);
					return true;
				}
			} catch (Exception e) {
				// Continue to next strategy
			}

			// Strategy 2: Success notification
			try {
				WebElement successNotification = longWait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'oxd-notification')]//p")));
				if (successNotification.isDisplayed()) {
					Reporter.log("Success notification found: " + successNotification.getText(), true);
					return true;
				}
			} catch (Exception e) {
				// Continue to next strategy
			}

			// Strategy 3: Any success message
			try {
				WebElement anySuccess = longWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
						"//*[contains(text(), 'Success') or contains(text(), 'success') or contains(text(), 'Updated')]")));
				if (anySuccess.isDisplayed()) {
					Reporter.log("Success message found: " + anySuccess.getText(), true);
					return true;
				}
			} catch (Exception e) {
				// All strategies failed
			}

			return false;

		} catch (Exception e) {
			Reporter.log("Error checking for success message: " + e.getMessage(), true);
			return false;
		}
	}

	
	
	// ------------------ Additional Helper Methods ------------------

		/** Check if field is interactable */
		public boolean isFieldInteractable(WebElement field) {
		    try {
		        return field.isEnabled() && field.isDisplayed();
		    } catch (Exception e) {
		        return false;
		    }
		}

		/** Click with verification and retry */
	    public void clickWithVerification(WebElement element, String elementName) {
	        try {
	            // Ensure driver is not null before casting
	            if (driver == null) {
	                throw new IllegalStateException("WebDriver is null in LdapTestHelper");
	            }

	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("arguments[0].click();", element);
	            Thread.sleep(1000);

	            // Verify click was registered
	            if (elementName.equalsIgnoreCase("Save") && !checkForSuccessMessage()) {
	                Thread.sleep(2000);
	            }

	        } catch (Exception e) {
	            Reporter.log("JavaScript click failed for " + elementName + ", trying regular click: " + e.getMessage(), true);
	            try {
	                element.click();
	            } catch (Exception ex) {
	                Reporter.log("Regular click also failed for " + elementName + ": " + ex.getMessage(), true);
	                ScreenshotUtil.captureScreenshot(driver, "clickFailed_" + elementName.replaceAll("\\s+", "_"));
	                throw new RuntimeException("Failed to click " + elementName, ex);
	            }
	        }
	    }

	    // Optional safety check for debugging
	    public void verifyDriverHealth() {
	        if (driver == null) {
	            Reporter.log("❌ ERROR: WebDriver is NULL inside LdapTestHelper!", true);
	            throw new IllegalStateException("Driver not initialized in LdapTestHelper");
	        } else {
	            Reporter.log("✅ WebDriver is initialized properly.", true);
	        }
	    }

		/** Check for detailed error messages */
		public void checkForDetailedError() {
		    try {
		        List<By> errorLocators = Arrays.asList(
		            By.xpath("//*[contains(@class, 'error')]"),
		            By.xpath("//*[contains(@class, 'invalid')]"),
		            By.xpath("//*[contains(text(), 'Error')]"),
		            By.xpath("//*[contains(text(), 'Failed')]"),
		            By.xpath("//*[contains(text(), 'Invalid')]"),
		            By.xpath("//*[contains(@class, 'oxd-input-error')]")
		        );
		        
		        for (By locator : errorLocators) {
		            try {
		                WebElement error = driver.findElement(locator);
		                if (error.isDisplayed()) {
		                    Reporter.log("Error detected: " + error.getText(), true);
		                }
		            } catch (Exception e) {
		                // Continue
		            }
		        }
		    } catch (Exception e) {
		        // Ignore
		    }
		}

		/** Check for connection-specific errors */
		public boolean checkForConnectionError() {
		    try {
		        List<By> connectionErrorLocators = Arrays.asList(
		            By.xpath("//*[contains(text(), 'Connection failed')]"),
		            By.xpath("//*[contains(text(), 'Unable to connect')]"),
		            By.xpath("//*[contains(text(), 'Authentication failed')]"),
		            By.xpath("//*[contains(text(), 'LDAP error')]")
		        );
		        
		        for (By locator : connectionErrorLocators) {
		            try {
		                WebElement error = driver.findElement(locator);
		                if (error.isDisplayed()) {
		                    Reporter.log("Connection error: " + error.getText(), true);
		                    return true;
		                }
		            } catch (Exception e) {
		                // Continue
		            }
		        }
		        return false;
		    } catch (Exception e) {
		        return false;
		    }
		}
}
