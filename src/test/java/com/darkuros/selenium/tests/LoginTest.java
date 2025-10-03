package com.darkuros.selenium.tests;

import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;
import com.darkuros.selenium.pageobjects.LoginPage;
import com.darkuros.selenium.utils.ConfigReader;
import com.darkuros.selenium.utils.DataProviderUtils;
import com.darkuros.selenium.utils.FrameworkHealthChecker;
import com.darkuros.selenium.utils.LoggerFactoryUtils;

public class LoginTest extends BaseTest {

	private static final Logger logger = LoggerFactoryUtils.getLogger(LoginTest.class);

	// This @BeforeMethod will run before EACH @Test method in THIS class
	// (LoginTest)
	@Override
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		logger.info("Setting up LoginTest environment.");
		super.setup(); // Call the setup method from BaseTest to initialize the driver
		FrameworkHealthChecker.validateDriver(getDriver(), "LoginTest.setup()");
		FrameworkHealthChecker.validateConfig(ConfigReader.getProps(), "LoginTest.setup()", "browser", "baseURL");
		logger.info("LoginTest environment setup complete.");
	}

	@Test(dataProvider = "loginScenarios", dataProviderClass = DataProviderUtils.class)
	public void loginScenarios(String email, String password, String expectedResult, String expectedErrorMessage) {
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		if (expectedResult.equalsIgnoreCase("success")) {
			reporter.logInfo("Starting Login Test with valid credentials.");
			Assert.assertTrue(loginPage.loginApplication(email, password).isHomePageDisplayed(),
					"Login failed for valid credentials.");
			reporter.logPass("Login successful with valid credentials.");
		} else {
			reporter.logInfo("Starting Login Test with invalid credentials.");
			loginPage.loginApplication(email, password);
			Assert.assertEquals(loginPage.getToastContainerText(), expectedErrorMessage);
			reporter.logPass("Login failed as expected with error message: " + expectedErrorMessage);
		}
	}

	@Test
	public void isForgotPasswordLinkWorking() {
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		logger.info("Checking if the Forgot Password link is working.");
		reporter.logInfo("Navigating to Forgot Password page.");
		Assert.assertEquals(loginPage.navigateToForgotPasswordLink().getCurrentPageURL(),
				"https://rahulshettyacademy.com/client/#/auth/password-new");
		logger.info("Forgot Password link is working as expected.");
		reporter.logPass("Forgot Password link navigation successful.");
	}

	@Test
	public void isRegisterLinkWorking() {
		LoginPage loginPage = new LoginPage(getDriver(), explicitWaitInSeconds, reporter);
		logger.info("Checking if the Register link is working.");
		reporter.logInfo("Navigating to Register page.");
		Assert.assertEquals(loginPage.navigateToRegisterLink().getCurrentPageURL(),
				"https://rahulshettyacademy.com/client/#/auth/register");
		logger.info("Register link is working as expected.");
		reporter.logPass("Register link navigation successful.");
	}
}
