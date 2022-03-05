package Utils;

import java.util.Map;

import DAO.AdminDAO;
import DAO.ClientDAO;

import model.Admin;
import model.Client;

public class UserConverter extends CASProtocol {

	// Attributes
	
	private static String USERNAME = "username";
	private static String EMAIL = "mail";

	private boolean isAdmin;
	private Client client;
	private Admin admin;
	private Map<String, String> map;

	// Constructor
	
	/**
	 * Class to handle data exchange between CAS server and this server, the
	 * constructor try to convert the text argument (which should be a message send
	 * by the CAS server) to a Client or an Administrator
	 * 
	 * @param text of the message
	 */
	public UserConverter(String text) {
		this.map = super.recieved(text);
		String email = this.map.get(EMAIL);
		String username = this.map.get(USERNAME);
		if (email == null || username == null) {
			throw new RuntimeException("Error: missing information in data send by CAS server");
		}
		admin = AdminDAO.get(email);
		client = ClientDAO.get(email);
		if (admin == null && client == null) {
			throw new RuntimeException("Error: unknown user");
		}
		isAdmin = admin != null;
	}

	/**
	 * @return true if the user is an Administrator, false otherwise
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * @return a Client object if !isAdmin(), a AssertionError is thrown otherwise
	 */
	public Client getClient() {
		if (isAdmin) {
			throw new AssertionError("User is not a Client");
		}
		return client;
	}

	/**
	 * @return a Admin object if isAdmin(), a AssertionError is thrown otherwise
	 */
	public Admin getAdmin() {
		if (!isAdmin) {
			throw new AssertionError("User is not a Admin");
		}
		return admin;
	}
}
