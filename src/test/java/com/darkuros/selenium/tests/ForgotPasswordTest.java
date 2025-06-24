package com.darkuros.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;

public class ForgotPasswordTest extends BaseTest {

	@Test
	public void saveNewPasswordWithValidCredentials() {
		loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "123@Dark", "123@Dark");
		// Explicitly wait
		wait.until(ExpectedConditions.urlToBe("https://rahulshettyacademy.com/client/auth/login"));
		// Assertions
		Assert.assertEquals(driver.getCurrentUrl(), "https://rahulshettyacademy.com/client/auth/login");
		Assert.assertEquals(loginPage.getPasswordChangeSuccessfulText(), "Password Changed Successfully");
	}

	@Test
	public void invalidEmail() {
		loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("com", "123@Dark", "123@Dark");
		// Assertions
		Assert.assertEquals(
				driver.findElement(By.cssSelector("input[formcontrolname='userEmail'] + div")).getText().trim(),
				"*Enter Valid Email");
	}

	@Test
	public void emptyEmail() {
		loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("", "123@Dark", "123@Dark");
		// Assertions
		Assert.assertEquals(
				driver.findElement(By.cssSelector("input[formcontrolname='userEmail'] + div")).getText().trim(),
				"*Email is required");
	}

	@Test
	public void emptyPassword() {
		loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "", "123@Dark");
		// Assertions
		Assert.assertEquals(driver.findElement(By.cssSelector("#userPassword + div")).getText().trim(),
				"*Password is required");
		Assert.assertEquals(driver.findElement(By.cssSelector("#confirmPassword + div")).getText().trim(),
				"Password and Confirm Password must match with each other.");

	}

	@Test
	public void emptyConfirmPassword() {
		loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "123@Dark", "");
		// Assertions
		Assert.assertEquals(driver.findElement(By.cssSelector("#confirmPassword + div")).getText().trim(),
				"*Confirm Password is required");
	}

	@Test
	public void emptyPasswordAndConfirmPassword() {
		loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "", "");
		// Assertions
		Assert.assertEquals(driver.findElement(By.cssSelector("#userPassword + div")).getText().trim(),
				"*Password is required");
		Assert.assertEquals(driver.findElement(By.cssSelector("#confirmPassword + div")).getText().trim(),
				"*Confirm Password is required");
	}

	@Test
	public void passwordsDontMatch() {
		loginPage.navigateToForgotPasswordLink();
		forgotPasswordPage.fillSavePasswordFormAndSubmit("dark@uros.com", "abc", "bca");
		// Assertions
		Assert.assertEquals(driver.findElement(By.cssSelector("#confirmPassword + div")).getText().trim(),
				"Password and Confirm Password must match with each other.");
	}

}
