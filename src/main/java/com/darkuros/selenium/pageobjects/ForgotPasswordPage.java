package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ForgotPasswordPage extends BasePage {

	public ForgotPasswordPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = ".mt-1 + input")
	WebElement emailInput;

	@FindBy(id = "userPassword")
	WebElement passwordInput;

	@FindBy(id = "confirmPassword")
	WebElement confirmPasswordInput;

	@FindBy(css = "button[type='submit']")
	WebElement submitButton;

	@FindBy(xpath = "//a[normalize-space()='Login']")
	WebElement loginLink;

	@FindBy(xpath = "//a[normalize-space()='Register']")
	WebElement registerLink;

	@FindBy(css = ".invalid-feedback div")
	WebElement invalidEmailError;

	@FindBy(css = ".invalid-feedback div")
	WebElement emptyEmailError;

	@FindBy(css = "#userPassword + div.invalid-feedback")
	WebElement passwordError;

	@FindBy(css = "#confirmPassword+ div.invalid-feedback")
	WebElement confirmPasswordError;

	/*
	 * All methods defining individual actions go here Keep actions small and
	 * reusable and return state of element instead of nothing to make them useful
	 */

	public String getPasswordErrorText() {
		return wait.until(ExpectedConditions.visibilityOf(passwordError)).getText().trim();
	}

	public String getConfirmPasswordErrorText() {
		return wait.until(ExpectedConditions.visibilityOf(confirmPasswordError)).getText().trim();
	}

	public String getEmptyEmailErrorText() {
		return wait.until(ExpectedConditions.visibilityOf(emptyEmailError)).getText().trim();
	}

	public String getInvalidEmailErrorText() {
		wait.until(ExpectedConditions.visibilityOf(invalidEmailError)).getText().trim();
		return invalidEmailError.getText().trim();
	}

	public void enterUserEmail(String userEmail) {
		wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(userEmail);
	}

	public void enterPassword(String password) {
		wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
	}

	public void enterConfirmPassword(String confirmPassword) {
		wait.until(ExpectedConditions.visibilityOf(confirmPasswordInput)).sendKeys(confirmPassword);
	}

	public void clickOnSubmitButton() {
		wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
	}

	// Method to navigate back to Login page from forgot password page
	public LoginPage navigateBackToLoginPage() {
		loginLink.click();
		return new LoginPage(getDriver());
	}

	// Method to navigate back to register page from forgot password page
	public RegisterPage navigateBackToRegisterPage() {
		registerLink.click();
		return new RegisterPage(getDriver());
	}

	public void fillSavePasswordFormAndSubmit(String userEmail, String password, String confirmPassword) {
		enterUserEmail(userEmail);
		enterPassword(password);
		enterConfirmPassword(confirmPassword);
		clickOnSubmitButton();
	}

	public String getForgotPasswordPageURL() {
		return getCurrentPageURL();
	}
}
