package DAO;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import DBUtils.Database;

import model.Anomaly;

// Anomaly Data Access Object
public class AnomalyDAO {

	private static final String TABLE_NAME = "Anomaly";

	private static final String ID_FIELD 			= "AnomalyId";
	private static final String DESCRIPTION_FIELD 	= "AnomalyDesc";
	private static final String STATUS_FIELD 		= "AnomalyStatus";
	private static final String RESSOURCE_ID_FIELD 	= "AnomalyRes";
	private static final String DECL_DATE_FIELD 	= "AnomalyStartDate";
	private static final String RESOLV_DATE_FIELD 	= "AnomalyEndDate";

	private static final String GET_REQUEST =
			"SELECT * FROM " + TABLE_NAME
			+ " WHERE " + ID_FIELD + " = ?;";
	
	private static final String GET_LIST =
			"SELECT * FROM " + TABLE_NAME
			+ " WHERE " + RESSOURCE_ID_FIELD + " = ?;";
	
	private static final String INSERT_REQUEST =
			"INSERT INTO " + TABLE_NAME
			+ "(" + DESCRIPTION_FIELD + "," + STATUS_FIELD + ","
			+ RESSOURCE_ID_FIELD + "," + DECL_DATE_FIELD
			+ ") VALUES(?, ?, ?, ?);";
	
	private static final String UPDATE_REQUEST =
			"UPDATE " + TABLE_NAME + " SET "
			+ DESCRIPTION_FIELD + " = ?,"
			+ STATUS_FIELD + " = ?,"
			+ RESSOURCE_ID_FIELD + " = ?";
	
	private static final String DELETE_REQUEST =
			"DELETE FROM " + TABLE_NAME +
			" WHERE " + ID_FIELD + " = ?;";
	
	public static Anomaly get(int anomalyId) {
		Anomaly anomaly = null;

		try {
			// Get connection from database
			Connection c = Database.getConnection();
			// Create a prepared statement
			PreparedStatement query = c.prepareStatement(GET_REQUEST);
			// bind the parameter
			query.setInt(1, anomalyId);
			// execute the query
			ResultSet res = query.executeQuery();
			if (res.next()) {
				anomaly = new Anomaly(res.getString(DESCRIPTION_FIELD),
						res.getInt(RESSOURCE_ID_FIELD));
				anomaly.setID(anomalyId);
				anomaly.setStatus(res.getInt(STATUS_FIELD));
				// get SQL format date
				anomaly.setDeclarationDate(res.getDate(DECL_DATE_FIELD));
				anomaly.setResolutionDate(res.getDate(RESOLV_DATE_FIELD));
			}
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			anomaly = null;
		}
		return anomaly;
	}

	public static List<Integer> getList(int ressourceId) {
		List<Integer> l = new ArrayList<Integer>();
		try {
			Connection c = Database.getConnection();
			PreparedStatement query = c.prepareStatement(GET_LIST);
			query.setInt(1, ressourceId);
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
	
	public static List<Integer> getLastResolvedList(int count) {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
				+ RESOLV_DATE_FIELD + " IS NOT NULL "
				+"ORDER BY " + RESOLV_DATE_FIELD + " DESC " 
				+ "LIMIT " + count + ";";
		
		List<Integer> l = new ArrayList<Integer>();
		try {
			ResultSet res = Database.execQuery(query);
			while (res.next()) {
				l.add(res.getInt(ID_FIELD));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			l = null;
		}
		return l;
	}

	public static void insert(Anomaly anomaly) {
		if (anomaly == null) {
			throw new AssertionError("Invalid parameters");
		}
		
		java.util.Date javaDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
        
		try {
			Connection c = Database.getConnection();
			PreparedStatement query = c.prepareStatement(INSERT_REQUEST);
			
			query.setString(1, anomaly.getDescription());
			query.setInt(2, anomaly.getStatus());
			query.setInt(3, anomaly.getRessource());
			query.setDate(4, sqlDate);
			
			query.executeUpdate();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to insert anomaly");
		}
	}

	public static void update(Anomaly anomaly) {
		if (anomaly == null) {
			throw new AssertionError("Invalid parameters");
		}
		
		String resolved = "";
		if (anomaly.isResolved()) {
			resolved = "," + RESOLV_DATE_FIELD + " = ?";
		}

		String req = UPDATE_REQUEST	+ resolved
				+ " WHERE " + ID_FIELD + " = ?;";
		try {
			Connection c = Database.getConnection();
			PreparedStatement query = c.prepareStatement(req);
			
			query.setString(1, anomaly.getDescription());
			query.setInt(2, anomaly.getStatus());
			query.setInt(3, anomaly.getRessource());
			if (anomaly.isResolved()) {
				// set resolution date
				java.util.Date javaDate = new java.util.Date();
		        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
				query.setDate(4, sqlDate);
				query.setInt(5, anomaly.getID());
			} else {
				query.setInt(4, anomaly.getID());
			}
			query.executeUpdate();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update anomaly");
		}
	}

	public static void delete(Anomaly anomaly) {
		if (anomaly == null) {
			throw new AssertionError("Invalid parameters");
		}

		int affectedRows = 0;
		try {
			Connection c = Database.getConnection();
			PreparedStatement query = c.prepareStatement(DELETE_REQUEST);
			
			query.setInt(1, anomaly.getID());
			
			affectedRows = query.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete anomaly " + anomaly.getID());
		}
		if (affectedRows != 1) {
			throw new RuntimeException("Failed to delete anomaly " + anomaly.getID());
		}
	}
}
