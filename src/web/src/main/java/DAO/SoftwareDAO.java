package DAO;

import model.Software;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Utils.Database;

// Software Data Access Object
public class SoftwareDAO {
	
	// constants
	
	private static String TABLE_NAME = "Software";
	private static String ID_FIELD = TABLE_NAME + "Id";
	private static String NAME_FIELD = TABLE_NAME + "Name";
	private static String DESC_FIELD = TABLE_NAME + "Desc";
	
	// SQL requests
    private static String GET_SOFTWARE = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_FIELD + " = ?;";
    private static String INSERT_SOFTWARE = "INSERT INTO " + TABLE_NAME + "(" + NAME_FIELD + ", " + DESC_FIELD + ")" + " VALUES (?, ?);";
    private static String UPDATE_SOFTWARE_NAME = "UPDATE " + TABLE_NAME + " SET " + NAME_FIELD + " = ?" + " WHERE " + ID_FIELD + " = ?;";
    private static String UPDATE_SOFTWARE_DESC = "UPDATE " + TABLE_NAME + " SET " + DESC_FIELD + " = ?" + " WHERE " + ID_FIELD + " = ?;";
    private static String DELETE_SOFTWARE = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_FIELD + " = ?;";
    
    // methods

 	/**
	 * Try to retrieve a software object filled with the data from the database
	 * identified by a it's id.
	 * 
	 * @param softwareId of the software to retrieve
	 * @return null on error, a software object on success
	 */	   
    public static Software get(int softwareId) {
    	Software software = null;
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_SOFTWARE);
			// bind the parameter
			query.setInt(1, softwareId);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// Admin exists in the DB -> create object
				software = new Software(res.getString(NAME_FIELD),
						res.getString(DESC_FIELD));
                software.setId(res.getInt(ID_FIELD));
			}
			// close connection
			c.close();
		} catch (SQLException e) {
			software = null;
			e.printStackTrace();
		}
		return software;
    }

    /**
	 * Insert the software object data into the database, throw an error on failure or
	 * if arguments supplied were incorrect
	 * 
	 * @param software object to insert
	 * @throws RuntimeException
	 */   
    public static void insert(Software software) {
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(INSERT_SOFTWARE);
			// bind the parameter
			query.setString(1, software.getName());
			query.setString(2, software.getDescription());
			// execute the query
			query.executeQuery();
			// close connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to insert this software");
		}
    }

    /**
	 * Update the software name from the database, throw an error on failure or if
	 * arguments supplied were incorrect
	 * 
	 * @param software object to update
	 * @throws RuntimeException
	 */	
    public static void updateName(Software software) {
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_SOFTWARE_NAME);
			// bind the parameter
			query.setInt(2, software.getId());
			query.setString(1, software.getName());
			// execute the query
			query.executeQuery();
			// close connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update this software");
		}
    }

    /**
	 * Update the software description from the database, throw an error on failure or if
	 * arguments supplied were incorrect
	 * 
	 * @param software object to update
	 * @throws RuntimeException
	 */	   
    public static void updateDescription(Software software) {
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_SOFTWARE_DESC);
			// bind the parameter
			query.setInt(2, software.getId());
			query.setString(1, software.getDescription());
			// execute the query
			query.executeQuery();
			// close connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update this software");
		}
    }

    /**
	 * Delete the software data from the database, throw an error on failure or if
	 * arguments supplied were incorrect
	 * 
	 * @param software object to delete
	 * @throws RuntimeException
	 */   
    public static void delete(Software software) {
		int affectedRows = 0;
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(DELETE_SOFTWARE);
			// bind the parameter
			query.setInt(1, software.getId());
			// execute the query
			affectedRows = query.executeUpdate();
			// close connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete this software");
		}

		if (affectedRows != 1) {
			throw new RuntimeException("Failed to delete software : " + software.getId());
		}
    }
	
}
