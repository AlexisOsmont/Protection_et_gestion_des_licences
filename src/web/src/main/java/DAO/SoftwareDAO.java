package DAO;

import model.Software;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.Database;

// Software Data Access Object
public class SoftwareDAO {
	
	// constants
	
	private static String TABLE_NAME  	   = "Software";
	private static String ID_FIELD   	   = TABLE_NAME + "Id";
	private static String NAME_FIELD  	   = TABLE_NAME + "Name";
	private static String DESC_FIELD  	   = TABLE_NAME + "Desc";
	private static String PRICE_FIELD 	   = TABLE_NAME + "Price";
	private static String PRICE_MULT_FIELD = TABLE_NAME + "PriceMultiplier";
	private static String IMAGE_FIELD      = TABLE_NAME + "Img";
	
	// SQL requests
    private static String GET_SOFTWARE = "SELECT * FROM " 
    								   + TABLE_NAME + " WHERE " 
    								   + ID_FIELD + " = ?;";
    
    private static String GET_SOFTWARE_LIST = "SELECT * FROM " 
    										+ TABLE_NAME + ";";
    
    private static String INSERT_SOFTWARE = "INSERT INTO " + TABLE_NAME 
    									  + "(" + NAME_FIELD + ", " + DESC_FIELD 
    									  + ", " + PRICE_FIELD + ", " + PRICE_MULT_FIELD 
    									  + ", " + IMAGE_FIELD + ")" 
    									  + " VALUES (?, ?, ?, ?, ?);";
    
    private static String UPDATE_SOFTWARE_NAME = "UPDATE " + TABLE_NAME 
    										   + " SET " + NAME_FIELD + " = ?" 
    										   + " WHERE " + ID_FIELD + " = ?;";
    
    private static String UPDATE_SOFTWARE_DESC = "UPDATE " + TABLE_NAME 
    										   + " SET " + DESC_FIELD + " = ?" 
    										   + " WHERE " + ID_FIELD + " = ?;";
    
    private static String UPDATE_SOFTWARE_PRICE = "UPDATE " + TABLE_NAME 
    											+ " SET " + PRICE_FIELD + " = ?"
    											+ " WHERE " + ID_FIELD + " = ?;";
    
    private static String UPDATE_SOFTWARE_PRICE_MULT = "UPDATE " + TABLE_NAME 
													 + " SET " + PRICE_MULT_FIELD + " = ?"
													 + " WHERE " + ID_FIELD + " = ?;";
    
    private static String UPDATE_SOFTWARE_IMAGE = "UPDATE " + TABLE_NAME 
												+ " SET " + IMAGE_FIELD + " = ?"
												+ " WHERE " + ID_FIELD + " = ?;";
    
    private static String DELETE_SOFTWARE = "DELETE FROM " + TABLE_NAME 
    									  + " WHERE " + ID_FIELD + " = ?;";
    
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
				software = new Software(res.getString(NAME_FIELD),
						res.getString(DESC_FIELD));
                software.setId(res.getInt(ID_FIELD));
                software.setPrice(res.getInt(PRICE_FIELD));
                software.setPriceMultiplier(res.getInt(PRICE_MULT_FIELD));
                
                byte[] imgData = null;
                Blob image = res.getBlob(IMAGE_FIELD);
                if (image != null) {
                	imgData = image.getBytes(1,(int)image.length());
                }
                software.setImg(imgData);
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
     * Create a list of all the software present in the database
     * @return the list
     */
    public static List<Software> list() {
    	List<Software> li = new ArrayList<Software>();
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_SOFTWARE_LIST);
			// execute the query
			ResultSet res = query.executeQuery();
			while (res.next()) {
				Software software = new Software(res.getString(NAME_FIELD),
										res.getString(DESC_FIELD));
                software.setId(res.getInt(ID_FIELD));
                software.setPrice(res.getInt(PRICE_FIELD));
                software.setPriceMultiplier(res.getInt(PRICE_MULT_FIELD));
                
                byte[] imgData = null;
                Blob image = res.getBlob(IMAGE_FIELD);
                if (image != null) {
                	imgData = image.getBytes(1,(int)image.length());
                }
                software.setImg(imgData);
                li.add(software);
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
			query.setInt(3, software.getPrice());
			query.setInt(4, software.getPriceMultiplier());
			byte[] data = software.getImg();
			if (data == null) {
				// inserting null value in a blob column type isn't allowed
				// insert an empty string instead
				data = "".getBytes();
			}
			InputStream in = new ByteArrayInputStream(data);
			query.setBinaryStream(5, in, data.length);

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
     * Update the software price from the database, throw an error on failure or if
	 * arguments supplied were incorrect
	 * 
	 * @param software object to update
	 * @throws RuntimeException
     */
    public static void updatePrice(Software software) {
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_SOFTWARE_PRICE);
			// bind the parameter
			query.setInt(2, software.getId());
			query.setInt(1, software.getPrice());
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
     * Update the software price multiplier from the database, 
     * throw an error on failure or if arguments supplied were incorrect
	 * 
	 * @param software object to update
	 * @throws RuntimeException
     */
    public static void updatePriceMultiplier(Software software) {
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_SOFTWARE_PRICE_MULT);
			// bind the parameter
			query.setInt(2, software.getId());
			query.setInt(1, software.getPriceMultiplier());
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
     * Update the software image data from the database, 
     * throw an error on failure or if arguments supplied were incorrect
	 * 
	 * @param software object to update
	 * @throws RuntimeException
     */
    public static void updateImage(Software software) {
    	// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_SOFTWARE_IMAGE);
			
			byte[] data = software.getImg();
			if (data == null) {
				// inserting null value in a blob column type isn't allowed
				// insert an empty string instead
				data = "".getBytes();
			}
			InputStream in = new ByteArrayInputStream(data);
			
			// bind the parameter
			query.setInt(2, software.getId());
			query.setBinaryStream(1, in, data.length);
			
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
