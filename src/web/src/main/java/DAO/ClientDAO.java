package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import database.Database;
import model.Client;

public class ClientDAO {
	
	// constants
	
	private static String TABLE_NAME = "Client";
	
	private static String ID_FIELD = TABLE_NAME + "Id";
	private static String USERNAME_FIELD = TABLE_NAME + "Username";
	private static String EMAIL_FIELD = TABLE_NAME + "Email";
	
	// SQL requests
	
	private static String GET_CLIENT = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_FIELD + " = ?;";
	
	private static String INSERT_CLIENT = "INSERT INTO " + TABLE_NAME
										+ "(" + USERNAME_FIELD + ", " + EMAIL_FIELD + ")"
										+ " VALUES (?, ?);";
	
	private static String UPDATE_EMAIL_CLIENT = "UPDATE " + TABLE_NAME
										+ " SET " + EMAIL_FIELD + " = ?"
										+ " WHERE " + ID_FIELD + " = ?;";
	
	private static String UPDATE_USERNAME_CLIENT = "UPDATE " + TABLE_NAME
										+ " SET " + USERNAME_FIELD + " = ?"
										+ " WHERE " + ID_FIELD + " = ?;";
	
	private static String DELETE_CLIENT = "DELETE FROM " + TABLE_NAME
										+ " WHERE " + ID_FIELD + " = ?;";
	
	// getters
	
	public static Client get(String email) {
		checkEmail(email);
		Client client = null;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_CLIENT);
			// bind the parameter
			query.setString(1, email);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// Client exists in the DB -> create object
				client = new Client(res.getString(USERNAME_FIELD),
						res.getString(EMAIL_FIELD));
                client.setId(res.getInt(ID_FIELD));
			}
			// close connection
			c.close();
		} catch (SQLException e) {
			client = null;
			e.printStackTrace();
		}
		return client;
	}
	
	public static void insert(Client client) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create the first prepared statement
			PreparedStatement query = c.prepareStatement(GET_CLIENT);
			// bind the parameter
			query.setString(1, client.getEmail());
			// execute first query (test)
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// A user already exists
				throw new AssertionError("This email is already in the database");
			}

			// Create the second prepared statement
			query = c.prepareStatement(INSERT_CLIENT);
			// bind parameters
			query.setString(1, client.getUsername());
			query.setString(2, client.getEmail());
			// execute second query (insert)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to insert user");
		}
	}
	
	public static void updateEmail(Client client)  {
        checkEmail(client.getEmail());
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_EMAIL_CLIENT);
			// bind parameters
			query.setString(1, client.getEmail());
			query.setInt(2, client.getId());
			// execute the query (update)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update user's email");
		}
	}
	
	public static void updateUsername(Client client) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_USERNAME_CLIENT);
			// bind parameters
			query.setString(1, client.getUsername());
			query.setInt(2, client.getId());
			// execute the query (update)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update user's username");
		}
	}
	
	public static void delete(Client client) {
		int affectedRows = 0;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(DELETE_CLIENT);
			// bind the parameter
			query.setInt(1, client.getId());
			// execute the query
			affectedRows = query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete user " + client.getId());
		}
		
		if (affectedRows != 1) {
			throw new RuntimeException("Failed to delete user " + client.getId());
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
