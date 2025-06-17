package com.darkuros.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.SetupAndTearDown;
import com.darkuros.selenium.pageobjects.LoginPage;


public class LoginTest extends SetupAndTearDown {

	@Test
	public void LoginWithValidCredentials() {
		// Create an object of LoginPage class
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginToApplication("dark@uros.com", "123@Dark");
		Assert.assertTrue(wait.until(ExpectedConditions.urlToBe("https://rahulshettyacademy.com/client/dashboard/dash")));
	}
	
	@Test
	public void LoginWithInvalidCredentials() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginToApplication("dark@dark.com", "123@Dark");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("overlay-container")));
		Assert.assertEquals(driver.findElement(By.id("toast-container")).getText().trim(), "Incorrect email or password.");
	}

}
