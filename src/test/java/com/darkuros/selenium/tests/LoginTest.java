package com.darkuros.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;

public class LoginTest extends BaseTest {

	@Test
	public void LoginWithValidCredentials() {
		loginPage.enterUserEmail("dark@uros.com");
		loginPage.enterUserPassword("123@Dark");
		loginPage.clickSubmitButton();
		Assert.assertTrue(
				wait.until(ExpectedConditions.urlToBe("https://rahulshettyacademy.com/client/dashboard/dash")));
	}

	@Test
	public void LoginWithInvalidUserEmail() {
		loginPage.enterUserEmail("light@uros.com");
		loginPage.enterUserPassword("123@Dark");
		loginPage.clickSubmitButton();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("overlay-container")));
		Assert.assertEquals(driver.findElement(By.id("toast-container")).getText().trim(),
				"Incorrect email or password.");
	}

	@Test
	public void LoginWithInvalidPassword() {
		loginPage.enterUserEmail("dark@uros.com");
		loginPage.enterUserPassword("123@light");
		loginPage.clickSubmitButton();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("overlay-container")));
		Assert.assertEquals(driver.findElement(By.id("toast-container")).getText().trim(),
				"Incorrect email or password.");
	}

	@Test
	public void LoginWithInvalidCredentials() {
		loginPage.enterUserEmail("light@uros.com");
		loginPage.enterUserPassword("123@light");
		loginPage.clickSubmitButton();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("overlay-container")));
		Assert.assertEquals(driver.findElement(By.id("toast-container")).getText().trim(),
				"Incorrect email or password.");
	}

	@Test
	public void isForgotPasswordLinkWorking() {
		loginPage.navigateToForgotPasswordLink();
		Assert.assertEquals(driver.getCurrentUrl(), "https://rahulshettyacademy.com/client/auth/password-new");
	}
	
	@Test
	public void isRegisterLinkWorking() {
		loginPage.navigateToRegisterLink();
		Assert.assertEquals(driver.getCurrentUrl(), "https://rahulshettyacademy.com/client/auth/register");
	}

}
