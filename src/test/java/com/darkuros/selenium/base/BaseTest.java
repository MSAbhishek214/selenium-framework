package com.darkuros.selenium.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.darkuros.selenium.utils.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	// Create a web driver object -> lives and is managed here
	private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

	public WebDriver getDriver() {
		return driverThreadLocal.get();
	}

	@BeforeMethod(alwaysRun = true)
	public void setup() {
		String baseURL = ConfigReader.get("baseURL");
		String browser = ConfigReader.get("browser");
		Long implicitWait = Long.parseLong(ConfigReader.get("implicitWait"));

		WebDriver localDriver;

		switch (browser.toLowerCase()) {
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			localDriver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			localDriver = new EdgeDriver();
			break;
		default:
			WebDriverManager.chromedriver().setup();
			localDriver = new ChromeDriver();
		}

		driverThreadLocal.set(localDriver);
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		getDriver().get(baseURL);
	}

	public String captureScreenshot(String methodName) {
		WebDriver driver = getDriver();
		if (driver == null) {
			System.err.println("🚨 Screenshot skipped: driver is null for " + methodName);
			return null;
		}

		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";
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