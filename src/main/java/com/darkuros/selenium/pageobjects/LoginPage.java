package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.darkuros.selenium.utils.InteractionUtils;

public class LoginPage extends BasePage {

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
		wait.until(ExpectedConditions.elementToBeClickable(emailInput)).sendKeys(email);
	}

	// Send keys to password field
	public void enterUserPassword(String password) {
		wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(password);
	}

	public void clickSubmitButton() {
		InteractionUtils.safeClick(getDriver(), submitButton);
	}

	// Navigate to register user link
	public RegisterPage navigateToRegisterLink() {
		InteractionUtils.safeClick(getDriver(), registerLink);
		return new RegisterPage(getDriver());
	}

	// Navigate to forgot password link
	public ForgotPasswordPage navigateToForgotPasswordLink() {
		InteractionUtils.safeClick(getDriver(), forgotPasswordLink);
		return new ForgotPasswordPage(getDriver());
	}

	// Get the text of the password change successful message
	public String getPasswordChangeSuccessfulText() {
		return wait.until(ExpectedConditions.visibilityOf(passwordChangedToast)).getText().trim();
	}

	public String getToastContainerText() {
		wait.until(ExpectedConditions.presenceOfElementLocated(overlayContainer));
		return wait.until(ExpectedConditions.presenceOfElementLocated(toastContainer)).getText().trim();
	}

	/**
	 * Performs a complete login action and returns the landingPage object if
	 * successful.
	 * 
	 * @param email    The user's email.
	 * @param password The user's password.
	 */
	public LandingPage loginApplication(String email, String password) {
		enterUserEmail(email);
		enterUserPassword(password);
		clickSubmitButton();
		return new LandingPage(getDriver());
	}

	public String getLoginPageURL() {
		wait.until(ExpectedConditions.urlContains("auth/login"));
		return getDriver().getCurrentUrl();
	}
}
