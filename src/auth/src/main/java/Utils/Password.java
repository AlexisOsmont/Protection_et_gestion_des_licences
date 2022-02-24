package Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Password {

	// Size of the salt
	private static final int SALT_LENGTH = 32;
	
	
	// Verify the password
	public static boolean verifyPassword(String refPassword, String refSalt,
			String toCheck) {
		String computedPassword = cookPassword(refSalt, toCheck);
		return refPassword.equals(computedPassword);
	}
	
	public static String cookPassword(String salt, String password) {
		String newPassword = null;
		// check for password strength 
		Validator.checkPasswordStrength(password);
		
		try {
			// Create an instance of MessageDigest to hash password
			// with SHA-256
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// Decode salt with base64
			Decoder decoder = Base64.getUrlDecoder();
			// compute hashed password
			md.update(decoder.decode(salt));
			byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
			// Encode into string value with base64
			Encoder encoder = Base64.getUrlEncoder();
			newPassword = encoder.encodeToString(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// return hashed password or null if an error occured
		return newPassword;
	}
	
	// Generate salt for storage of user's passwords.
		public static String generateSalt() {
			// Create a SecureRandom object (Java CSPRNG)
			SecureRandom sr = new SecureRandom();
	    	byte b[] = new byte[SALT_LENGTH];
	    	// Generate next SALT_LENGTH bytes in b
	    	sr.nextBytes(b);
	    	// Encode into string value with base64
	    	Encoder encoder = Base64.getUrlEncoder();
	    	return encoder.encodeToString(b);
		}
}
