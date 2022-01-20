package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import DBUtils.Database;
import DBUtils.PasswordChief;
import model.Maintainer;

// Maintainer Data Access Object
public class MaintainerDAO {
	
	private static final String EMAIL_REGEX =
			"^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
	
	private static final String TABLE_NAME		= "Maintainer";
	
	private static final String ID_FIELD 		= "MainId";
	private static final String USERNAME_FIELD	= "MainName";
	private static final String EMAIL_FIELD 	= "MainMail";
	private static final String PASSWORD_FIELD	= "MainPassword";
	private static final String SALT_FIELD		= "MainSalt";
	private static final String STATUS_FIELD 	= "MainStatus";
	
	// SQL request to get a maintainer from DB by email
	private static final String GET_BY_EMAIL =
			"SELECT * FROM " + TABLE_NAME
			+ " WHERE " + EMAIL_FIELD + " = ?;";
	
	// SQL request to get a maintainer from DB by id
	private static final String GET_BY_ID =
			"SELECT * FROM " + TABLE_NAME
			+ " WHERE " + ID_FIELD + " = ?;";
	
	// SQL request to get list of maintainers
	private static final String GET_LIST =
			"SELECT " + ID_FIELD + " FROM " + TABLE_NAME;
	
	// SQL request to insert the maintainer if it doesn't already exists
	private static final String INSERT_REQUEST =
			"INSERT INTO " + TABLE_NAME + "("
			+ USERNAME_FIELD + "," + EMAIL_FIELD + ","
			+ PASSWORD_FIELD + "," + SALT_FIELD + ","
			+ STATUS_FIELD
			+ ") VALUES(?, ?, ?, ?, ?);";
	
	// SQL request to delete a maintainer
	private static final String DELETE_REQUEST =
			"DELETE FROM " + TABLE_NAME
			+ " WHERE " + ID_FIELD + " = ?;";

	public static Maintainer get(String email) {
		checkEmail(email);
		Maintainer m = null;
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_BY_EMAIL);
			// bind the parameter
			query.setString(1, email);
			m = privGet(query);
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return m;
	}
	
	public static Maintainer get(int maintainerID) {
		Maintainer m = null;
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_BY_ID);
			// bind the parameter
			query.setInt(1, maintainerID);
			m = privGet(query);
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public static List<Integer> getList(int adminID) {
		// Try to execute the request
		List<Integer> list = new ArrayList<Integer>();
		try {
			ResultSet res = Database.execQuery(GET_LIST);
			while (res.next()) {
				list.add(res.getInt(ID_FIELD));
			}
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	public static void insert(Maintainer maintainer) {
		if (maintainer == null) {
			throw new AssertionError("Invalid parameters");
		}
		// Try to execute both requests
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create the first prepared statement
			PreparedStatement query = c.prepareStatement(GET_BY_EMAIL);
			// bind the parameter
			query.setString(1, maintainer.getEmail());
			// execute first query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// A user already exists
				throw new AssertionError("This email is already in the database");
			}
			// User doesn't exists -> compute salt and password for storage
			maintainer.setSalt(PasswordChief.generateSalt());
			maintainer.setPassword(PasswordChief.cookPassword(maintainer.getSalt(),
					maintainer.getPassword()));
			
			// Create the second prepared statement
			query = c.prepareStatement(INSERT_REQUEST);
			// bind parameters
			query.setString(1, maintainer.getUsername());
			query.setString(2, maintainer.getEmail());
			query.setString(3, maintainer.getPassword());
			query.setString(4, maintainer.getSalt());
			query.setInt(5, maintainer.getStatus());
			// execute second query
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static void update(Maintainer maintainer) {
		if (maintainer == null) {
			throw new AssertionError("Invalid parameters");
		}
		
		// SQL request to update a maintainer attributes
		String req = "UPDATE " + TABLE_NAME
				+ " SET " + USERNAME_FIELD + " = ?,"
				+ EMAIL_FIELD + " = ?,";
				
		// check if user change it's password
		boolean passwordChanged = false;
		Maintainer maintainerRef = get(maintainer.getID());
		if (maintainerRef != null) {
			if (PasswordChief.verifyPassword(maintainerRef.getPassword(), maintainerRef.getSalt(), 
					maintainer.getPassword())) {
				// user change it's password -> update it
				maintainer.setSalt(PasswordChief.generateSalt());
				maintainer.setPassword(PasswordChief.cookPassword(maintainer.getSalt(),
						maintainer.getPassword()));
				passwordChanged = true;
				// update the query too
				req += PASSWORD_FIELD + " = ?,"
					  +  SALT_FIELD + " = ?,";
			}
		}
		req += STATUS_FIELD + " = ?"
		      +  " WHERE " + ID_FIELD + " = ?;";
		
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create the first prepared statement
			PreparedStatement query = c.prepareStatement(req);
			// bind the parameters
			query.setString(1, maintainer.getUsername());
			query.setString(2, maintainer.getEmail());
			if (passwordChanged) {
				query.setString(3, maintainer.getPassword());
				query.setString(4, maintainer.getSalt());
				query.setInt(5, maintainer.getStatus());
				query.setInt(6, maintainer.getID());
			} else {
				query.setInt(3, maintainer.getStatus());
				query.setInt(4, maintainer.getID());
			}
			// execute the query
			query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update user");
		}
	}
	
	public static void delete(Maintainer maintainer) {
		if (maintainer == null) {
			throw new AssertionError("Invalid parameters");
		}
		
		// first we need to delete all the ressource of the maintainer
		List<Integer> list = RessourceDAO.getList(maintainer.getID());
		for (Integer id : list) {
			RessourceDAO.delete(RessourceDAO.get(id));
		}

		int affectedRows = 0;
		// Try to execute the request
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create the prepared statement
			PreparedStatement query = c.prepareStatement(DELETE_REQUEST);
			// bind the parameter
			query.setInt(1, maintainer.getID());
			// execute query
			affectedRows = query.executeUpdate();
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete user " + maintainer.getID());
		}
		
		if (affectedRows != 1) {
			throw new RuntimeException("Failed to delete user " + maintainer.getID());
		}
	}
	
	// Private methods
	
	private static void checkEmail(String email) {
		Pattern pat = Pattern.compile(EMAIL_REGEX);
		if (email == null || !pat.matcher(email).matches()) {
			throw new AssertionError("Invalid email !");
		}
	}
	
	private static Maintainer privGet(PreparedStatement query) {
		// Try to execute the request
		Maintainer maintainer = null;
		try {
			ResultSet res = query.executeQuery();
			if (res.next()) {
				// Maintainer exists in the DB -> create object
				maintainer = new Maintainer(res.getString(USERNAME_FIELD),
						res.getString(EMAIL_FIELD), res.getString(PASSWORD_FIELD));
				
				maintainer.setID(res.getInt(ID_FIELD));
				maintainer.setSalt(res.getString(SALT_FIELD));
				maintainer.setStatus(res.getInt(STATUS_FIELD));
				// Get list of ressources from the ressources DAO
				if (maintainer.isActive()) {
					List<Integer> list = RessourceDAO.getList(maintainer.getID());
					if (list == null) {
						maintainer = null;
					} else {
						maintainer.setRessourcesList(list);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			maintainer = null;
		}
		return maintainer;
	}
}
