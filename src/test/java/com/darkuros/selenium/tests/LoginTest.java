package com.darkuros.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;
import com.darkuros.selenium.pageobjects.LoginPage;
import com.darkuros.selenium.utils.ConfigReader;
import com.darkuros.selenium.utils.DataProviderUtils;
import com.darkuros.selenium.utils.FrameworkHealthChecker;

public class LoginTest extends BaseTest {
	// This @BeforeMethod will run before EACH @Test method in THIS class
	// (LoginTest)
	@Override
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		super.setup(); // Call the setup method from BaseTest to initialize the driver
		FrameworkHealthChecker.validateDriver(getDriver(), "LoginTest.setup()");
		FrameworkHealthChecker.validateConfig(ConfigReader.getProps(), "LoginTest.setup()");
	}

	@Test(dataProvider = "loginScenarios", dataProviderClass = DataProviderUtils.class)
	public void loginScenarios(String email, String password, String expectedResult, String expectedErrorMessage) {
		LoginPage loginPage = new LoginPage(getDriver());
		if (expectedResult.equalsIgnoreCase("success")) {
			Assert.assertTrue(loginPage.loginApplication(email, password).isHomePageDisplayed(), "Login failed for valid credentials.");
		} else {
			loginPage.loginApplication(email, password);
			Assert.assertEquals(loginPage.getToastContainerText(), expectedErrorMessage);
		}
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
