package DAO;

import model.Ressource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBUtils.Database;

// Ressource Data Access Object
public class RessourceDAO {

	private static final String TABLE_NAME = "Ressource";

	private static final String ID_FIELD 			= "ResId";
	private static final String DESCRIPTION_FIELD 	= "ResDesc";
	private static final String LOCATION_FIELD 		= "ResLocation";
	private static final String MAINTAINER_ID_FIELD = "ResMaintainer";
	
	private static final String GET_REQUEST =
			"SELECT * FROM " + TABLE_NAME + " WHERE " 
			+ ID_FIELD + " = ?;";

	private static final String GET_LIST =
			"SELECT * FROM " + TABLE_NAME + " WHERE " 
			+ MAINTAINER_ID_FIELD + " = ?;";
	
	private static final String INSERT_REQUEST =
			"INSERT INTO " + TABLE_NAME
			+ "(" + DESCRIPTION_FIELD + "," + LOCATION_FIELD + ","
			+ MAINTAINER_ID_FIELD + ") VALUES(?, ?, ?);";
	
	private static final String UPDATE_REQUEST =
			"UPDATE " + TABLE_NAME + " SET "
			+ DESCRIPTION_FIELD + " = ?,"
			+ LOCATION_FIELD + " = ?,"
			+ MAINTAINER_ID_FIELD + " = ?"
			+ " WHERE " + ID_FIELD + " = ?;";
	
	private static final String DELETE_REQUEST =
			"DELETE FROM " + TABLE_NAME
			+ " WHERE " + ID_FIELD + " = ?;";

	public static Ressource get(int ressourceId) {
		Ressource ressource = null;
		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_REQUEST);
			// bind the parameter
			query.setInt(1, ressourceId);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				ressource = new Ressource(res.getString(LOCATION_FIELD),
						res.getString(DESCRIPTION_FIELD),
						res.getInt(MAINTAINER_ID_FIELD));
				ressource.setID(ressourceId);
				// Get list of anomaly from the anomaly DAO
				List<Integer> list = AnomalyDAO.getList(ressourceId);
				if (list == null) {
					ressource = null;
				} else {
					ressource.setAnomalyList(list);
				}
			}
			// close the connection
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ressource = null;
		}
		return ressource;
	}

	public static List<Integer> getList(int MaintainerId) {
		List<Integer> l = new ArrayList<Integer>();
		try {
			Connection c = Database.getConnection();
			PreparedStatement query = c.prepareStatement(GET_LIST);
			query.setInt(1, MaintainerId);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				l.add(res.getInt(ID_FIELD));
			}
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			l = null;
		}
		return l;
	}

	public static void insert(Ressource ressource) {
		if (ressource == null) {
			throw new AssertionError("Invalid parameters");
		}
		
		try {
			Connection c = Database.getConnection();
			PreparedStatement query = c.prepareStatement(INSERT_REQUEST);
			
			query.setString(1, ressource.getDescription());
			query.setString(2, ressource.getLocation());
			query.setInt(3, ressource.getMaintainer());
			
			query.executeUpdate();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to insert ressource");
		}
	}

	public static void update(Ressource ressource) {
		if (ressource == null) {
			throw new AssertionError("Invalid parameters");
		}

		try {
			Connection c = Database.getConnection();
			PreparedStatement query = c.prepareStatement(UPDATE_REQUEST);
			
			query.setString(1, ressource.getDescription());
			query.setString(2, ressource.getLocation());
			query.setInt(3, ressource.getMaintainer());
			query.setInt(4, ressource.getID());
			
			query.executeUpdate();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update ressource");
		}
	}

	public static void delete(Ressource ressource) {
		if (ressource == null) {
			throw new AssertionError("Invalid parameters");
		}

		// first we need to delete all the anomaly of the ressource
		List<Integer> list = AnomalyDAO.getList(ressource.getID());
		for (Integer id : list) {
			AnomalyDAO.delete(AnomalyDAO.get(id));
		}

		int affectedRows = 0;
		try {
			Connection c = Database.getConnection();
			PreparedStatement query = c.prepareStatement(DELETE_REQUEST);
			
			query.setInt(1, ressource.getID());
			
			affectedRows = query.executeUpdate();
			c.close();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to delete ressource " + ressource.getID());
		}
		
		// unsure that we modify the rows
		if (affectedRows != 1) {
			throw new RuntimeException("Failed to delete ressource " + ressource.getID());	
		}
	}
}
