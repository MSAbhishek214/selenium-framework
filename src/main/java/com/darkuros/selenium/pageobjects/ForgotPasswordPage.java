package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ForgotPasswordPage {

	// Declare a WebDriver
	WebDriver driver;

	public ForgotPasswordPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".mt-1 + input")
	WebElement userEmail;

	@FindBy(id = "userPassword")
	WebElement userPassword;

	@FindBy(id = "confirmPassword")
	WebElement userConfirmPassword;

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
		this.userEmail.sendKeys(userEmail);
	}

	public void enterPassword(String password) {
		this.userPassword.sendKeys(password);
	}

	public void enterConfirmPassword(String confirmPassword) {
		this.userConfirmPassword.sendKeys(confirmPassword);
	}

	public void clickOnSubmitButton() {
		this.submitButton.click();
	}

	// Method to navigate back to Login page from forgot password page
	public void navigateBackToLoginPage() {
		this.loginLink.click();
	}

	// Method to navigate back to register page from forgot password page
	public void navigateBackToRegisterPage() {
		this.registerLink.click();
	}
}
