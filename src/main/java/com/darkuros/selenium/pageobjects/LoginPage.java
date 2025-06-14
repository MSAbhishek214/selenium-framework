package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	// Class level driver
	WebDriver driver;

	// Constructor to initialize driver
	public LoginPage(WebDriver driver) {
		this.driver = driver;
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

	// Login to product catalogue page
	public void loginToApplication(String username, String password) {
		this.userEmail.sendKeys(username);
		this.userPassword.sendKeys(password);
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
}
