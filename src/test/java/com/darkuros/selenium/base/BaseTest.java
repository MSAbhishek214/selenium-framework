package com.darkuros.selenium.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.darkuros.selenium.pageobjects.ForgotPasswordPage;
import com.darkuros.selenium.pageobjects.LoginPage;

public class BaseTest {
	// Create a web driver object
	protected WebDriver driver;
	// Create a Web driver wait object
	protected WebDriverWait wait;
	// Create an object for LoginPage
	protected LoginPage loginPage;
	// Create an object for LoginPage
	protected ForgotPasswordPage forgotPasswordPage;

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "baseURL" })
	public void setup(String baseURL) {
		// Initializing WebDriver for Chrome browser
		driver = new ChromeDriver();
		// Create a wait object for handling explicit wait scenarios
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		// Navigating to the login practice page
		driver.get(baseURL);
		// Maximise window
		driver.manage().window().maximize();
		// Initialise loginPage object with driver
		loginPage = new LoginPage(driver);
		// Initialise forgotPasswordPage object with driver
		forgotPasswordPage = new ForgotPasswordPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
