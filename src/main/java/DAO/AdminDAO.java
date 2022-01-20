package DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DBUtils.Database;
import DBUtils.PasswordChief;
import model.Admin;

// Administator Data Access Object
public class AdminDAO {
	
	private static final String EMAIL_REGEX =
			"^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
	
	private static final String TABLE_NAME		= "Admin";
	
	private static final String ID_FIELD 		= "AdminId";
	private static final String USERNAME_FIELD	= "AdminName";
	private static final String EMAIL_FIELD 	= "AdminMail";
	private static final String PASSWORD_FIELD	= "AdminPassword";
	private static final String SALT_FIELD		= "AdminSalt";
	
	// SQL request to get an admin from DB by email
	private static final String GET_REQUEST =
			"SELECT * FROM " + TABLE_NAME
			+ " WHERE " + EMAIL_FIELD + " = ?;";
	
	// SQL request to insert the admin if it doesn't already exists
	private static final String INSERT_REQUEST =
			"INSERT INTO " + TABLE_NAME + "("
			+ USERNAME_FIELD + "," + EMAIL_FIELD + ","
			+ PASSWORD_FIELD + "," + SALT_FIELD
			+ ") VALUES(?, ?, ?, ?);";
	
	// SQL request to update an admin attributes, not really fine, update all fields
	private static final String UPDATE_REQUEST =
			"UPDATE " + TABLE_NAME
			+ " SET " + USERNAME_FIELD + " = ?,"
			+ EMAIL_FIELD + " = ?,"
			+ PASSWORD_FIELD + " = ?, "
			+ SALT_FIELD + " = ?"
			+ " WHERE " + ID_FIELD + " = ?;";
	
	// SQL request to delete an admin
	private static final String DELETE_REQUEST =
			"DELETE FROM " + TABLE_NAME
			+ " WHERE " + ID_FIELD + " = ?;";
	
	public static Admin get(String email) {
		checkEmail(email);
		Admin admin = null;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_REQUEST);
			// bind the parameter
			query.setString(1, email);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// Admin exists in the DB -> create object
				admin = new Admin(res.getString(USERNAME_FIELD),
						res.getString(EMAIL_FIELD), res.getString(PASSWORD_FIELD));
				admin.setID(res.getInt(ID_FIELD));
				admin.setSalt(res.getString(SALT_FIELD));
				// Get list of maintainers from the maintainer DAO
				List<Integer> list = MaintainerDAO.getList(admin.getID());
				if (list == null) {
					admin = null;
				} else {
					admin.setMaintainersList(list);
				}
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
		if (admin == null) {
			throw new AssertionError("Invalid parameters");
		}
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create the first prepared statement
			PreparedStatement query = c.prepareStatement(GET_REQUEST);
			// bind the parameter
			query.setString(1, admin.getEmail());
			// execute first query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// A user already exists
				throw new AssertionError("This email is already in the database");
			}
			// User doesn't exists -> compute salt and password for storage
			admin.setSalt(PasswordChief.generateSalt());
			admin.setPassword(PasswordChief.cookPassword(admin.getSalt(),
					admin.getPassword()));

			// Create the second prepared statement
			query = c.prepareStatement(INSERT_REQUEST);
			// bind parameters
			query.setString(1, admin.getUsername());
			query.setString(2, admin.getEmail());
			query.setString(3, admin.getPassword());
			query.setString(4, admin.getSalt());
			// execute second query
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to insert user");
		}
	}
	
	public static void update(Admin admin) {
		if (admin == null) {
			throw new AssertionError("Invalid parameters");
		}
		// Update password and salt in case user change his password
		// If it's not the case, just change salt -> no problem
		admin.setSalt(PasswordChief.generateSalt());
		admin.setPassword(PasswordChief.cookPassword(admin.getSalt(),
				admin.getPassword()));
		
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(UPDATE_REQUEST);
			// bind parameters
			query.setString(1, admin.getUsername());
			query.setString(2, admin.getEmail());
			query.setString(3, admin.getPassword());
			query.setString(4, admin.getSalt());
			query.setInt(5, admin.getID());
			// execute the query
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update user");
		}
	}
	
	public static void delete(Admin admin) {
		if (admin == null) {
			throw new AssertionError("Invalid parameters");
		}
		
		int affectedRows = 0;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(DELETE_REQUEST);
			// bind the parameter
			query.setInt(1, admin.getID());
			// execute the query
			affectedRows = query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete user " + admin.getID());
		}
		
		if (affectedRows != 1) {
			throw new RuntimeException("Failed to delete user " + admin.getID());
		}
	}
	
	// Private methods
	
	private static void checkEmail(String email) {
		Pattern pat = Pattern.compile(EMAIL_REGEX);
		if (email == null || !pat.matcher(email).matches()) {
			throw new AssertionError("Invalid email !");
		}
	}
}
