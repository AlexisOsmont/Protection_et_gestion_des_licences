package Entity;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String mail;
	
	public User(int id, String username, String password, String mail) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.mail = mail;
	}
	
	public int getId() {
		return id;
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
	
}
