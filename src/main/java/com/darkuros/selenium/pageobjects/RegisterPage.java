package com.darkuros.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage {

	// Class level driver
	WebDriver driver;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Username field locator
	@FindBy(id = "firstName")
	WebElement firstName;

	@FindBy(id = "lastName")
	WebElement lastName;

	@FindBy(id = "userEmail")
	WebElement email;

	@FindBy(id = "userMobile")
	WebElement phoneNumber;

	@FindBy(css = ".custom-select")
	WebElement occupationDropdown;

	Select occupation = new Select(occupationDropdown);

	@FindBy(css = "input[value='Male']")
	WebElement genderMale;

	@FindBy(css = "input[value='Female']")
	WebElement genderFemale;

	@FindBy(id = "userPassword")
	WebElement password;

	@FindBy(id = "confirmPassword")
	WebElement confirmPassword;

	@FindBy(css = ".col-md-1 input")
	WebElement ageCheckBox;

	@FindBy(id = "login")
	WebElement registerButton;

	@FindBy(css = ".text-reset")
	WebElement navigateToLogin;

	// Provide details of the new user and register
	public void registerUser(String firstName, String lastName, String email, String phoneNumber, String password) {
		this.firstName.sendKeys(firstName);
		this.lastName.sendKeys(lastName);
		this.email.sendKeys(email);
		this.phoneNumber.sendKeys(phoneNumber);
		this.occupation.selectByValue("Student");
		this.genderMale.click();
		this.password.sendKeys(password);
		this.confirmPassword.sendKeys(password);
		this.ageCheckBox.click();
		this.registerButton.click();
	}

}
