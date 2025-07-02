package com.darkuros.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;
import com.darkuros.selenium.models.UserTestData;
import com.darkuros.selenium.pageobjects.LoginPage;
import com.darkuros.selenium.pageobjects.RegisterPage;
import com.darkuros.selenium.utils.ConfigReader;
import com.darkuros.selenium.utils.DataProviderUtils;
import com.darkuros.selenium.utils.FrameworkHealthChecker;

public class RegisterTest extends BaseTest {

	@Override
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		super.setup(); // Call the setup method from BaseTest to initialize the driver
		FrameworkHealthChecker.validateDriver(getDriver(), "RegisterTest.setup()");
		FrameworkHealthChecker.validateConfig(ConfigReader.getProps(), "RegisterTest.setup()");
	}

	@Test
	public void clickRegisterWithEmptyForm() {
		LoginPage loginPage = new LoginPage(getDriver());
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.clickRegisterButton();

		/*
		 * Assertions for submitting an empty form Make sure to offload all wait
		 * conditions and locator strategies to its respective PO file
		 */
		Assert.assertEquals(registerPage.getFirstNameEmptyErrorText(), "*First Name is required");
		Assert.assertEquals(registerPage.getEmailRequiredErrorText(), "*Email is required");
		Assert.assertEquals(registerPage.getPhoneRequiredErrorText(), "*Phone Number is required");
		Assert.assertEquals(registerPage.getPasswordRequiredErrorText(), "*Password is required");
		Assert.assertEquals(registerPage.getConfirmPasswordRequiredErrorText(), "Confirm Password is required");
		if (!registerPage.isCheckboxSelected()) {
			Assert.assertEquals(registerPage.getCheckboxErrorText(), "*Please check above checkbox");
		}
	}

	@Test(dataProvider = "registrationData", dataProviderClass = DataProviderUtils.class)
	public void registerUserWithValidDetails(UserTestData user) {
		LoginPage loginPage = new LoginPage(getDriver());
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.registerUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(),
				user.getOccupation(), user.getGender(), user.getPassword());

		Assert.assertEquals(registerPage.getRegisterSuccessfulToast(), "Registered Successfully");
		Assert.assertEquals(registerPage.getSuccessMessageAfterRegister(), "Account Created Successfully");
	}

	@Test(dataProvider = "registrationData", dataProviderClass = DataProviderUtils.class)
	public void registerUserWithOnlyRequiredFields(UserTestData user) {
		LoginPage loginPage = new LoginPage(getDriver());
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.registerUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), "",
				"", user.getPassword());

		Assert.assertEquals(registerPage.getRegisterSuccessfulToast(), "Registered Successfully");
		Assert.assertEquals(registerPage.getSuccessMessageAfterRegister(), "Account Created Successfully");
	}

	@Test(dataProvider = "registrationData", dataProviderClass = DataProviderUtils.class)
	public void passwordLessThan8Chars(UserTestData user) {
		LoginPage loginPage = new LoginPage(getDriver());
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.registerUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(),
				user.getOccupation(), user.getGender(), user.getPassword().substring(0, 7));

		Assert.assertEquals(registerPage.getMessagePasswordLessThan8Chars(), "Password must be 8 Character Long!");
	}

}
