package com.darkuros.selenium.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.darkuros.selenium.models.UserTestData;

public final class DataGeneratorUtils {

	private static final String[] FIRST_NAMES = { "Aryan", "Meera", "Rohan", "Priya", "Karan", "Sneha", "Ayaan",
			"Ritika", "Dev", "Anjali" };

	private static final String[] LAST_NAMES = { "Sharma", "Patel", "Mehta", "Desai", "Iyer", "Verma", "Kumar", "Singh",
			"Reddy", "Chowdhury" };

	private DataGeneratorUtils() {
	}

	public static String generateFirstName() {
		return FIRST_NAMES[ThreadLocalRandom.current().nextInt(FIRST_NAMES.length)];
	}

	public static String generateLastName() {
		return LAST_NAMES[ThreadLocalRandom.current().nextInt(LAST_NAMES.length)];
	}

	public static String generateEmailFromName(String firstName, String lastName) {
		return (generateFirstName() + "." + generateLastName() + UUID.randomUUID().toString().substring(0, 4))
				.toLowerCase() + "@test.com";
	}
	
	public static UserTestData generateTestUser() {
	    String first = generateFirstName();
	    String last = generateLastName();
	    String email = generateEmailFromName(first, last);
	    return new UserTestData(
	        first,
	        last,
	        email,
	        "9876987645",
	        "Student",
	        "Male",
	        "Password@1"
	    );
	}

}