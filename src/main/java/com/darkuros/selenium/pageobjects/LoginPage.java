package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;

import com.darkuros.selenium.utils.IReporter;
import com.darkuros.selenium.utils.InteractionUtils;
import com.darkuros.selenium.utils.LoggerFactoryUtils;

/**
 * LoginPage class represents the login page of the application. It contains
 * methods to interact with the login form elements. This class extends BasePage
 * to inherit common functionalities.
 */
public class LoginPage extends BasePage {
	private static final Logger logger = LoggerFactoryUtils.getLogger(LoginPage.class);

	// Constructor to initialize driver
	public LoginPage(WebDriver driver, long explicitWaitInSeconds, IReporter reporter) {
		super(driver, explicitWaitInSeconds, reporter); // This initializes 'this.driver' and 'this.wait' in BasePage
	}

	// Find email xpath for landing page
	@FindBy(id = "userEmail")
	private WebElement emailInput;

	// Find password field for landing page
	@FindBy(id = "userPassword")
	private WebElement passwordInput;

	// Find submit button for landing page
	@FindBy(id = "login")
	private WebElement submitButton;

	@FindBy(css = ".forgot-password-link")
	private WebElement forgotPasswordLink;

	@FindBy(css = "p.login-wrapper-footer-text")
	private WebElement registerLink;

	@FindBy(css = "div[aria-label='Password Changed Successfully']")
	private WebElement passwordChangedToast;

	// initialized to wait for the overlay to be present
	private final By overlayContainer = By.className("overlay-container");

	// initialized to wait for the toast container to be present
	private final By toastContainer = By.id("toast-container");

	/*
	 * All methods defining individual actions go here Keep actions small and
	 * reusable and return state of element instead of nothing to make them useful
	 */

	// Send keys to username field
	public void enterUserEmail(String email) {
		logger.info("Entering email: {}", email);
		wait.until(ExpectedConditions.elementToBeClickable(emailInput)).sendKeys(email);
	}

	// Send keys to password field
	public void enterUserPassword(String password) {
		logger.info("Entering password: {}", password);
		wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(password);
	}

	public void clickSubmitButton() {
		InteractionUtils.safeClick(getDriver(), submitButton);
		logger.info("Submit button clicked, waiting for login to process");
	}

	// Navigate to register user link
	public RegisterPage navigateToRegisterLink() {
		reporter.logInfo("Navigating to Register page");
		InteractionUtils.safeClick(getDriver(), registerLink);
		logger.info("Register link clicked, navigating to RegisterPage");
		return new RegisterPage(getDriver(), explicitWaitInSeconds, reporter);
	}

	// Navigate to forgot password link
	public ForgotPasswordPage navigateToForgotPasswordLink() {
		reporter.logInfo("Navigating to Forgot Password page");
		InteractionUtils.safeClick(getDriver(), forgotPasswordLink);
		logger.info("Forgot password link clicked, navigating to ForgotPasswordPage");
		return new ForgotPasswordPage(getDriver(), explicitWaitInSeconds, reporter);
	}

	// Get the text of the password change successful message
	public String getPasswordChangeSuccessfulText() {
		String passwordChangedText = wait.until(ExpectedConditions.visibilityOf(passwordChangedToast)).getText().trim();
		logger.info("Fetched password change successful text: {}", passwordChangedText);
		return passwordChangedText;
	}

	/**
	 * Gets the text from the toast container, which is used to display messages
	 * like password change success. Using explicit wait to ensure the toast is
	 * visible before fetching text, instead of just waiting for presence. This was
	 * causing issues in headless mode where the element was present but not
	 * visible.
	 * 
	 * @return The text from the toast container.
	 */
	public String getToastContainerText() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(overlayContainer));
		String toastText = wait.until(ExpectedConditions.visibilityOfElementLocated(toastContainer)).getText().trim();
		logger.info("Fetched toast container text: {}", toastText);
		return toastText;
	}

	/**
	 * Performs a complete login action and returns the landingPage object if
	 * successful.
	 * 
	 * @param email    The user's email.
	 * @param password The user's password.
	 */
	public ProductCataloguePage loginApplication(String email, String password) {
		logger.info("Logging in with email: {}", email);
		enterUserEmail(email);
		reporter.logInfo("Email entered: " + email);
		enterUserPassword(password);
		reporter.logInfo("Password entered: " + password);
		clickSubmitButton();
		reporter.logInfo("Login form submitted");
		logger.info("Login submitted, waiting for landing page to load");
		return new ProductCataloguePage(getDriver(), explicitWaitInSeconds, reporter);
	}

	/**
	 * Gets the current URL of the login page.
	 * 
	 * @return The URL of the login page.
	 */
	public String getLoginPageURL() {
		wait.until(ExpectedConditions.urlContains("auth/login"));
		logger.info("Login URL: {}", getDriver().getCurrentUrl());
		return getDriver().getCurrentUrl();
	}
}
