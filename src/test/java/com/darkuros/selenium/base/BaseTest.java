package com.darkuros.selenium.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
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

		// Read browser and headless mode from system properties or config file
		String browser = System.getProperty("browser", ConfigReader.get("browser"));
		Boolean headless = Boolean.parseBoolean(System.getProperty("headless", ConfigReader.get("headless")));
		Long implicitWait = Long.parseLong(ConfigReader.get("implicitWait"));

		WebDriver localDriver;

		switch (browser.toLowerCase()) {
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			localDriver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			EdgeOptions edgeOptions = new EdgeOptions();
			if (headless) {
				edgeOptions.addArguments("--headless=new");
				edgeOptions.addArguments("--disable-gpu");
				edgeOptions.addArguments("--disable-dev-shm-usage");
				edgeOptions.addArguments("--window-size=1920,1080"); // Set window size for headless mode
			}
			localDriver = new EdgeDriver(edgeOptions);
			break;
		default:
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			if (headless) {
				chromeOptions.addArguments("--headless=new");
				chromeOptions.addArguments("--disable-gpu");
				chromeOptions.addArguments("--disable-dev-shm-usage");
				chromeOptions.addArguments("--window-size=1920,1080"); // Set window size for headless mode
			}
			localDriver = new ChromeDriver(chromeOptions);
		}

		driverThreadLocal.set(localDriver);
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		getDriver().get(baseURL);
	}

	public String captureScreenshot(String methodName) {
		WebDriver driver = getDriver();
		if (driver == null) {
			System.err.println("🚫 Screenshot skipped: driver is null for " + methodName);
			return null;
		}

		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String path = System.getProperty("user.dir") + "/screenshots/" + methodName + "_"
					+ System.currentTimeMillis() + ".png";
			Files.copy(src.toPath(), new File(path).toPath());
			System.out.println("📸 Screenshot captured: " + path);
			return path;
		} catch (IOException | WebDriverException e) {
			System.err.println("❌ Screenshot capture failed: " + e.getMessage());
			return null;
		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (getDriver() != null) {
			getDriver().quit();
			driverThreadLocal.remove(); // Clean up the ThreadLocal reference
		}
	}
}