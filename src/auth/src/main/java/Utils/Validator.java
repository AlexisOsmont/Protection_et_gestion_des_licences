package Utils;
import java.util.regex.Pattern;

public class Validator {
	
	private static final String USERNAME_REGEX = null;
	//TODO: USERNAME_REGEX
	
	// Regular expression to check the strength of password
	private static final String REGEXP =
			"^(?=.*[A-Z])"		// at least one uppercase between A-Z
			+ "(?=.*[!@#$&*])"	// at least one special character
			+ "(?=.*[0-9])"		// at least one number
			+ "(?=.*[a-z])"		// at least one lowercase between a-z
			+ ".{8,}$";		// at least 8 characters
	
	private static final String EMAIL_REGEX =
			"^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
	
	public static void checkUsername(String password) {
		//TODO: checkUsername
	}
	
	// Check if password if strong enough.
	public static void checkPasswordStrength(String password) {
		Pattern p = Pattern.compile(REGEXP);
		if (password == null || !p.matcher(password).matches()) {
			throw new AssertionError("Mot de passe invalide. "
					+ "Sont requis : 8 Caractères minimum dont 1 Majuscule, 1 Minuscule, 1 Caractère spécial !@#$&* et 1 chiffre.");
		}
	}
	
	public static void checkEmail(String email) {
		Pattern pat = Pattern.compile(EMAIL_REGEX);
		if (email == null || !pat.matcher(email).matches()) {
			throw new AssertionError("Adresse Email non valide.");
		}
	}
	
}