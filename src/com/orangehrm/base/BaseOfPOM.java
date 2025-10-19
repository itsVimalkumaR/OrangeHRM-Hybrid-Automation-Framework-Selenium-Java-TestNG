package com.orangehrm.base;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BaseOfPOM {

	protected WebDriver driver;

	public BaseOfPOM(WebDriver driver) {
		this.driver = driver;
	}

	// -------------------- LOGIN PAGE --------------------
	@FindBy(name = "username")
	public WebElement usernameInputField;

	@FindBy(name = "password")
	public WebElement passwordInputField;
	
	@FindBy(xpath = "//button[.=' Login ']")
	public WebElement loginButton;

	// -------------------- HOME PAGE --------------------
	@FindBy(xpath = "//p[@class='oxd-userdropdown-name']")
	public WebElement profileMenu;
	
	@FindBy(xpath = "//a[.='Logout']")
	public WebElement logoutLink;

	// -------------------- ADMIN PAGE --------------------
	@FindBy(xpath = "(//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name'])[1]")
	public WebElement adminLink;
	
	@FindBy(xpath = "//span[text()='Configuration ']")
	public WebElement configurationLink;
	
	@FindBy(xpath = "//a[text()='LDAP Configuration']")
	public WebElement ldapConfigLink;

	// -------------------- LDAP CONFIGURATION --------------------
	@FindBy(xpath = "//h6[text()='LDAP Configuration']")
	public WebElement ldapHeading;

	@FindBy(xpath = "//input[@class='oxd-input oxd-input--active']")
	public List<WebElement> textFields;
	
	@FindBy(xpath = "//input[@type='checkbox']")
	public List<WebElement> toggleButtons;
	
	@FindBy(xpath = "//button")
	public List<WebElement> allButtons;
	
	@FindBy(xpath = "//button[contains(.,'Test Connection')]")
	public WebElement testConnectionButton;

	@FindBy(xpath = "//button[contains(.,'Save')]")
	public WebElement saveButton;

	@FindBy(xpath = "//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")
	public WebElement connectionMessage;

	// -------------------- COMMON USED ELEMENTS --------------------

	@FindBy(xpath = "//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']")
	public List<WebElement> dropdownBtn;

	@FindBy(xpath = "(//span[@class='oxd-switch-input oxd-switch-input--active --label-right'])[1]")
	public WebElement bindButton;

	@FindBy(xpath = "//span[@class='oxd-switch-input oxd-switch-input--active --label-left']")
	public WebElement ldapToggleBtn;

	@FindBy(xpath = "//span[@class='oxd-switch-input oxd-switch-input--active --label-right']")
	public List<WebElement> allTogglesBtn;

	@FindBy(xpath = "(//span[@class='oxd-switch-input oxd-switch-input--active --label-right'])[2]")
	public WebElement workMailToggleBtn;

	@FindBy(xpath = "(//span[@class='oxd-switch-input oxd-switch-input--active --label-right'])[3]")
	public WebElement empIdToggleBtn;

	@FindBy(xpath = "(//span[contains(@class,'active --label-right')])[4]")
	public WebElement addSettingsBtn;

	@FindBy(xpath = "//button[contains(@class,'oxd-dialog-close-button')]")
	public WebElement connectionStatusCloseButton;

	// -------------------- AFTER CLICK BIND TOGGLE BUTTON --------------------
	@FindBy(xpath = "(//input)[6]")
	public WebElement BdNameTxtField1;

	@FindBy(xpath = "(//input[@class='oxd-input oxd-input--active'])[5]")
	public WebElement UserNameAttribue;

	@FindBy(xpath = "(//input)[8]")
	public WebElement USearchFilter;

	@FindBy(xpath = "(//input)[9]")
	public WebElement UUIDAttribute;

	@FindBy(xpath = "(//input)[11]	")
	public WebElement midName;

	@FindBy(xpath = "(//input)[13]")
	public WebElement UserStatus;

	@FindBy(xpath = "(//input)[14]")
	public WebElement WorkEmail;

	@FindBy(xpath = "(//input)[16]")
	public WebElement EmpID;

	// -------------------- BEFORE CLICK BIND TOGGLE BUTTON --------------------
	@FindBy(xpath = "(//input)[3]")
	public WebElement HostTxtFields;

	@FindBy(xpath = "(//input)[4]")
	public WebElement portTxtFields;

	@FindBy(xpath = "(//input)[6]")
	public WebElement DistingNameTxtFields;

	@FindBy(xpath = "(//input)[7]")
	public WebElement PasswordTxtFields;

	@FindBy(xpath = "(//input)[8]")
	public WebElement BdNameTxtField;

	@FindBy(xpath = "(//input)[9]")
	public WebElement UNAttribute;

	@FindBy(xpath = "(//input)[10]")
	public WebElement USFilter;

	@FindBy(xpath = "(//input)[11]")
	public WebElement UUIDAttribute1;

	@FindBy(xpath = "(//input)[12]")
	public WebElement FNameTxtFields;

	@FindBy(xpath = "(//input)[13]")
	public WebElement midName1;

	@FindBy(xpath = "(//input)[14]")
	public WebElement LNameTxtFields;

	@FindBy(xpath = "(//input)[15]")
	public WebElement UserStatus1;

	@FindBy(xpath = "(//input)[16]")
	public WebElement WorkEmail1;

	@FindBy(xpath = "(//input)[18]")
	public WebElement EmpID1;

	@FindBy(xpath = "(//input)[21]")
	public WebElement SyncInterval;

	// -------------------- DROPDOWN --------------------
	@FindBy(xpath = "(//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'])[1]")
	public WebElement encryptionDropdown;

	@FindBy(xpath = "//div[text()='TLS']")
	public WebElement TLSOpt;

	@FindBy(xpath = "(//div[.='SSL'])[4]")
	public WebElement SSLOpt;

	@FindBy(xpath = "(//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'])[2]")
	public WebElement LdapImplemenDD;

	@FindBy(xpath = "(//div[.='Open LDAP v3'])[4]")
	public WebElement OpenLdapOpt;

	@FindBy(xpath = "(//div[.='MS Active Directory'])[4]")
	public WebElement MSADirectoryOpt;

	@FindBy(xpath = "(//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'])[3]")
	public WebElement SearchScopeDD;

	@FindBy(xpath = "(//div[.='Subtree'])[4]")
	public WebElement SubtreeOpt;

	@FindBy(xpath = "(//div[.='One level'])[4]")
	public WebElement OneLevelOpt;

	/******************************************************************************************************/

	@FindBy(xpath = "//p[contains(@class,'oxd-input-field-required')]")
	public List<WebElement> ReqOpt;

	@FindBy(xpath = "//label[contains(@class,'oxd-input-field-required')]")
	public List<WebElement> ReqoOpt1;

	@FindBy(xpath = "//p[contains(@class,'orangehrm-subtitle')]")
	public List<WebElement> SubTitles;

	@FindBy(xpath = "//h6[contains(@class,'orangehrm-main-title')]")
	public WebElement MainTitle;

	@FindBy(xpath = "//h6[.='Admin']")
	public WebElement adminHeading;

	@FindBy(xpath = "//h6[.='Configuration']")
	public WebElement configHeading;

	@FindBy(xpath = "//a[.='OrangeHRM, Inc']")
	public WebElement OrgLink;

	@FindBy(xpath = "//a[.='OrangeHRM, Inc']")
	public WebElement HRSoftawre;

	@FindBy(xpath = "//span[text()='Required']")
	public WebElement requiredText;

	@FindBy(xpath = "//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")
	public WebElement disclaimerMessage;

	@FindBy(xpath = "(//span[contains(@class,'oxd-input-group__message')])[4]")
	public WebElement workEmailRequiredText;

	@FindBy(xpath = "(//span[contains(@class,'oxd-input-group__message')])[4]")
	public WebElement empIdRequiredText;

	@FindBy(xpath = "//span[contains(@class,'oxd-input-group__message')]")
	public List<WebElement> AllRequiredTxt;

	@FindBy(xpath = "//span[contains(@class,'oxd-input-group__message')]")
	public List<WebElement> ReqCount;

	@FindBy(xpath = "//img[@alt='client brand banner']")
	public WebElement OrgHrmImage;

	@FindBy(xpath = "//p[@class='oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text']")
	public WebElement SuccessSMS;

	@FindBy(xpath = "//p[.='Connection Status']")
	public WebElement ConnPopUpTxt;

}