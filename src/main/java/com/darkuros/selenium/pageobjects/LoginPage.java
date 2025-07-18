package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.darkuros.selenium.utils.InteractionUtils;
import com.darkuros.selenium.utils.LoggerFactoryUtils;

import org.slf4j.Logger;

/**
 * LoginPage class represents the login page of the application. It contains
 * methods to interact with the login form elements. This class extends BasePage
 * to inherit common functionalities.
 */
public class LoginPage extends BasePage {
	private static final Logger logger = LoggerFactoryUtils.getLogger(LoginPage.class);

	// Constructor to initialize driver
	public LoginPage(WebDriver driver) {
		super(driver); // This initializes 'this.driver' and 'this.wait' in BasePage
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
		InteractionUtils.safeClick(getDriver(), registerLink);
		logger.info("Register link clicked, navigating to RegisterPage");
		return new RegisterPage(getDriver());
	}

	// Navigate to forgot password link
	public ForgotPasswordPage navigateToForgotPasswordLink() {
		InteractionUtils.safeClick(getDriver(), forgotPasswordLink);
		logger.info("Forgot password link clicked, navigating to ForgotPasswordPage");
		return new ForgotPasswordPage(getDriver());
	}

	// Get the text of the password change successful message
	public String getPasswordChangeSuccessfulText() {
		String passwordChangedText = wait.until(ExpectedConditions.visibilityOf(passwordChangedToast)).getText().trim();
		logger.info("Fetched password change successful text: {}", passwordChangedText);
		return passwordChangedText;
	}

	/**
	 * Gets the text from the toast container, which is used to display messages
	 * like password change success.
	 * 
	 * @return The text from the toast container.
	 */
	public String getToastContainerText() {
		wait.until(ExpectedConditions.presenceOfElementLocated(overlayContainer));
		String toastText = wait.until(ExpectedConditions.presenceOfElementLocated(toastContainer)).getText().trim();
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
	public LandingPage loginApplication(String email, String password) {
		logger.info("Logging in with email: {}", email);
		enterUserEmail(email);
		enterUserPassword(password);
		clickSubmitButton();
		logger.info("Login submitted, waiting for landing page to load");
		return new LandingPage(getDriver());
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
