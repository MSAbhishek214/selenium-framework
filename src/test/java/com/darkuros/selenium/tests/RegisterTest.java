package com.darkuros.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.darkuros.selenium.base.BaseTest;
import com.darkuros.selenium.pageobjects.LoginPage;
import com.darkuros.selenium.pageobjects.RegisterPage;

public class RegisterTest extends BaseTest {

	private LoginPage loginPage;

	// This @BeforeMethod will run before EACH @Test method in THIS class
	// (LoginTest)
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		loginPage = new LoginPage(driver);
	}

	@Test
	public void clickRegisterWithEmptyForm() {
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.clickRegisterButton();

		/*
		 * Assertions for submitting an empty form Make sure to offload all wait
		 * conditions and locator strategies to its respective PO file
		 */
		Assert.assertEquals(registerPage.getFirstNameEmptyErrorText(), "*First Name is required");
		Assert.assertEquals(registerPage.getEmailRequiredErrorText(), "*Email is required");
		Assert.assertEquals(registerPage.getPhoneRequiredErrorText(), "*Phone Number is required");
		Assert.assertEquals(registerPage.getPasswordRequiredErrorText(), "*Password is required");
		Assert.assertEquals(registerPage.getConfirmPasswordRequiredErrorText(), "Confirm Password is required");
		if (!registerPage.isCheckboxSelected()) {
			Assert.assertEquals(registerPage.getCheckboxErrorText(), "*Please check above checkbox");
		}
	}

	@Test
	public void registerUserWithValidDetails() {
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.registerUser("tad", "packer", "tad@packer.comdd", "9876987645", "Student", "Male", "Password@1");

		Assert.assertEquals(registerPage.getRegisterSuccessfulToast(), "Registered Successfully");
		Assert.assertEquals(registerPage.getSuccessMessageAfterRegister(), "Account Created Successfully");
	}

	@Test
	public void registerUserWithOnlyRequiredFields() {
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.registerUser("troy", "baker", "troy@bakers.com", "9878978978", "", "", "Password@1");

		Assert.assertEquals(registerPage.getRegisterSuccessfulToast(), "Registered Successfully");
		Assert.assertEquals(registerPage.getSuccessMessageAfterRegister(), "Account Created Successfully");
	}

	@Test
	public void passwordLessThan8Chars() {
		RegisterPage registerPage = loginPage.navigateToRegisterLink();
		registerPage.registerUser("tad", "packer", "tad@packer.comds", "9876987645", "Student", "Male", "Pass@1");
		
		Assert.assertEquals(registerPage.getMessagePasswordLessThan8Chars(), "Password must be 8 Character Long!");
	}

}
