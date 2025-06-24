package com.darkuros.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;

public class RegisterTest extends BaseTest {

	@Test
	public void clickRegisterWithEmptyForm() {
		loginPage.navigateToRegisterLink().clickRegisterButton();
		// Assertions for empty form
		Assert.assertEquals(driver.findElement(By.cssSelector("#firstName + div")).getText().trim(),
				"*First Name is required");
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ng-tns-c4-7"))));
		Assert.assertEquals(driver.findElement(By.cssSelector(".ng-tns-c4-7")).getText().trim(),
				"Last Name is required!");
		Assert.assertEquals(driver.findElement(By.cssSelector("#userEmail+ div")).getText().trim(),
				"*Email is required");
		Assert.assertEquals(driver.findElement(By.cssSelector("#userMobile + div")).getText().trim(),
				"*Phone Number is required");
		Assert.assertEquals(driver.findElement(By.cssSelector("#userPassword + div")).getText().trim(),
				"*Password is required");
		Assert.assertEquals(driver.findElement(By.cssSelector("#confirmPassword + div")).getText().trim(),
				"Confirm Password is required");
		if (!driver.findElement(By.cssSelector(".col-md-1 input[type='checkbox']")).isSelected()) {
			Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'*Please check above checkbox')]"))
					.getText().trim(), "*Please check above checkbox");
		}
	}

	@Test
	public void registerUserWithValidDetails() {
		loginPage.navigateToRegisterLink().registerUser("tad", "packer", "tad@packer.comd", "9876987645", "Student",
				"Male", "Password@1");
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@aria-label='Registered Successfully']")));
		Assert.assertEquals(registerPage.getRegisterSuccessfulToast(), "Registered Successfully");
		Assert.assertEquals(registerPage.getSuccessMessageAfterRegister(), "Account Created Successfully");
	}

	@Test
	public void registerUserWithOnlyRequiredFields() {
		loginPage.navigateToRegisterLink().registerUser("troy", "baker", "troy@baker.com", "9878978978", "", "",
				"Password@1");
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@aria-label='Registered Successfully']")));
		Assert.assertEquals(registerPage.getRegisterSuccessfulToast(), "Registered Successfully");
		Assert.assertEquals(registerPage.getSuccessMessageAfterRegister(), "Account Created Successfully");
	}

	@Test(groups = "debug")
	public void passwordLessThan8Chars() {
		loginPage.navigateToRegisterLink().registerUser("tad", "packer", "tad@packer.comd", "9876987645", "Student",
				"Male", "Pass@1");
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@aria-label='Password must be 8 Character Long!']")));
		Assert.assertEquals(registerPage.getMessagePasswordLessThan8Chars(), "Password must be 8 Character Long!");
	}

}
