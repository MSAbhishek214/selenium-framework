package com.darkuros.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;
import com.darkuros.selenium.models.UserTestData;
import com.darkuros.selenium.pageobjects.LoginPage;
import com.darkuros.selenium.pageobjects.RegisterPage;
import com.darkuros.selenium.utils.DataProviderUtils;

public class RegisterTest extends BaseTest {

	@Test
	public void clickRegisterWithEmptyForm() {
		reporter.logInfo("Starting test to submit empty registration form");
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.clickRegisterButton();
		reporter.logInfo("Submitted empty registration form");

		/*
		 * Assertions for submitting an empty form Make sure to offload all wait
		 * conditions and locator strategies to its respective PO file
		 */
		Assert.assertEquals(registerPage.getFirstNameEmptyErrorText(), "*First Name is required");
		reporter.logPass("First Name required error message displayed as expected");
		Assert.assertEquals(registerPage.getEmailRequiredErrorText(), "*Email is required");
		reporter.logPass("Email required error message displayed as expected");
		Assert.assertEquals(registerPage.getPhoneRequiredErrorText(), "*Phone Number is required");
		reporter.logPass("Phone Number required error message displayed as expected");
		Assert.assertEquals(registerPage.getPasswordRequiredErrorText(), "*Password is required");
		reporter.logPass("Password required error message displayed as expected");
		Assert.assertEquals(registerPage.getConfirmPasswordRequiredErrorText(), "Confirm Password is required");
		reporter.logPass("Confirm Password required error message displayed as expected");
		if (!registerPage.isCheckboxSelected()) {
			Assert.assertEquals(registerPage.getCheckboxErrorText(), "*Please check above checkbox");
			reporter.logPass("Checkbox required error message displayed as expected");
		}
	}

	/**
	 * This test will register a user with valid details and check if the
	 * registration was successful.
	 * 
	 * @param user UserTestData object containing user details for registration
	 */
	@Test(dataProvider = "registrationData", dataProviderClass = DataProviderUtils.class)
	public void registerUserWithValidDetails(UserTestData user) {
		reporter.logInfo("Starting test to register user with valid details");
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.registerUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(),
				user.getOccupation(), user.getGender(), user.getPassword());

		Assert.assertEquals(registerPage.getRegisterSuccessfulToast(), "Registered Successfully");
		Assert.assertEquals(registerPage.getSuccessMessageAfterRegister(), "Account Created Successfully");
		reporter.logPass("User registered successfully with valid details");
	}

	/**
	 * This test will register a user with only the required fields and check if the
	 * registration was successful.
	 * 
	 * @param user UserTestData object containing user details for registration
	 */
	@Test(dataProvider = "registrationData", dataProviderClass = DataProviderUtils.class)
	public void registerUserWithOnlyRequiredFields(UserTestData user) {
		reporter.logInfo("Starting test to register user with only required fields");
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.registerUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), "",
				"", user.getPassword());

		Assert.assertEquals(registerPage.getRegisterSuccessfulToast(), "Registered Successfully");
		Assert.assertEquals(registerPage.getSuccessMessageAfterRegister(), "Account Created Successfully");
		reporter.logPass("User registered successfully with only required fields");
	}

	/**
	 * This test will register a user with an invalid email format and check if the
	 * error message is displayed.
	 * 
	 * @param user UserTestData object containing user details for registration
	 */
	@Test(dataProvider = "registrationData", dataProviderClass = DataProviderUtils.class)
	public void passwordLessThan8Chars(UserTestData user) {
		reporter.logInfo("Starting test to register user with password less than 8 characters");
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.registerUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(),
				user.getOccupation(), user.getGender(), user.getPassword().substring(0, 7));

		Assert.assertEquals(registerPage.getMessagePasswordLessThan8Chars(), "Password must be 8 Character Long!");
		reporter.logPass("Password less than 8 characters error message displayed as expected");
	}

}
