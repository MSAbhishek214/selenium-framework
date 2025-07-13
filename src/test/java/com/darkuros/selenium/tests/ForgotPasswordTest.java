package com.darkuros.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;
import com.darkuros.selenium.pageobjects.ForgotPasswordPage;
import com.darkuros.selenium.pageobjects.LoginPage;
import com.darkuros.selenium.utils.ConfigReader;
import com.darkuros.selenium.utils.FrameworkHealthChecker;

public class ForgotPasswordTest extends BaseTest {

	// This @BeforeMethod will run before EACH @Test method in THIS class
	// (LoginTest)
	@Override
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		super.setup();
		FrameworkHealthChecker.validateDriver(getDriver(), "ForgotPasswordTest.setup()");
		FrameworkHealthChecker.validateConfig(ConfigReader.getProps(), "ForgotPasswordTest.setup()", "browser",
				"baseURL");
	}

	@Test
	public void saveNewPasswordWithValidCredentials() {
		LoginPage loginPage = new LoginPage(getDriver());
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		loginPage = forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "123@Dark", "123@Dark");
		Assert.assertEquals(loginPage.getLoginPageURL(), "https://rahulshettyacademy.com/client/auth/login");
		Assert.assertEquals(loginPage.getPasswordChangeSuccessfulText(), "Password Changed Successfully");
	}

	@Test
	public void invalidEmail() {
		LoginPage loginPage = new LoginPage(getDriver());
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("com", "123@Dark", "123@Dark");

		Assert.assertEquals(forgotPasswordPage.getInvalidEmailErrorText(), "*Enter Valid Email");
	}

	@Test
	public void emptyEmail() {
		LoginPage loginPage = new LoginPage(getDriver());
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("", "123@Dark", "123@Dark");

		Assert.assertEquals(forgotPasswordPage.getEmptyEmailErrorText(), "*Email is required");
	}

	@Test
	public void emptyPassword() {
		LoginPage loginPage = new LoginPage(getDriver());
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "", "123@Dark");

		Assert.assertEquals(forgotPasswordPage.getPasswordErrorText(), "*Password is required");
		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(),
				"Password and Confirm Password must match with each other.");

	}

	@Test
	public void emptyConfirmPassword() {
		LoginPage loginPage = new LoginPage(getDriver());
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "123@Dark", "");

		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(), "*Confirm Password is required");
	}

	@Test
	public void emptyPasswordAndConfirmPassword() {
		LoginPage loginPage = new LoginPage(getDriver());
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "", "");

		Assert.assertEquals(forgotPasswordPage.getPasswordErrorText(), "*Password is required");
		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(), "*Confirm Password is required");
	}

	@Test
	public void passwordsDontMatch() {
		LoginPage loginPage = new LoginPage(getDriver());
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "abc", "bca");

		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(),
				"Password and Confirm Password must match with each other.");
	}

}
