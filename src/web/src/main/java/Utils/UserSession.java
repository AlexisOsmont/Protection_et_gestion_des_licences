package Utils;

import model.Admin;
import model.Client;
import model.User;

public class UserSession {

	// list of all the routes a user is allowed to take
	private static final String[] USER_ALLOWED_URLS = { "/product-list", "/product-owned", "/product", "/product-img" };

	// list of all the routes an admin is allowed to take
	private static final String[] ADMIN_ALLOWED_URLS = {  };

	private boolean isAdmin;
	private Client client;
	private Admin admin;

	/**
	 * Object used to wrap information of the current logged user in the HttpSession
	 * 
	 * @param isAdmin true if the object passed as parameters is an Admin, false
	 *                otherwise
	 * @param user    is either an Admin or a Client Object
	 */
	public UserSession(boolean isAdmin, User user) {
		if (user == null) {
			throw new AssertionError("Invalid paramters");
		}

		this.isAdmin = isAdmin;
		if (isAdmin) {
			this.admin = (Admin) user;
		} else {
			this.client = (Client) user;
		}
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

	/**
	 * This function check if the user which was passed to the constructor is
	 * allowed to go to the route passed as parameters
	 * 
	 * @param url of the route user want to go
	 * @return true if user is allowed to take that route, false otherwise
	 */
	public boolean isAllowedFor(String url) {
		boolean result = false;
		if (url != null) {
			if (this.isAdmin) {

				for (String route : ADMIN_ALLOWED_URLS) {
					if (url.contains(route)) {
						result = true;
						break;
					}
				}
			} else {

				for (String route : USER_ALLOWED_URLS) {
					if (url.contains(route)) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}

}
