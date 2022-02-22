package model;

public class User {
	
	// attributes
    
    private int id;
    private String username;
    private String mail;
    
    // constructor
    
    public User(int id, String username, String mail) {
        this.id = id;
        this.username = username;
        this.mail = mail;
    }
    
    // getters
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    // setters
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return mail;
    }
    
    public void setEmail(String mail) {
        this.mail = mail;
    }
}
