package com.darkuros.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;
import com.darkuros.selenium.pageobjects.ForgotPasswordPage;
import com.darkuros.selenium.pageobjects.LoginPage;

public class ForgotPasswordTest extends BaseTest {

	// Declare the LoginPage object at the class level
	private LoginPage loginPage;

	// This @BeforeMethod will run before EACH @Test method in THIS class
	// (LoginTest)
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		loginPage = new LoginPage(getDriver());
	}

	@Test(groups = "debug")
	public void saveNewPasswordWithValidCredentials() {
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		loginPage = forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "123@Dark", "123@Dark");

		Assert.assertEquals(loginPage.getLoginPageURL(),
				"https://rahulshettyacademy.com/client/auth/login");
		Assert.assertEquals(loginPage.getPasswordChangeSuccessfulText(), "Password Changed Successfully");
	}

	@Test
	public void invalidEmail() {
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("com", "123@Dark", "123@Dark");

		Assert.assertEquals(forgotPasswordPage.getInvalidEmailErrorText(), "*Enter Valid Email");
	}

	@Test
	public void emptyEmail() {
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("", "123@Dark", "123@Dark");

		Assert.assertEquals(forgotPasswordPage.getEmptyEmailErrorText(), "*Email is required");
	}

	@Test
	public void emptyPassword() {
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "", "123@Dark");

		Assert.assertEquals(forgotPasswordPage.getPasswordErrorText(), "*Password is required");
		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(),
				"Password and Confirm Password must match with each other.");

	}

	@Test
	public void emptyConfirmPassword() {
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "123@Dark", "");

		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(), "*Confirm Password is required");
	}

	@Test
	public void emptyPasswordAndConfirmPassword() {
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "", "");

		Assert.assertEquals(forgotPasswordPage.getPasswordErrorText(), "*Password is required");
		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(), "*Confirm Password is required");
	}

	@Test
	public void passwordsDontMatch() {
		ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "abc", "bca");

		Assert.assertEquals(forgotPasswordPage.getConfirmPasswordErrorText(),
				"Password and Confirm Password must match with each other.");
	}

}
