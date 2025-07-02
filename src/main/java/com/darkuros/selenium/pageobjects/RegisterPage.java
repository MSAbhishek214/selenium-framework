package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage extends BasePage {

	public RegisterPage(WebDriver driver) {
		super(driver);
	}

	// Username field locator
	@FindBy(id = "firstName")
	private WebElement firstNameInput;

	@FindBy(id = "lastName")
	private WebElement lastNameInput;

	@FindBy(id = "userEmail")
	private WebElement emailInput;

	@FindBy(id = "userMobile")
	private WebElement phoneNumberInput;

	@FindBy(css = "input[value='Male']")
	private WebElement genderMaleRadioButton;

	@FindBy(css = "input[value='Female']")
	private WebElement genderFemaleRadioButton;

	@FindBy(id = "userPassword")
	private WebElement passwordInput;

	@FindBy(id = "confirmPassword")
	private WebElement confirmPasswordInput;

	@FindBy(css = ".col-md-1 input")
	private WebElement ageCheckBoxSelection;

	@FindBy(id = "login")
	private WebElement registerButton;

	@FindBy(css = ".text-reset")
	private WebElement navigateToLogin;

	@FindBy(css = ".login-wrapper h1")
	private WebElement successMessage;

	@FindBy(xpath = "//div[@aria-label='Registered Successfully']")
	private WebElement registerSuccessfulToast;

	@FindBy(xpath = "//div[@aria-label='Password must be 8 Character Long!']")
	private WebElement passwordLessThan8CharsErrorToast;

	@FindBy(css = "#firstName + div")
	private WebElement firstNameEmptyError;

	@FindBy(css = ".ng-tns-c4-7")
	private WebElement lastNameRequiredToast;

	@FindBy(xpath = "//div[@aria-label='Last Name is required!']")
	private WebElement lastNameRequiredError;

	@FindBy(css = "#userEmail+ div")
	private WebElement emailRequiredError;

	@FindBy(css = "#userMobile + div")
	private WebElement phoneRequiredError;

	@FindBy(css = "#userPassword + div")
	private WebElement passwordRequiredError;

	@FindBy(css = "#confirmPassword + div")
	private WebElement confirmPasswordRequiredError;

	@FindBy(xpath = "//div[contains(text(),'*Please check above checkbox')]")
	private WebElement checkboxError;

	@FindBy(css = ".custom-select")
	private WebElement occupationDropdown;

	// Provide details of the new user and register
	public void registerUser(String firstName, String lastName, String email, String phoneNumber, String Occupation,
			String gender, String password) {
		enterFirstName(firstName);
		enterLastName(lastName);
		enterEmail(email);
		enterPhoneNumber(phoneNumber);
		selectOccupation(Occupation);
		if (!gender.isEmpty())
			selectGender(gender);
		enterPassword(password);
		enterConfirmPassword(password);
		ageCheckBoxSelection.click();
		clickRegisterButton();
	}

	public void enterFirstName(String firstName) {
		wait.until(ExpectedConditions.visibilityOf(firstNameInput)).sendKeys(firstName);
	}

	public void enterLastName(String lastName) {
		wait.until(ExpectedConditions.visibilityOf(lastNameInput)).sendKeys(lastName);
	}

	public void enterEmail(String email) {
		wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);
	}

	public void enterPhoneNumber(String phoneNumber) {
		wait.until(ExpectedConditions.visibilityOf(phoneNumberInput)).sendKeys(phoneNumber);

	}

	public void selectOccupation(String occupationValue) {
		wait.until(ExpectedConditions.elementToBeClickable(occupationDropdown));
		if (!occupationValue.isBlank()) {
			new Select(occupationDropdown).selectByVisibleText(occupationValue);
		}
	}

	public void selectGender(String gender) {
		WebElement genderOption = getDriver().findElement(By.cssSelector("input[value='" + gender + "']"));
		wait.until(ExpectedConditions.elementToBeClickable(genderOption)).click();
	}

	public void enterPassword(String password) {
		wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
	}

	public void enterConfirmPassword(String confirmPassword) {
		wait.until(ExpectedConditions.visibilityOf(confirmPasswordInput)).sendKeys(confirmPassword);
	}

	public Boolean isCheckBoxSelected() {
		return wait.until(ExpectedConditions.elementToBeClickable(ageCheckBoxSelection)).isSelected();
	}

	public void clickRegisterButton() {
		wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
	}

	public LoginPage navigateToLogin() {
		navigateToLogin.click();
		return new LoginPage(getDriver());
	}

	public String getSuccessMessageAfterRegister() {
		return successMessage.getText().trim();
	}

	public String getRegisterSuccessfulToast() {
		return wait.until(ExpectedConditions.visibilityOf(registerSuccessfulToast)).getText().trim();
	}

	public String getMessagePasswordLessThan8Chars() {
		return wait.until(ExpectedConditions.visibilityOf(passwordLessThan8CharsErrorToast)).getText().trim();
	}

	public String getFirstNameEmptyErrorText() {
		return wait.until(ExpectedConditions.visibilityOf(firstNameEmptyError)).getText().trim();
	}

	public String getLastNameRequiredToast() {
		wait.until(ExpectedConditions.visibilityOf(lastNameRequiredToast));
		return lastNameRequiredError.getText().trim();
	}

	public String getEmailRequiredErrorText() {
		return wait.until(ExpectedConditions.visibilityOf(emailRequiredError)).getText().trim();
	}

	public String getPhoneRequiredErrorText() {
		return wait.until(ExpectedConditions.visibilityOf(phoneRequiredError)).getText().trim();
	}

	public String getPasswordRequiredErrorText() {
		return wait.until(ExpectedConditions.visibilityOf(passwordRequiredError)).getText().trim();
	}

	public String getConfirmPasswordRequiredErrorText() {
		return wait.until(ExpectedConditions.visibilityOf(confirmPasswordRequiredError)).getText().trim();
	}

	public String getCheckboxErrorText() {
		return wait.until(ExpectedConditions.visibilityOf(checkboxError)).getText().trim();
	}

	public Boolean isCheckboxSelected() {
		return wait.until(ExpectedConditions.elementToBeClickable(ageCheckBoxSelection)).isSelected();
	}
}
