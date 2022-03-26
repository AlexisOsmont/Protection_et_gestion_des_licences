package Utils;

import java.security.NoSuchAlgorithmException;

public class Password {
	
	// Verify the password
	public static boolean verifyPassword(String refPassword, String toCheck) {
		return BCrypt.checkpw(toCheck, refPassword);
	}
	
	public static String cookPassword(String salt, String password) throws NoSuchAlgorithmException {
		return BCrypt.hashpw(password, salt);
	}
	
	// Generate salt for storage of user's passwords.
		public static String generateSalt() {
			return BCrypt.gensalt();
		}
}
