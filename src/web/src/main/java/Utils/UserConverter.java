package Utils;

import java.util.Map;

import DAO.AdminDAO;
import DAO.ClientDAO;

import model.Admin;
import model.Client;

public class UserConverter extends CASProtocol {

	private static String USERNAME = "username";
	private static String EMAIL    = "mail";
	
	private boolean isAdmin;
	private Client client;
	private Admin admin;
	private Map<String, String> map;
	
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
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public Client getClient() {
		if (isAdmin) {
			throw new AssertionError("User is not a Client");
		}
		return client;
	}
	
	public Admin getAdmin() {
		if (!isAdmin) {
			throw new AssertionError("User is not a Admin");
		}
		return admin;
	}
}
