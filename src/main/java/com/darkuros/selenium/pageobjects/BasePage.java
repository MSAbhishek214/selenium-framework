package com.darkuros.selenium.pageobjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
	
	protected WebDriver driver; // <<< Driver declared here but lives in BaseTest
	protected WebDriverWait wait;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		// Initialize elements for the specific child Page Object
        // 'this' refers to the concrete Page Object instance (e.g., LoginPage)
        PageFactory.initElements(this.driver, this);
	}
	
	public String getCurrentPageURL() {
		return driver.getCurrentUrl();
	}
	
}
