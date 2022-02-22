package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;
import model.Software;

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
    
    // getter
    
    public static Software get(int id) {
    	Software software = null;
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_SOFTWARE);
			// bind the parameter
			query.setInt(1, id);
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
    
    public static void insert(Software software) {
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(INSERT_SOFTWARE);
			// bind the parameter
			query.setString(1, software.getName());
			query.setString(2, software.getDesc());
			// execute the query
			query.executeQuery();
			// close connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public static void updateSoftwareName(Software software) {
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
		}
    }
    
    public static void updateSoftwareDesc(Software software) {
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_SOFTWARE_DESC);
			// bind the parameter
			query.setInt(2, software.getId());
			query.setString(1, software.getDesc());
			// execute the query
			query.executeQuery();
			// close connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public static void delete(Software software) {
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(DELETE_SOFTWARE);
			// bind the parameter
			query.setInt(1, software.getId());
			// execute the query
			query.executeQuery();
			// close connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
}
