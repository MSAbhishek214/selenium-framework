package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ForgotPasswordPage {

	// Declare a WebDriver
	private final WebDriver driver;

	public ForgotPasswordPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(css = ".mt-1 + input")
	WebElement emailInput;

	@FindBy(id = "userPassword")
	WebElement passwordInput;

	@FindBy(id = "confirmPassword")
	WebElement confirmPasswordInput;

	@FindBy(css = ".btn")
	WebElement submitButton;

	@FindBy(xpath = "//a[normalize-space()='Login']")
	WebElement loginLink;

	@FindBy(xpath = "//a[normalize-space()='Register']")
	WebElement registerLink;

	/*
	 * All methods defining individual actions go here Keep actions small and
	 * reusable and return state of element instead of nothing to make them useful
	 */

	public void enterUserEmail(String userEmail) {
		emailInput.sendKeys(userEmail);
	}

	public void enterPassword(String password) {
		passwordInput.sendKeys(password);
	}

	public void enterConfirmPassword(String confirmPassword) {
		confirmPasswordInput.sendKeys(confirmPassword);
	}

	public void clickOnSubmitButton() {
		this.submitButton.click();
	}

	// Method to navigate back to Login page from forgot password page
	public LoginPage navigateBackToLoginPage() {
		loginLink.click();
		return new LoginPage(driver);
	}

	// Method to navigate back to register page from forgot password page
	public RegisterPage navigateBackToRegisterPage() {
		registerLink.click();
		return new RegisterPage(driver);
	}

	public void fillSavePasswordFormAndSubmit(String userEmail, String password, String confirmPassword) {
		enterUserEmail(userEmail);
		enterPassword(password);
		enterConfirmPassword(confirmPassword);
		clickOnSubmitButton();
	}
}
