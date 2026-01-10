package com.darkuros.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;
import com.darkuros.selenium.pageobjects.ForgotPasswordPage;
import com.darkuros.selenium.pageobjects.LoginPage;

public class ForgotPasswordTest extends BaseTest {

	@Test
	public void saveNewPasswordWithValidCredentials() {
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		loginPage = forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "123@Dark", "123@Dark");

		Assert.assertEquals(loginPage.getLoginPageURL(), "https://rahulshettyacademy.com/client/#/auth/login");
		Assert.assertEquals(loginPage.getPasswordChangeSuccessfulText(), "Password Changed Successfully");
		reporter.logPass("Password changed successfully and navigated to login page.");
	}

	@Test
	public void invalidEmail() {
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("com", "123@Dark", "123@Dark");

		Assert.assertEquals(forgotPasswordPage.getInvalidEmailErrorText(), "*Enter Valid Email");
		reporter.logPass("Invalid email error message displayed as expected.");
	}

	@Test
	public void emptyEmail() {
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("", "123@Dark", "123@Dark");

		Assert.assertEquals(forgotPasswordPage.getEmptyEmailErrorText(), "*Email is required");
		reporter.logPass("Empty email error message displayed as expected.");
	}

	@Test
	public void emptyPassword() {
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "", "123@Dark");

		Assert.assertEquals(forgotPasswordPage.getPasswordErrorText(), "*Password is required");
		reporter.logPass("Empty password error message displayed as expected.");
		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(),
				"Password and Confirm Password must match with each other.");
		reporter.logPass("Confirm password mismatch message displayed as expected.");
	}

	@Test
	public void emptyConfirmPassword() {
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "123@Dark", "");

		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(), "*Confirm Password is required");
		reporter.logPass("Empty confirm password error message displayed as expected.");
	}

	@Test
	public void emptyPasswordAndConfirmPassword() {
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "", "");

		Assert.assertEquals(forgotPasswordPage.getPasswordErrorText(), "*Password is required");
		reporter.logPass("Empty password error message displayed as expected.");
		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(), "*Confirm Password is required");
		reporter.logPass("Empty confirm password error message displayed as expected.");
	}

	@Test
	public void passwordsDontMatch() {
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "abc", "bca");

		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(),
				"Password and Confirm Password must match with each other.");
		reporter.logPass("Confirm password mismatch message displayed as expected.");
	}
}
