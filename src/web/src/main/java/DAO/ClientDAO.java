package DAO;

import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import Utils.Database;

// Client Data Access Object
public class ClientDAO {
	
	// constants
	
	private static String TABLE_NAME = "Client";
	
	private static String ID_FIELD = TABLE_NAME + "Id";
	private static String USERNAME_FIELD = TABLE_NAME + "Username";
	private static String EMAIL_FIELD = TABLE_NAME + "Email";
	
	// SQL requests
	
	private static String GET_CLIENT_BY_MAIL = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_FIELD + " = ?;";

    private static String GET_CLIENT_BY_ID   = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_FIELD + " = ?;";
	
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
	
	private static String GET_CLIENT_LIST = "SELECT * FROM " + TABLE_NAME + ";";
	
	// methods

	/**
	 * Try to retrieve a client object filled with the data from the database
	 * identified by the client's email.
	 * 
	 * @param email of the client to retrieve
	 * @return null on error, a client object on success
	 * @throws AssertionError
	 */	
	public static Client get(String email) {
		checkEmail(email);
		Client client = null;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_CLIENT_BY_MAIL);
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

	/**
	 * Try to retrieve a client object filled with the data from the database
	 * identified by the client's clientId.
	 * 
	 * @param clientId of the client to retrieve
	 * @return null on error, a client object on success
	 */
	public static Client get(int clientId) {
		Client client = null;
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_CLIENT_BY_ID);
			// bind the parameter
			query.setInt(1, clientId);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// Client exists in the DB -> create object
				client = new Client(res.getString(USERNAME_FIELD),
						res.getString(EMAIL_FIELD));
                client.setId(res.getInt(ID_FIELD));
			}			
            // close the connection
			c.close();
		} catch (SQLException e) {
			client = null;
			e.printStackTrace();
		}
		return client;
	}
	
	/**
	 * Return a list of all the clients present in the database 
	 * @return the list of all clients on success, null otherwise
	 */
	public static List<Client> list() {
		List<Client> li = new ArrayList<Client>();
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_CLIENT_LIST);
			// execute the query
			ResultSet res = query.executeQuery();
			while (res.next()) {
				Client client = new Client(res.getString(USERNAME_FIELD),
						res.getString(EMAIL_FIELD));
                client.setId(res.getInt(ID_FIELD));
                
                li.add(client);
			}
			// close connection
			c.close();
		} catch (SQLException e) {
			li = null;
			e.printStackTrace();
		}
		return li;
	}
	
	/**
	 * Insert the client object data into the database, throw an error on failure or
	 * if arguments supplied were incorrect
	 * 
	 * @param client object to insert
	 * @throws AssertionError, RuntimeException
	 */
	public static void insert(Client client) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create the first prepared statement
			PreparedStatement query = c.prepareStatement(GET_CLIENT_BY_MAIL);
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

	/**
	 * Update the client email from the database, throw an error on failure or if
	 * arguments supplied were incorrect
	 * 
	 * @param client object to update
	 * @throws AssertionError, RuntimeException
	 */	
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

    /**
	 * Update the client username from the database, throw an error on failure or if
	 * arguments supplied were incorrect
	 * 
	 * @param client object to update
	 * @throws RuntimeException
	 */	
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

    /**
	 * delete the client data from the database, throw an error on failure or if
	 * arguments supplied were incorrect
	 * 
	 * @param client object to delete
	 * @throws RuntimeException
	 */
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
	/**
	 * Check if it's a well formatted email.
	 * 
	 * @param email a email to verify
	 * @throws AssertionError
	 */	
	private static void checkEmail(String email) {
		Pattern pat = Pattern.compile(EMAIL_REGEX);
		if (email == null || !pat.matcher(email).matches()) {
			throw new AssertionError("Invalid email !");
		}
	}
	
}
