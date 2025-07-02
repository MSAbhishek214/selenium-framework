package com.darkuros.selenium.models;

public class UserTestData {
	// This class represents a user with various attributes for testing purposes.
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String phoneNumber;
	private final String occupation;
	private final String gender;
	private final String password;

	public UserTestData(String firstName, String lastName, String email, String phoneNumber, String occupation,
			String gender, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.occupation = occupation;
		this.gender = gender;
		this.password = password;
	}

	// Getters only (if immutable), or with setters if needed
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getGender() {
		return gender;
	}

	public String getPassword() {
		return password;
	}
}