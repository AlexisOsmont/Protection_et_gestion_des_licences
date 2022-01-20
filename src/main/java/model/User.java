package model;

import java.util.regex.Pattern;

import DBUtils.PasswordChief;

/*
 * Modèle des utilisateurs : classe parentes des administrateurs et des
 * responsables de maintenance.
 * Les utilisateurs possèdent un identifiant, un nom, un email, un mot de
 * passe et un sel pour stocker son mot de passe. Pour chacun de ces attributs
 * un getter et un setter est défini.
 */
public class User {
	
	// Regular expression to check validity of emails
	private static final String EMAIL_REGEX =
			"^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
	
	// Attributes
	
	private int id;
	private String username;
	private String email;
	private String password;
	private String salt;
	
	// Constructor
	
	public User(String username, String email, String password) {
		checkEmail(email);
		// done in PasswordChief
		// checkPassword(password);
		this.id = -1;
		this.username = username;
		this.email = email;
		this.password = password;
		this.salt = null;
	}

	// Getters

	public int getID() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getSalt() {
		return salt;
	}
	
	// Setters
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setUsername(String newName) {
		this.username = newName;
	}
	
	public void setEmail(String email) {
		checkEmail(email);
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	// Utils functions
	
	public boolean matchPassword(String password) {
		return PasswordChief.verifyPassword(this.password,
				this.salt, password);
	}
	
	// Private methods
	
	/*
	 * Check if it's a well formatted email.
	 */
	private void checkEmail(String email) {
		Pattern pat = Pattern.compile(EMAIL_REGEX);
		if (email == null || !pat.matcher(email).matches()) {
			throw new AssertionError("Invalid email !");
		}
	}
	
//	private void checkPassword(String password) {
//		if (password == null || !PasswordChief.checkPasswordStrength(password)) {
//			throw new AssertionError("Invalid password !");
//		}
//	}
//	
}

