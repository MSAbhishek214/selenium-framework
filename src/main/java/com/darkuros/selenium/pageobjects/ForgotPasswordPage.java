package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;

import com.darkuros.selenium.utils.IReporter;
import com.darkuros.selenium.utils.LoggerFactoryUtils;

/**
 * ForgotPasswordPage class represents the Forgot Password page in the
 * application. It contains methods to interact with the elements on the page
 * and perform actions such as entering email, password, and confirming
 * password, as well as navigating to other pages like Login and Register.
 */
public class ForgotPasswordPage extends BasePage {
	private static final Logger logger = LoggerFactoryUtils.getLogger(ForgotPasswordPage.class);

	/**
	 * Constructor for ForgotPasswordPage.
	 * 
	 * @param driver WebDriver instance to interact with the browser.
	 * @param explicitWaitInSeconds Time in seconds for explicit waits.
	 */
	public ForgotPasswordPage(WebDriver driver, long explicitWaitInSeconds, IReporter reporter) {
		super(driver, explicitWaitInSeconds, reporter); // This initializes 'this.driver' and 'this.wait' in BasePage
	}

	@FindBy(css = ".mt-1 + input")
	private WebElement emailInput;

	@FindBy(id = "userPassword")
	private WebElement passwordInput;

	@FindBy(id = "confirmPassword")
	private WebElement confirmPasswordInput;

	@FindBy(css = "button[type='submit']")
	private WebElement submitButton;

	@FindBy(xpath = "//a[normalize-space()='Login']")
	private WebElement loginLink;

	@FindBy(xpath = "//a[normalize-space()='Register']")
	private WebElement registerLink;

	@FindBy(css = "input[formcontrolname='userEmail'] + div > div")
	private WebElement emailValidationError;

	@FindBy(css = "#userPassword + div.invalid-feedback")
	private WebElement passwordError;

	@FindBy(css = "#confirmPassword+ div.invalid-feedback")
	private WebElement confirmPasswordError;

	/*
	 * All methods defining individual actions go here Keep actions small and
	 * reusable and return state of element instead of nothing to make them useful
	 */

	public String getPasswordErrorText() {
		String passwordErrorText = wait.until(ExpectedConditions.visibilityOf(passwordError)).getText().trim();
		logger.info("Fetched password error text: {}", passwordErrorText);
		return passwordErrorText;
	}

	public String getConfirmPasswordErrorText() {
		String confirmPasswordErrorText = wait.until(ExpectedConditions.visibilityOf(confirmPasswordError)).getText()
				.trim();
		logger.info("Fetched confirm password error text: {}", confirmPasswordErrorText);
		return confirmPasswordErrorText;
	}

	public String getEmptyEmailErrorText() {
		String emptyEmailErrorText = wait.until(ExpectedConditions.visibilityOf(emailValidationError)).getText().trim();
		logger.info("Fetch empty email error text: {}", emptyEmailErrorText);
		return emptyEmailErrorText;
	}

	public String getInvalidEmailErrorText() {
		String InvalidEmailErrorText = wait.until(ExpectedConditions.visibilityOf(emailValidationError)).getText().trim();
		logger.info("Fetched invalid email error text: {}", InvalidEmailErrorText);
		return InvalidEmailErrorText;
	}

	public void enterUserEmail(String userEmail) {
		logger.info("Entering user email: {}", userEmail);
		wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(userEmail);
	}

	public void enterPassword(String password) {
		logger.info("Entering password: {}", password);
		wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
	}

	public void enterConfirmPassword(String confirmPassword) {
		logger.info("Entering confirm password: {}", confirmPassword);
		wait.until(ExpectedConditions.visibilityOf(confirmPasswordInput)).sendKeys(confirmPassword);
	}

	public void clickOnSubmitButton() {
		logger.info("Clicking on submit button");
		wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
	}

	// Method to navigate back to Login page from forgot password page
	public LoginPage navigateBackToLoginPage() {
		logger.info("Navigating back to Login page from Forgot Password page");
		wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
		return new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
	}

	// Method to navigate back to register page from forgot password page
	public RegisterPage navigateBackToRegisterPage() {
		logger.info("Navigating back to Register page from Forgot Password page");
		wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
		return new RegisterPage(getDriver(), explicitWaitInSeconds, reporter);
	}

	public LoginPage fillSavePasswordFormAndSubmit(String userEmail, String password, String confirmPassword) {
		logger.info("Filling save password form with user email: {}, password: {}, confirm password: {}", userEmail,
				password, confirmPassword);
		enterUserEmail(userEmail);
		enterPassword(password);
		enterConfirmPassword(confirmPassword);
		clickOnSubmitButton();
		logger.info("Filled save password form and submitted");
		return new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
	}

	public String getForgotPasswordPageURL() {
		logger.info("Fetching current URL of Forgot Password page");
		return getCurrentPageURL();
	}
}
