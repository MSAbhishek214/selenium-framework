package com.darkuros.selenium.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

	// Class level driver
	private WebDriver driver;
	private WebDriverWait wait;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		this.setWait(new WebDriverWait(driver, Duration.ofSeconds(5)));
	}

	// Username field locator
	@FindBy(id = "firstName")
	WebElement firstNameInput;

	@FindBy(id = "lastName")
	WebElement lastNameInput;

	@FindBy(id = "userEmail")
	WebElement emailInput;

	@FindBy(id = "userMobile")
	WebElement phoneNumberInput;

	@FindBy(css = "input[value='Male']")
	WebElement genderMaleRadioButton;

	@FindBy(css = "input[value='Female']")
	WebElement genderFemaleRadioButton;

	@FindBy(id = "userPassword")
	WebElement passwordInput;

	@FindBy(id = "confirmPassword")
	WebElement confirmPasswordInput;

	@FindBy(css = ".col-md-1 input")
	WebElement ageCheckBoxSelection;

	@FindBy(id = "login")
	WebElement registerButton;

	@FindBy(css = ".text-reset")
	WebElement navigateToLogin;

	@FindBy(css = ".login-wrapper h1")
	WebElement successMessage;

	@FindBy(xpath = "//div[@aria-label='Registered Successfully']")
	WebElement registerSuccessfulToast;
	
	@FindBy(xpath = "//div[@aria-label='Password must be 8 Character Long!']")
	WebElement passwordLessThan8CharsErrorToast;

	// Provide details of the new user and register
	public void registerUser(String firstName, String lastName, String email, String phoneNumber, String Occupation,
			String gender, String password) {
		enterFirstName(firstName);
		enterLastName(lastName);
		enterEmail(email);
		enterPhoneNumber(phoneNumber);
		selectOccupation(Occupation);
		selectGender(gender);
		enterPassword(password);
		enterConfirmPassword(password);
		checkAgeCheckbox();
		clickRegisterButton();
	}

	public void enterFirstName(String firstName) {
		firstNameInput.sendKeys(firstName);
	}

	public void enterLastName(String lastName) {
		lastNameInput.sendKeys(lastName);
	}

	public void enterEmail(String email) {
		emailInput.sendKeys(email);
	}

	public void enterPhoneNumber(String phoneNumber) {
		phoneNumberInput.sendKeys(phoneNumber);
	}

	public void selectOccupation(String occupationValue) {

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".custom-select"))));
		Select occupation = new Select(driver.findElement(By.cssSelector(".custom-select")));
		if (!occupationValue.equalsIgnoreCase("")) {
			occupation.selectByVisibleText(occupationValue);
		}

	}

	public void selectGender(String gender) {
		if (gender == "Male")
			genderMaleRadioButton.click();
		else if (gender == "Female")
			genderFemaleRadioButton.click();
	}

	public void enterPassword(String password) {
		passwordInput.sendKeys(password);
	}

	public void enterConfirmPassword(String confirmPassword) {
		confirmPasswordInput.sendKeys(confirmPassword);
	}

	public void checkAgeCheckbox() {
		if (!ageCheckBoxSelection.isSelected()) {
			ageCheckBoxSelection.click();
		}
	}

	public void clickRegisterButton() {
		registerButton.click();
	}

	public LoginPage navigateToLogin() {
		navigateToLogin.click();
		return new LoginPage(driver);
	}

	public WebDriverWait getWait() {
		return wait;
	}

	public void setWait(WebDriverWait wait) {
		this.wait = wait;
	}

	public String getSuccessMessageAfterRegister() {
		return successMessage.getText().trim();
	}

	public String getRegisterSuccessfulToast() {
		return registerSuccessfulToast.getText().trim();
	}
	
	public String getMessagePasswordLessThan8Chars() {
		return passwordLessThan8CharsErrorToast.getText().trim();
	}
}
