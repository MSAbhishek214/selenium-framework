package com.darkuros.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;
import com.darkuros.selenium.pageobjects.LoginPage;

public class LoginTest extends BaseTest {

	// Declare the LoginPage object at the class level
	private LoginPage loginPage;

	// This @BeforeMethod will run before EACH @Test method in THIS class
	// (LoginTest)
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		loginPage = new LoginPage(driver);
	}

	@Test
	public void LoginWithValidCredentials() {
		Assert.assertTrue(loginPage.loginApplication("dark@uros.com", "123@Dark").isHomePageDisplayed());
	}

	@Test
	public void LoginWithInvalidUserEmail() {
		loginPage.loginApplication("light@uros.com", "123@Dark");
		Assert.assertEquals(loginPage.getToastContainerText(), "Incorrect email or password.");
	}

	@Test
	public void LoginWithInvalidPassword() {
		loginPage.loginApplication("dark@uros.com", "123@light");
		Assert.assertEquals(loginPage.getToastContainerText(), "Incorrect email or password.");
	}

	@Test
	public void LoginWithInvalidCredentials() {
		loginPage.loginApplication("light@uros.com", "123@light");
		Assert.assertEquals(loginPage.getToastContainerText(), "Incorrect email or password.");
	}

	@Test
	public void isForgotPasswordLinkWorking() {
		Assert.assertEquals(loginPage.navigateToForgotPasswordLink().getCurrentPageURL(),
				"https://rahulshettyacademy.com/client/auth/password-new");
	}

	@Test
	public void isRegisterLinkWorking() {
		Assert.assertEquals(loginPage.navigateToRegisterLink().getCurrentPageURL(),
				"https://rahulshettyacademy.com/client/auth/register");
	}

}
