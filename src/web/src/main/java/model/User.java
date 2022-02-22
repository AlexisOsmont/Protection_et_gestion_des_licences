package model;

import java.util.regex.Pattern;

public class User {

	// Attributes
	public static final int ID_NOT_SET = -1;
	private int id;
	private String username;
	private String email;

	// Regular expression to check validity of emails
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@"
			+ "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

	public User(String username, String email) {
		checkEmail(email);
		this.id = ID_NOT_SET;
		this.email = email;
		this.username = username;
	}

	// Getters

	/**
	 * return the id of the user object if set, if not return User.ID_NOT_SET
	 * 
	 * @return the id of the user object
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the username of the user object
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the email of the user object
	 */
	public String getEmail() {
		return email;
	}

	// Setters
	/**
	 * Update the id of the user object
	 * 
	 * @param id new id for this user
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Update the username of the user object
	 * 
	 * @param newName the new username for this user
	 */
	public void setUsername(String newName) {
		this.username = newName;
	}

	/**
	 * Update the email of the user object is the email parameters is valid, if not
	 * throw an error
	 * 
	 * @param email a valid email for the user
	 * @throws AssertionError
	 */
	public void setEmail(String email) {
		checkEmail(email);
		this.email = email;
	}

	// Private methods

	/**
	 * Check if it's a well formatted email.
	 * 
	 * @param email a email to verify
	 * @throws AssertionError
	 */
	private void checkEmail(String email) {
		Pattern pat = Pattern.compile(EMAIL_REGEX);
		if (email == null || !pat.matcher(email).matches()) {
			throw new AssertionError("Invalid email !");
		}
	}
}
