package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;

import com.darkuros.selenium.utils.IReporter;
import com.darkuros.selenium.utils.LoggerFactoryUtils;

/**
 * Represents the registration page of the application. This page allows new
 * users to register by providing their details. Also has methods to navigate to
 * the login page.
 */
public class RegisterPage extends BasePage {
	private static final Logger logger = LoggerFactoryUtils.getLogger(RegisterPage.class);

	public RegisterPage(WebDriver driver, long explicitWaitInSeconds, IReporter reporter) {
		super(driver, explicitWaitInSeconds, reporter);
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
		logger.info(
				"Registering user with details: First Name: {}, Last Name: {}, Email: {}, Phone Number: {}, Occupation: {}, gender: {}, Password: {}",
				firstName, lastName, email, phoneNumber, Occupation, gender, password);
		reporter.logInfo("Filling out the user registration form.");
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
		logger.info("User registration form filled with provided details and submitted.");
		reporter.logInfo("User registration form submitted.");
	}

	public void enterFirstName(String firstName) {
		logger.info("Entering first name: {}", firstName);
		wait.until(ExpectedConditions.visibilityOf(firstNameInput)).sendKeys(firstName);
		reporter.logInfo("Entered first name: " + firstName);
	}

	public void enterLastName(String lastName) {
		logger.info("Entering last name: {}", lastName);
		wait.until(ExpectedConditions.visibilityOf(lastNameInput)).sendKeys(lastName);
		reporter.logInfo("Entered last name: " + lastName);
	}

	public void enterEmail(String email) {
		logger.info("Entering email: {}", email);
		wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);
		reporter.logInfo("Entered email: " + email);
	}

	public void enterPhoneNumber(String phoneNumber) {
		logger.info("Entering phone number: {}", phoneNumber);
		wait.until(ExpectedConditions.visibilityOf(phoneNumberInput)).sendKeys(phoneNumber);
		reporter.logInfo("Entered phone number: " + phoneNumber);

	}

	public void selectOccupation(String occupationValue) {
		logger.info("Selecting occupation: {}", occupationValue);
		wait.until(ExpectedConditions.elementToBeClickable(occupationDropdown));
		if (!occupationValue.isBlank()) {
			new Select(occupationDropdown).selectByVisibleText(occupationValue);
			reporter.logInfo("Selected occupation: " + occupationValue);
		}
	}

	public void selectGender(String gender) {
		WebElement genderOption = getDriver().findElement(By.cssSelector("input[value='" + gender + "']"));
		wait.until(ExpectedConditions.elementToBeClickable(genderOption)).click();
		logger.info("Selected gender: {}", gender);
		reporter.logInfo("Selected gender: " + gender);
	}

	public void enterPassword(String password) {
		logger.info("Entering password: {}", password);
		wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
		reporter.logInfo("Entered password: " + password);
	}

	public void enterConfirmPassword(String confirmPassword) {
		logger.info("Entering confirm password: {}", confirmPassword);
		wait.until(ExpectedConditions.visibilityOf(confirmPasswordInput)).sendKeys(confirmPassword);
		reporter.logInfo("Entered confirm password: " + confirmPassword);
	}

	public void clickRegisterButton() {
		wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
		logger.info("Clicked on the register button.");
	}

	public LoginPage navigateToLogin() {
		logger.info("Navigating to the login page.");
		navigateToLogin.click();
		return new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
	}

	public String getSuccessMessageAfterRegister() {
		String successMessageText = wait.until(ExpectedConditions.visibilityOf(successMessage)).getText().trim();
		logger.info("Success message after registration: {}", successMessageText);
		return successMessageText;
	}

	public String getRegisterSuccessfulToast() {
		String registerSuccessText = wait.until(ExpectedConditions.visibilityOf(registerSuccessfulToast)).getText()
				.trim();
		logger.info("Register successful toast message: {}", registerSuccessText);
		return registerSuccessText;
	}

	public String getMessagePasswordLessThan8Chars() {
		String passwordErrorText = wait.until(ExpectedConditions.visibilityOf(passwordLessThan8CharsErrorToast))
				.getText().trim();
		logger.info("Password less than 8 characters error message: {}", passwordErrorText);
		return passwordErrorText;
	}

	public String getFirstNameEmptyErrorText() {
		String firstNameEmptyErrorText = wait.until(ExpectedConditions.visibilityOf(firstNameEmptyError)).getText()
				.trim();
		logger.info("First name empty error message: {}", firstNameEmptyErrorText);
		return firstNameEmptyErrorText;
	}

	public String getLastNameRequiredToast() {
		wait.until(ExpectedConditions.visibilityOf(lastNameRequiredToast));
		String lastNameRequiredErrorText = lastNameRequiredError.getText().trim();
		logger.info("Last name required toast message: {}", lastNameRequiredErrorText);
		return lastNameRequiredErrorText;
	}

	public String getEmailRequiredErrorText() {
		String emailRequiredErrorText = wait.until(ExpectedConditions.visibilityOf(emailRequiredError)).getText()
				.trim();
		logger.info("Email required error message: {}", emailRequiredErrorText);
		return emailRequiredErrorText;
	}

	public String getPhoneRequiredErrorText() {
		String phoneRequiredErrorText = wait.until(ExpectedConditions.visibilityOf(phoneRequiredError)).getText()
				.trim();
		logger.info("Phone number required error message: {}", phoneRequiredErrorText);
		return phoneRequiredErrorText;
	}

	public String getPasswordRequiredErrorText() {
		String passwordRequiredErrorText = wait.until(ExpectedConditions.visibilityOf(passwordRequiredError)).getText()
				.trim();
		logger.info("Password required error message: {}", passwordRequiredErrorText);
		return passwordRequiredErrorText;
	}

	public String getConfirmPasswordRequiredErrorText() {
		String confirmPasswordRequiredErrorText = wait
				.until(ExpectedConditions.visibilityOf(confirmPasswordRequiredError)).getText().trim();
		logger.info("Confirm password required error message: {}", confirmPasswordRequiredErrorText);
		return confirmPasswordRequiredErrorText;
	}

	public String getCheckboxErrorText() {
		String checkboxErrorText = wait.until(ExpectedConditions.visibilityOf(checkboxError)).getText().trim();
		logger.info("Checkbox error message: {}", checkboxErrorText);
		return checkboxErrorText;
	}

	public Boolean isCheckboxSelected() {
		Boolean isSelected = wait.until(ExpectedConditions.elementToBeClickable(ageCheckBoxSelection)).isSelected();
		logger.info("Is age checkbox selected? {}", isSelected);
		return isSelected;
	}
}
