package com.darkuros.selenium.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {
	// Create a web driver object -> lives and is managed here
	protected WebDriver driver;

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "baseURL" })
	public void setup(String baseURL) {
		// Initializing WebDriver for Chrome browser
		driver = new ChromeDriver();
		
		// Maximise window
		driver.manage().window().maximize();
		
		// Optional: Implicit wait (applies globally to findElement calls)
		// Explicit waits in Page Objects will handle precise waits.
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		// Navigating to the base URL
		driver.get(baseURL);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}