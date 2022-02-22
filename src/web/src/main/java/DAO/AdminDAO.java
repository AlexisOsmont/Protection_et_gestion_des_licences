package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import database.Database;
import model.Admin;

public class AdminDAO {
	
	// constants
	
	private static String TABLE_NAME = "Admin";
	
	private static String ID_FIELD 			= TABLE_NAME + "Id";
	private static String USERNAME_FIELD 	= TABLE_NAME + "Username";
	private static String EMAIL_FIELD 		= TABLE_NAME + "Email";
	
	// SQL requests
	
	private static String GET_ADMIN = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_FIELD + " = ?;";
	
	private static String INSERT_ADMIN = "INSERT INTO " + TABLE_NAME
										+ "(" + USERNAME_FIELD + ", " + EMAIL_FIELD + ")"
										+ " VALUES (?, ?);";
	
	private static String UPDATE_EMAIL_ADMIN = "UPDATE " + TABLE_NAME
										+ " SET " + EMAIL_FIELD + " = ?"
										+ " WHERE " + ID_FIELD + " = ?;";
	
	private static String UPDATE_USERNAME_ADMIN = "UPDATE " + TABLE_NAME
										+ " SET " + USERNAME_FIELD + " = ?"
										+ " WHERE " + ID_FIELD + " = ?;";
	
	private static String DELETE_ADMIN = "DELETE FROM " + TABLE_NAME
										+ " WHERE " + ID_FIELD + " = ?;";
	
	// methods
	
	public static Admin get(String email) {
		checkEmail(email);
		Admin admin = null;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_ADMIN);
			// bind the parameter
			query.setString(1, email);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// Admin exists in the DB -> create object
				admin = new Admin(res.getString(USERNAME_FIELD),
						res.getString(EMAIL_FIELD));
                admin.setId(res.getInt(ID_FIELD));
			}
			// close connection
			c.close();
		} catch (SQLException e) {
			admin = null;
			e.printStackTrace();
		}
		return admin;
	}
	
	public static void insert(Admin admin) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create the first prepared statement
			PreparedStatement query = c.prepareStatement(GET_ADMIN);
			// bind the parameter
			query.setString(1, admin.getEmail());
			// execute first query (test)
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// A user already exists
				throw new AssertionError("This email is already in the database");
			}

			// Create the second prepared statement
			query = c.prepareStatement(INSERT_ADMIN);
			// bind parameters
			query.setString(1, admin.getUsername());
			query.setString(2, admin.getEmail());
			// execute second query (insert)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to insert user");
		}
	}
	
	public static void updateEmail(Admin admin) {
        checkEmail(admin.getEmail());
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_EMAIL_ADMIN);
			// bind parameters
			query.setString(1, admin.getEmail());
			query.setInt(2, admin.getId());
			// execute the query (update)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update user's email");
		}
	}
	
	public static void updateUsername(Admin admin) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_USERNAME_ADMIN);
			// bind parameters
			query.setString(1, admin.getUsername());
			query.setInt(2, admin.getId());
			// execute the query (update)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update user's username");
		}
	}
	
	public static void delete(Admin admin) {
		int affectedRows = 0;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(DELETE_ADMIN);
			// bind the parameter
			query.setInt(1, admin.getId());
			// execute the query
			affectedRows = query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete user " + admin.getId());
		}
		
		if (affectedRows != 1) {
			throw new RuntimeException("Failed to delete user " + admin.getId());
		}
	}
	
	// Private methods
	
	private  static final String EMAIL_REGEX =
			"^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
	
	private static void checkEmail(String email) {
		Pattern pat = Pattern.compile(EMAIL_REGEX);
		if (email == null || !pat.matcher(email).matches()) {
			throw new AssertionError("Invalid email !");
		}
	}
	
}
