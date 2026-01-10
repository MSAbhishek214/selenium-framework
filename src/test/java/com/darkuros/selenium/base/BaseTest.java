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
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.darkuros.selenium.reporting.ExtentReportLogger;
import com.darkuros.selenium.utils.ConfigReader;
import com.darkuros.selenium.utils.FrameworkHealthChecker;
import com.darkuros.selenium.utils.IReporter;
import com.darkuros.selenium.utils.LoggerFactoryUtils;

public class BaseTest {
	/**
	 * BaseTest class serves as the foundation for all test classes in the Selenium
	 * framework. It handles WebDriver setup, configuration, and teardown, ensuring
	 * a clean environment for each test.
	 * 
	 * @author Darkuros
	 */

	// This logger is used to log messages in the BaseTest class
	private static final Logger logger = LoggerFactoryUtils.getLogger(BaseTest.class);

	// Create a web driver object -> lives and is managed here
	private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
	
	protected long explicitWaitInSeconds;
	// Reporter instance for logging test steps
	protected IReporter reporter;
	
	/**
	 * Returns the WebDriver instance for the current thread. This method ensures
	 * that each test method gets its own WebDriver instance, allowing parallel
	 * execution of tests.
	 * 
	 * @return WebDriver instance for the current thread
	 */
	public WebDriver getDriver() {
		return driverThreadLocal.get();
	}

	/**
	 * Sets up the WebDriver before each test method. It reads configuration
	 * settings, initializes the WebDriver based on the specified browser, and
	 * navigates to the base URL.
	 */
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		// Create a new reporter instance for each test
		this.reporter = new ExtentReportLogger();
		// Read baseURL from config file
		String baseURL = ConfigReader.get("baseURL");
		// Read browser and headless mode from system properties or config file
		String browser = System.getProperty("browser", ConfigReader.get("browser"));
		Boolean headless = Boolean.parseBoolean(System.getProperty("headless", ConfigReader.get("headless")));
		Long implicitWait = Long.parseLong(ConfigReader.get("implicitWait"));
		this.explicitWaitInSeconds = Long.parseLong(ConfigReader.get("explicitWait"));

		WebDriver localDriver;

		// Log the browser and headless mode being used
		logger.info("Test setup initiated with browser: {}, headless: {}, implicitWait: {}, explicitWait: {}", browser, headless,
				implicitWait, this.explicitWaitInSeconds);

		switch (browser.toLowerCase()) {
		case "firefox":
			localDriver = new FirefoxDriver();
			logger.info("Launching browser: {}", browser);
			break;
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			if (headless) {
				edgeOptions.addArguments("--headless=new");
				edgeOptions.addArguments("--disable-gpu");
				edgeOptions.addArguments("--disable-dev-shm-usage");
				edgeOptions.addArguments("--window-size=1920,1080"); // Set window size for headless mode
				logger.info("Launching browser in headless mode: {}", browser);
			} else {
				logger.info("Launching browser: {}", browser);
			}
			localDriver = new EdgeDriver(edgeOptions);
			break;
		default:
			ChromeOptions chromeOptions = new ChromeOptions();
			if (headless) {
				chromeOptions.addArguments("--headless=new");
				chromeOptions.addArguments("--disable-gpu");
				chromeOptions.addArguments("--disable-dev-shm-usage");
				chromeOptions.addArguments("--window-size=1920,1080"); // Set window size for headless mode
				logger.info("Launching browser in headless mode: {}", browser);
			} else {
				logger.info("Launching browser: {}", browser);
			}
			localDriver = new ChromeDriver(chromeOptions);
		}

		driverThreadLocal.set(localDriver);
		logger.info("ThreadLocal WebDriver set for current thread: {}", Thread.currentThread().threadId());
		
		// --- Centralized Health Check ---
        // Dynamically get the name of the subclass (e.g., "LoginTest")
		String context = this.getClass().getSimpleName() + ".setup()";
		
		// Validate WebDriver and configuration
		FrameworkHealthChecker.validateDriver(getDriver(), context);
		FrameworkHealthChecker.validateConfig(ConfigReader.getProps(), context, "browser", "baseURL");
		
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		logger.info("Implicit wait set to {} seconds", implicitWait);
		getDriver().get(baseURL);
		logger.info("Current driver URL: {}", getDriver().getCurrentUrl());
	}

	/**
	 * Captures a screenshot of the current browser window. The screenshot is saved
	 * in the "screenshots" directory with a filename based on the method name and
	 * current timestamp.
	 * 
	 * @param methodName The name of the test method, used to create a unique
	 *                   filename for the screenshot
	 * @return The path to the saved screenshot file, or null if the capture failed
	 */
	public String captureScreenshot(String methodName) {
		WebDriver driver = getDriver();
		if (driver == null) {
			logger.warn("Screenshot skipped: driver is null for {}", methodName);
			return null;
		}

		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String path = System.getProperty("user.dir") + "/screenshots/" + methodName + "_"
					+ System.currentTimeMillis() + ".png";
			Files.copy(src.toPath(), new File(path).toPath());
			logger.info("📸 Screenshot captured for method '{}': {}", methodName, path);
			return path;
		} catch (IOException | WebDriverException e) {
			System.err.println("❌ Screenshot capture failed: " + e.getMessage());
			logger.error("Screenshot capture failed for method {}: {}", methodName, e.getMessage());
			return null;
		}
	}

	/**
	 * Tears down the WebDriver after each test method. It quits the WebDriver and
	 * removes the ThreadLocal reference to ensure proper cleanup.
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (getDriver() != null) {
			getDriver().quit();
			driverThreadLocal.remove(); // Clean up the ThreadLocal reference
			logger.info("WebDriver for thread {} has been quit and removed from ThreadLocal.",
					Thread.currentThread().threadId());
		}
	}
}