package DBUtils;

public class Test {
	
	public static void main(String args[]) {
		// String salt = "$2a$10$e5pSu4N1CJIzoZVRrlqh4u";
		String salt = BCrypt.gensalt(12);
		String passwd = BCrypt.hashpw("Password1234@", salt);
	
		// System.out.println(salt);
		System.out.println(salt + " / " + passwd);
	}
}
