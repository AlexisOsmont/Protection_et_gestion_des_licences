package DAO;

import model.Licence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.Database;

// Licence Data Access Model
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
	private static String GET_LICENCE_BY_EXT_ID = "SELECT * FROM " + TABLE_NAME
							+ " WHERE " + CL_ID_FIELD + " = ?"
							+ " AND " + SW_ID_FIELD + " = ?;";

    private static String GET_LICENCE_BY_OWN_ID = "SELECT * FROM " + TABLE_NAME
                            + " WHERE " + ID_FIELD + " = ?;";
    
    private static String GET_LICENCE_LIST = "SELECT * FROM " + TABLE_NAME;
    
    private static String GET_LICENCE_LIST_BY_CLIENT_ID = "SELECT * FROM " + TABLE_NAME
    						+ " WHERE " + CL_ID_FIELD + " = ?;";
 	
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

	/**
	 * Try to retrieve a licence object filled with the data from the database
	 * identified by a clientId and a softwareId
	 * 
	 * @param clientId   of the licence to retrieve
	 * @param softwareId of the licence to retrieve
	 * @return null on error, a licence object on success
	 */	
	public static Licence get(int clientId, int softwareId) {
		Licence licence = null;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_LICENCE_BY_EXT_ID);
			// bind parameters
			query.setInt(1, clientId);
			query.setInt(2, softwareId);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// Licence exists in the DB -> create object
                licence = new Licence(res.getInt(CL_ID_FIELD), res.getInt(SW_ID_FIELD));
                licence.setId(res.getInt(ID_FIELD));
                licence.setStatus(res.getInt(STATUS_FIELD));
                licence.setValidity(new java.util.Date(res.getDate(VALID_FIELD).getTime()));
                licence.setHardwareId(res.getString(HW_ID_FIELD));
			}
			// close connection
			c.close();
		} catch (SQLException e) {
			licence = null;
			e.printStackTrace();
		}
		return licence;
	}

	/**
	 * Try to retrieve a licence object filled with the data from the database
	 * identified by a it's id.
	 * 
	 * @param licenceId of the licence to retrieve
	 * @return null on error, a licence object on success
	 */	
	public static Licence get(int licenceId) {
		Licence licence = null;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_LICENCE_BY_OWN_ID);
			// bind parameters
			query.setInt(1, licenceId);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// Licence exists in the DB -> create object
				licence = new Licence(res.getInt(CL_ID_FIELD), res.getInt(SW_ID_FIELD));
                licence.setId(res.getInt(ID_FIELD));
                licence.setStatus(res.getInt(STATUS_FIELD));
                licence.setValidity(new java.util.Date(res.getDate(VALID_FIELD).getTime()));
                licence.setHardwareId(res.getString(HW_ID_FIELD));
			}
			// close connection
			c.close();
		} catch (SQLException e) {
			licence = null;
			e.printStackTrace();
		}
		return licence;
	}
	
	/**
	 * Return a list of the licence owned by a client, identified by it's id
	 * @param clientId the id of a client
	 * @return a list of licence owned by the client, null on failure 
	 */
	public static List<Licence> list(int clientId) {
		List<Licence> li = new ArrayList<Licence>();
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_LICENCE_LIST_BY_CLIENT_ID);
			// bind parameters
			query.setInt(1, clientId);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				Licence licence = new Licence(res.getInt(CL_ID_FIELD), res.getInt(SW_ID_FIELD));
                licence.setId(res.getInt(ID_FIELD));
                licence.setStatus(res.getInt(STATUS_FIELD));
                licence.setValidity(new java.util.Date(res.getDate(VALID_FIELD).getTime()));
                licence.setHardwareId(res.getString(HW_ID_FIELD));
                
                li.add(licence);
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
	 * Return a list of all the licence present in the database 
	 * @return the list of all licence on success, null otherwise
	 */
	public static List<Licence> list() {
		List<Licence> li = new ArrayList<Licence>();
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_LICENCE_LIST);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				Licence licence = new Licence(res.getInt(CL_ID_FIELD), res.getInt(SW_ID_FIELD));
                licence.setId(res.getInt(ID_FIELD));
                licence.setStatus(res.getInt(STATUS_FIELD));
                licence.setValidity(new java.util.Date(res.getDate(VALID_FIELD).getTime()));
                licence.setHardwareId(res.getString(HW_ID_FIELD));
                
                li.add(licence);
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
	 * Insert the licence object data into the database, throw an error on failure or
	 * if arguments supplied were incorrect
	 * 
	 * @param licence object to insert
	 * @throws AssertionError, RuntimeException
	 */
	public static void insert(Licence licence) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create the first prepared statement
			PreparedStatement query = c.prepareStatement(GET_LICENCE_BY_EXT_ID);
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
			query.setString(1, licence.getHardwareId());
			query.setInt(2, licence.getStatus());
			query.setDate(3, new Date(licence.getValidity().getTime()));
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

    /**
	 * Update the licence status from the database, throw an error on failure or if
	 * arguments supplied were incorrect
	 * 
	 * @param licence object to update
	 * @throws RuntimeException
	 */	
	public static void updateStatus(Licence licence) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_STATUS);
			// bind parameters
			query.setInt(1, licence.getStatus());
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

    /**
	 * Update the licence validy from the database, throw an error on failure or if
	 * arguments supplied were incorrect
	 * 
	 * @param licence object to update
	 * @throws RuntimeException
	 */	
	public static void updateValidity(Licence licence) {
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_VALIDITY);
			// bind parameters
			query.setDate(1, new Date(licence.getValidity().getTime()));
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

    /**
	 * Delete the licence data from the database, throw an error on failure or if
	 * arguments supplied were incorrect
	 * 
	 * @param licence object to delete
	 * @throws RuntimeException
	 */
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
