package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import Database.Database;
import Entity.User;

public class UserModel {
		
		private static String GET_QUERY= "SELECT * FROM auth WHERE email = ?;";
		private static String INSERT_QUERY = "INSERT INTO auth (username, email, password) VALUES (?, ?, ?);";		
		private static String DELETE_QUERY = "DELETE FROM auth WHERE username = ?;";

		private static String UPDATE_MAIL= "UPDATE auth SET  email = ? WHERE mail = ?;";
		private static String UPDATE_USERNAME = "UPDATE auth SET username = ? WHERE username = ?;";
		private static String UPDATE_PASSWORD = "UPDATE auth SET password = ? WHERE username = ?;";
						
		public static User getUserByMail(String mail) {
			checkEmail(mail);
			User user = null;
			// Try to execute the request
			try {
				// Get connection from database
				Connection c = Database.getConnection();
				// Create a prepared statement
				PreparedStatement query = c.prepareStatement(GET_QUERY);
				// bind the parameter
				query.setString(1, mail);
				// execute the query
				ResultSet res = query.executeQuery();
				if (res.next()) {
					// User exists in the DB -> create object
					user = new User(Integer.parseInt(res.getString("id")), res.getString("username"),
							res.getString("mail"), res.getString("password"));
				}
				// close connection
				c.close();
			} catch (SQLException e) {
				user = null;
				e.printStackTrace();
			}
			return user;
		}
		
		public static void insertUser(User user) {
			// Try to execute the request
			try {
				// Get connection from database
				Connection c = Database.getConnection();
				// Create the first prepared statement
				PreparedStatement query = c.prepareStatement(GET_QUERY);
				// bind the parameter
				query.setString(1, user.getMail());
				// execute first query (test)
				ResultSet res = query.executeQuery();
				if (res.next()) {
					// A user already exists
					throw new AssertionError("This email is already in the database");
				}

				// Create the second prepared statement
				query = c.prepareStatement(INSERT_QUERY);
				// bind parameters
				query.setString(1, user.getUsername());
				query.setString(2, user.getMail());
				query.setString(3, user.getPassword());
				// execute second query (insert)
				query.executeUpdate();
				// close the connection
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to insert user");
			}
		}
		
		public static void delete(User user) {
			int affectedRows = 0;
			// Try to execute the request
			try {
				// Get connection from database
				Connection c = Database.getConnection();
				// Create a prepared statement
				PreparedStatement query = c.prepareStatement(DELETE_QUERY);
				// bind the parameter
				query.setInt(1, user.getId());
				// execute the query
				affectedRows = query.executeUpdate();
				// close the connection
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to delete user " + user.getId());
			}
			
			if (affectedRows != 1) {
				throw new RuntimeException("Failed to delete user " + user.getId());
			}
		}
		
		public static void updateMail(User user)  {
	        checkEmail(user.getMail());
			// Try to execute the request
			try {
				// Get connection from database
				Connection c = Database.getConnection();
				// Create a prepared statement
				PreparedStatement query = c.prepareStatement(UPDATE_MAIL);
				// bind parameters
				query.setString(1, user.getMail());
				query.setInt(2, user.getId());
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
				query.setInt(2, user.getId());
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
				query.setString(1, user.getUsername());
				query.setInt(2, user.getId());
				// execute the query (update)
				query.executeUpdate();
				// close the connection
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to update user's username");
			}
		}
		
		
	// Validator 
	// TODO: Faire une classe validator
		
		private static final String EMAIL_REGEX =
				"^[a-zA-Z0-9_+&*-]+(?:\\."+
	            "[a-zA-Z0-9_+&*-]+)*@" +
	            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
	            "A-Z]{2,7}$";
		
		private static final String USERNAME_REGEX = null;
		//TODO: USERNAME_REGEX
		
		private static final String PASSWORD_REGEX = null;
		//TODO: PASSWORD_REGEX
		
		public static void checkEmail(String email) {
			Pattern pat = Pattern.compile(EMAIL_REGEX);
			if (email == null || !pat.matcher(email).matches()) {
				throw new AssertionError("Invalid email !");
			}
		}
		
		public static void checkUsername(String password) {
			//TODO: checkUsername
		}
		
		public static void checkPassword(String password) {
			//TODO: checkPassword
		}
}