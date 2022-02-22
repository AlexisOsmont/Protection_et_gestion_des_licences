package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;
import model.Licence;

public class LicenceDAO {
	
	// constants
	
	private static String TABLE_NAME = "Licence";
	
	private static String ID_FIELD 		= TABLE_NAME + "Id";
	private static String HW_ID_FIELD 	= TABLE_NAME + "HardwareId";
	private static String STATUS_FIELD 	= TABLE_NAME + "Status";
	private static String VALID_FIELD 	= TABLE_NAME + "Validity";
	private static String CL_ID_FIELD 	= "ClientId";
	private static String SW_ID_FIELD	= "SoftwareId";
	
	// SQL requests
	
	// get licence by client and software ids
	private static String GET_LICENCE = "SELECT * FROM " + TABLE_NAME
							+ " WHERE " + CL_ID_FIELD + " = ?"
							+ " AND " + SW_ID_FIELD + " = ?;";
	
	private static String INSERT_LICENCE = "INSERT INTO " + TABLE_NAME
							+ "(" + HW_ID_FIELD + ", " + STATUS_FIELD + ", " + VALID_FIELD + ", "
							+ CL_ID_FIELD + ", " + SW_ID_FIELD + ")"
							+ " VALUES (?, ?, ?, ?, ?);";
	
	private static String UPDATE_STATUS = "UPDATE " + TABLE_NAME
							+ " SET " + STATUS_FIELD + " = ?"
							+ " WHERE " + ID_FIELD + " = ?;";
	
	private static String UPDATE_VALIDITY = "UPDATE " + TABLE_NAME
							+ " SET " + VALID_FIELD + " = ?"
							+ " WHERE " + ID_FIELD + " = ?;";
	
	private static String DELETE_LICENCE = "DELETE FROM " + TABLE_NAME
							+ " WHERE " + ID_FIELD + " = ?;";
	
	// methods
	
	public static Licence get(int clientId, int softwareId) {
		Licence licence = null;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_LICENCE);
			// bind parameters
			query.setInt(1, clientId);
			query.setInt(2, softwareId);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// Licence exists in the DB -> create object
				licence = new Licence(res.getInt(ID_FIELD),
						res.getString(HW_ID_FIELD),
						res.getInt(STATUS_FIELD),
						new java.util.Date(res.getDate(VALID_FIELD).getTime()),
						res.getInt(CL_ID_FIELD),
						res.getInt(SW_ID_FIELD));
			}
			// close connection
			c.close();
		} catch (SQLException e) {
			licence = null;
			e.printStackTrace();
		}
		return licence;
	}
	
	public static void insert(Licence licence) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create the first prepared statement
			PreparedStatement query = c.prepareStatement(GET_LICENCE);
			// bind parameters
			query.setInt(1, licence.getClientId());
			query.setInt(2, licence.getSoftwareId());
			// execute first query (test)
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// A user already exists
				throw new AssertionError("The client has already a licence for this software");
			}

			// Create the second prepared statement
			query = c.prepareStatement(INSERT_LICENCE);
			// bind parameters
			query.setString(1, licence.getLicenceHardwareId());
			query.setInt(2, licence.getLicenceStatus());
			query.setDate(3, new Date(licence.getLicenceValidity().getTime()));
			query.setInt(4, licence.getClientId());
			query.setInt(5, licence.getSoftwareId());
			// execute second query (insert)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to insert this licence");
		}
	}
	
	public static void updateStatus(Licence licence) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_STATUS);
			// bind parameters
			query.setInt(1, licence.getLicenceStatus());
			query.setInt(2, licence.getId());
			// execute the query (update)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update licence's status");
		}
	}
	
	public static void updateValidity(Licence licence) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_VALIDITY);
			// bind parameters
			query.setDate(1, new Date(licence.getLicenceValidity().getTime()));
			query.setInt(2, licence.getId());
			// execute the query (update)
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update licence's validity");
		}
	}
	
	public static void delete(Licence licence) {
		int affectedRows = 0;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(DELETE_LICENCE);
			// bind the parameter
			query.setInt(1, licence.getId());
			// execute the query
			affectedRows = query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete licence : " + licence.getId());
		}
		
		if (affectedRows != 1) {
			throw new RuntimeException("Failed to delete licence : " + licence.getId());
		}
	}
}
