package Utils;

import model.Admin;
import model.Client;
import model.User;

public class UserSession {

	private static final String[] USER_ALLOWED_URLS = {
        "/product-buy", "/product-owned", "/product", "/product-img"
	};
	
	private static final String[] ADMIN_ALLOWED_URLS = {
		"/product-buy", "/product-owned", "/product", "/product-img"
	};
	
	private boolean isAdmin;
	private Client client;
	private Admin admin;

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
