package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Random;

import Entity.User;
import Utils.Database;

public class TicketsModel {
	
	// Temps d'expiration en seconde.
	private static final long EXPIRACY_TIME = 5;
	
	private static final String INSERT_TICKET = "INSERT INTO tickets (hash, usr, epoch) values (?, (SELECT id FROM users WHERE username = ?), UNIX_TIMESTAMP());";
	private static final String DELETE_TICKET = "DELETE FROM tickets WHERE hash = ?;";
	
	private static final String CHECK_TICKET = "SELECT hash, epoch FROM tickets WHERE hash = ?";
	
	private static final String GET_USER_BY_TICKET= "SELECT * FROM users WHERE id = (SELECT usr FROM tickets WHERE hash = ?);";
	
	private static final int HASH_LEN = 25;
	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	
	/*
	 * newTicket : Generate a new ticket which can authenticate the user
	 * 				The ticket is randomly generated and stored in the database
	 */
	public static String newTicket(User user) throws SQLException {
		String ticket = generateTicketHash();
		// Get connection from database
		Connection c = Database.getConnection();
		// Create the second prepared statement
		PreparedStatement query = c.prepareStatement(INSERT_TICKET);
		// bind parameters
		query.setString(1, ticket);
		query.setString(2, user.getUsername());
		// execute second query (insert)
		query.executeUpdate();
		// close the connection
		c.close();
		return ticket;
	}
	
	/*
	 * getUserWithTicket : Retourne l'utilisateur associé au ticket.
	 * 						Tente d'effacer le ticket en cas de succès de récupération de l'user
	 */
	public static User getUserWithTicket(String hash) throws SQLException {
		User user = null;
		// Get connection from database
		Connection c = Database.getConnection();
		// Create a prepared statement
		PreparedStatement query = c.prepareStatement(GET_USER_BY_TICKET);
		// bind the parameter
		query.setString(1, hash);
		// execute the query
		ResultSet res = query.executeQuery();
		if (res.next()) {
			// User exists in the DB -> create object
			user = new User(res.getString("username"),
					res.getString("password"), res.getString("email"));
		}
		
		deleteTicket(hash);
				
		// close connection
		c.close();
		return user;
	}
	
	/*
	 * checkTicket : Returns true if the ticket is valid, false otherwise
	 */
	public static boolean checkTicket(String hash) throws SQLException {
		// Get connection from database
		Connection c = Database.getConnection();				
		
		// Test if ticket is in database
		PreparedStatement query = c.prepareStatement(CHECK_TICKET);
		query.setString(1, hash);
		ResultSet set = query.executeQuery();
		// close the connection
		boolean ticket_exists = set.next();
		if (ticket_exists) {
			String epoch = set.getString("epoch");
			if (epoch == null) {
				deleteTicket(set.getString("hash"));
				c.close();
				return false;
			}
			long ticket_creation = Long.parseLong(epoch);
			long currentTimestamp = Instant.now().getEpochSecond();
			
			if (currentTimestamp - ticket_creation > EXPIRACY_TIME) {
				deleteTicket(set.getString("hash"));
				c.close();
				throw new SQLException("Ticket expiré.");
			}
		}
		c.close();
		return ticket_exists;
	}
	
	public static void deleteTicket(String hash) throws SQLException {
		if (hash == null) {
			return;
		}
		Connection c = Database.getConnection();
		// Now trying to delete the ticket
		PreparedStatement query = c.prepareStatement(DELETE_TICKET);
		query.setString(1, hash);
		query.execute();
	}
	
	private static String generateTicketHash() {

	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();

	    for(int i = 0; i < HASH_LEN; i++) {

	      int index = random.nextInt(ALPHABET.length());
	      char randomChar = ALPHABET.charAt(index);
	      sb.append(randomChar);
	    }

	    return sb.toString();
	}
}