package Utils;
import java.util.regex.Pattern;

public class Validator {
	
	private static final String USERNAME_REGEX = "^[A-Za-z0-9]{0,25}$";
	//TODO: USERNAME_REGEX
	
	// Regular expression to check the strength of password
	private static final String PASS_REGEXP =
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
	
	private static final String TICKET_REGEX = "^[A-Za-z0-9]{0,25}$";
	//TODO: TICKET_REGEXP
	
	public static void checkUsername(String username) throws AssertionError {
		Pattern pat = Pattern.compile(USERNAME_REGEX);
		if (username == null || !pat.matcher(username).matches()) {
			throw new AssertionError("Nom d'utilisateur non valide. "
					+ "Sont requis au maximum 25 caratères alphanumériques. - et _ sont aussi autorisés");
		}
	}
	
	// Check if password if strong enough.
	public static void checkPasswordStrength(String password) throws AssertionError {
		Pattern p = Pattern.compile(PASS_REGEXP);
		if (password == null || !p.matcher(password).matches()) {
			throw new AssertionError("Mot de passe invalide. "
					+ "Sont requis au minimum 8 caractères dont 1 Majuscule, 1 Minuscule, 1 Caractère spécial !@#$&* et 1 chiffre.");
		}
	}
	
	public static void checkEmail(String email) throws AssertionError {
		Pattern pat = Pattern.compile(EMAIL_REGEX);
		if (email == null || !pat.matcher(email).matches()) {
			throw new AssertionError("Adresse Email non valide.");
		}
	}

	public static void checkTicket(String hash) throws AssertionError {
		Pattern pat = Pattern.compile(TICKET_REGEX);
		if (hash == null || !pat.matcher(hash).matches()) {
			throw new AssertionError("Ticket non valide.");
		}
	}
	
}