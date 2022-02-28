package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utils.Database;
import Utils.Validator;
import Entity.User;

public class UserModel {
		
		private static final String GET_QUERY= "SELECT * FROM users WHERE email = ?;";
		private static final String INSERT_QUERY = "INSERT INTO users (username, email, password) VALUES (?, ?, ?);";		
		private static final String DELETE_QUERY = "DELETE FROM users WHERE username = ? AND mail = ?;";

		private static final String UPDATE_MAIL= "UPDATE users SET mail = ? WHERE username = ?;";
		private static final String UPDATE_USERNAME = "UPDATE users SET username = ? WHERE mail = ?;";
		private static final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE username = ?;";
		
		private static final String COUNT_ATTRIBUTE = "SELECT username FROM users WHERE ? = ?;";
						
		public static User getUserByMail(String mail) {
			Validator.checkEmail(mail);
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
					user = new User(res.getString("username"),
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
		
		public static void insertUser(User user) throws SQLException {
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
							
			PreparedStatement query = c.prepareStatement(COUNT_ATTRIBUTE);
			
			// Test if username is taken
			query.setString(1, "username");
			query.setString(2, user.getUsername());
			ResultSet set = query.executeQuery();
			if (set.next()) {
				throw new SQLException("Le nom d'utilisateur n'est pas disponible.");
			}
			
			
			query = c.prepareStatement(COUNT_ATTRIBUTE);
			
			// Test if mail is taken
			query.setString(1, "mail");
			query.setString(2, user.getMail());
			set = query.executeQuery();
			if (set.next()) {
				throw new SQLException("L'addresse mail n'est pas disponible.");
			}
			
			// close the connection
			c.close();
		}
	
}