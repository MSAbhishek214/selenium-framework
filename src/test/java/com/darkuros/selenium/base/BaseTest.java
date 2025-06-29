package com.darkuros.selenium.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	// Create a web driver object -> lives and is managed here
	private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

	public WebDriver getDriver() {
		return driverThreadLocal.get();
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "baseURL" })
	public void setup(String baseURL) {
		// Initializing WebDriver for Chrome browser
		WebDriverManager.chromedriver().setup();
		WebDriver localDriver = new ChromeDriver();
		driverThreadLocal.set(localDriver);

		// Maximise window
		getDriver().manage().window().maximize();

		// Optional: Implicit wait (applies globally to findElement calls)
		// Explicit waits in Page Objects will handle precise waits.
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Navigating to the base URL
		getDriver().get(baseURL);
	}
	
	public String captureScreenshot(String methodName) {
	    File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
	    String path = System.getProperty("user.dir") + "/screenshots/" + methodName + "_" + System.currentTimeMillis() + ".png";
	    try {
	        Files.copy(src.toPath(), new File(path).toPath());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return path;
	}


	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (getDriver() != null) {
			getDriver().quit();
			driverThreadLocal.remove(); // Clean up the ThreadLocal reference
		}
	}
}