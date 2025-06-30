package com.darkuros.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;
import com.darkuros.selenium.pageobjects.LoginPage;
import com.darkuros.selenium.utils.DataProviderUtils;

public class LoginTest extends BaseTest {
	// This @BeforeMethod will run before EACH @Test method in THIS class
	// (LoginTest)
	@Override
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		super.setup(); // Call the setup method from BaseTest to initialize the driver
		System.out.println("🧪 Driver before LoginPage init: " + getDriver());
	}

	@Test(dataProvider = "loginData", dataProviderClass = DataProviderUtils.class, groups = "debug")
	public void LoginWithValidCredentials(String email, String password) {
		LoginPage loginPage = new LoginPage(getDriver());
		Assert.assertTrue(loginPage.loginApplication(email, password).isHomePageDisplayed());
	}

	@Test
	public void LoginWithInvalidUserEmail() {
		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.loginApplication("light@uros.com", "123@Dark");
		Assert.assertEquals(loginPage.getToastContainerText(), "Incorrect email or password.");
	}

	@Test
	public void LoginWithInvalidPassword() {
		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.loginApplication("dark@uros.com", "123@light");
		Assert.assertEquals(loginPage.getToastContainerText(), "Incorrect email or password.");
	}

	@Test
	public void LoginWithInvalidCredentials() {
		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.loginApplication("light@uros.com", "123@light");
		Assert.assertEquals(loginPage.getToastContainerText(), "Incorrect email or password.");
	}

	@Test
	public void isForgotPasswordLinkWorking() {
		LoginPage loginPage = new LoginPage(getDriver());
		Assert.assertEquals(loginPage.navigateToForgotPasswordLink().getCurrentPageURL(),
				"https://rahulshettyacademy.com/client/auth/password-new");
	}

	@Test
	public void isRegisterLinkWorking() {
		LoginPage loginPage = new LoginPage(getDriver());
		Assert.assertEquals(loginPage.navigateToRegisterLink().getCurrentPageURL(),
				"https://rahulshettyacademy.com/client/auth/register");
	}

}
