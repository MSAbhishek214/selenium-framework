package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	// Class level driver
	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	// Constructor to initialize driver
	public LoginPage(WebDriver driver) {
		this.setDriver(driver);
		PageFactory.initElements(driver, this);
	}

	// Find email xpath for landing page
	@FindBy(id = "userEmail")
	WebElement userEmail;

	// Find password field for landing page
	@FindBy(id = "userPassword")
	WebElement userPassword;

	// Find submit button for landing page
	@FindBy(id = "login")
	WebElement submitButton;

	@FindBy(css = ".forgot-password-link")
	WebElement forgotPasswordLink;

	@FindBy(css = "p.login-wrapper-footer-text")
	WebElement registerLink;

	@FindBy(css = "div[aria-label='Password Changed Successfully']")
	WebElement passwordChangeSuccessfulMessage;

	/*
	 * All methods defining individual actions go here Keep actions small and
	 * reusable and return state of element instead of nothing to make them useful
	 */

	// Send keys to username field
	public void enterUserEmail(String userEmail) {
		this.userEmail.sendKeys(userEmail);
	}

	// Send keys to password field
	public void enterUserPassword(String userPassword) {
		this.userPassword.sendKeys(userPassword);
	}

	public void clickSubmitButton() {
		this.submitButton.click();
	}

	// Navigate to register user link
	public void navigateToRegisterLink() {
		this.registerLink.click();
	}

	// Navigate to forgot password link
	public void navigateToForgotPasswordLink() {
		this.forgotPasswordLink.click();
	}

	public String getPasswordChangeSuccessfulText() {
		return passwordChangeSuccessfulMessage.getText().trim();
	}
}
