package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utils.Database;
import Utils.Validator;
import Entity.User;

public class UserModel {
	
	private static final String INSERT_QUERY = "INSERT INTO users (username, email, password) VALUES (?, ?, ?);";		
	private static final String DELETE_QUERY = "DELETE FROM users WHERE username = ? AND email = ?;";

	private static final String UPDATE_MAIL= "UPDATE users SET email = ? WHERE username = ?;";
	private static final String UPDATE_USERNAME = "UPDATE users SET username = ? WHERE mail = ?;";
	private static final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE username = ?;";
	
	private static final String CHECK_USERNAME = "SELECT username FROM users WHERE username = ?;";
	private static final String CHECK_MAIL= "SELECT email FROM users WHERE email = ?;";
	private static final String GET_BY_MAIL= "SELECT * FROM users WHERE email = ?;";

					
	public static User getUserByMail(String mail) throws SQLException {
		Validator.checkEmail(mail);
		User user = null;
		// Get connection from database
		Connection c = Database.getConnection();
		// Create a prepared statement
		PreparedStatement query = c.prepareStatement(GET_BY_MAIL);
		// bind the parameter
		query.setString(1, mail);
		// execute the query
		ResultSet res = query.executeQuery();
		if (res.next()) {
			// User exists in the DB -> create object
			user = new User(res.getString("username"),
					res.getString("password"), res.getString("email"));
		}
		// close connection
		c.close();
		return user;
	}
	
	public static void insertUser(User user) throws SQLException {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create the second prepared statement
			PreparedStatement query = c.prepareStatement(INSERT_QUERY);
			// bind parameters
			query.setString(1, user.getUsername());
			query.setString(2, user.getMail());
			query.setString(3, user.getPassword());
			// execute second query (insert)
			query.executeUpdate();
			// close the connection
			c.close();
	}
	
	public static void deleteUser(User user) {
		int affectedRows = 0;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(DELETE_QUERY);
			// bind the parameter
			query.setString(1, user.getUsername());
			query.setString(2, user.getMail());
			// execute the query
			affectedRows = query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete user " + user.getUsername());
		}
		
		if (affectedRows != 1) {
			throw new RuntimeException("Failed to delete user " + user.getUsername());
		}
	}
	
	public static void updateMail(User user)  {
        Validator.checkEmail(user.getMail());
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_MAIL);
			// bind parameters
			query.setString(1, user.getMail());
			query.setString(2, user.getUsername());
			// execute the query (update)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update user's email");
		}
	}
	
	public static void updateUsername(User user) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_USERNAME);
			// bind parameters
			query.setString(1, user.getUsername());
			query.setString(2, user.getMail());
			// execute the query (update)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update user's username");
		}
	}
	
	public static void updatePassword(User user) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_PASSWORD);
			// bind parameters
			query.setString(1, user.getPassword());
			query.setString(2, user.getUsername());
			// execute the query (update)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update user's username");
		}
	}
	
	public static void checkUserAlreadyExist(User user) throws SQLException {
		if (user == null) {
			throw new AssertionError("Null user");
		}
		
		// Get connection from database
		Connection c = Database.getConnection();				
			
		// Test if username is taken
		PreparedStatement query = c.prepareStatement(CHECK_USERNAME);
		query.setString(1, user.getUsername());
		ResultSet set = query.executeQuery();
		if (set.next()) {
			throw new SQLException("Le nom d'utilisateur n'est pas disponible.");
		}
		
		// Test if mail is taken
		query = c.prepareStatement(CHECK_MAIL);
		query.setString(1, user.getMail());
		set = query.executeQuery();
		if (set.next()) {
			// A user with the same mail address already exists
			throw new SQLException("L'adresse mail n'est pas disponible.");
		}
		
		// close the connection
		c.close();
	}
}