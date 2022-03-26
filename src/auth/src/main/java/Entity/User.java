package Entity;

public class User {
	
	private String username;
	private String password;
	private String mail;
	
	public User(String username, String password, String mail) {
		this.username = username;
		this.password = password;
		this.mail = mail;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setUsername(String username) {
		if (username == null) {
			throw new AssertionError("Null username");
		}
		this.username = username;
	}
	
	public void setPassword(String password) {
		if (password == null) {
			throw new AssertionError("Null password");
		}
		this.password = password;
	}
	
	public void setMail(String mail) {
		if (mail == null) {
			throw new AssertionError("Null mail");
		}
		this.mail = mail;
	}
	
}
