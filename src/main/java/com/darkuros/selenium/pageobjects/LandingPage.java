package com.darkuros.selenium.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LandingPage extends BasePage {

	public LandingPage(WebDriver driver) {
		super(driver);
	}

	// Get a list of all products in the catalogue page
	@FindBy(css = ".mb-3")
	List<WebElement> products;
	
	public boolean isHomePageDisplayed() {
		return wait.until(ExpectedConditions.urlToBe("https://rahulshettyacademy.com/client/dashboard/dash"));
	}

}
