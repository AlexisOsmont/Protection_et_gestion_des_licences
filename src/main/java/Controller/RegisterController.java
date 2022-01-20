package Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import DAO.MaintainerDAO;
import DBUtils.PasswordChief;
import model.Maintainer;

public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Controller
	public RegisterController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String action = null;
		
		// work only if user has set the session
		if (session != null) {
			
			if (request.getQueryString() != null) {
				// parse the query string
				action = request.getParameter("action");
			}
			
			if (action != null && action.equals("register")) {
				doRegister(request, response);
			} else {
				// user is just requesting the register page
				ControllerCommon.forwardTo("/WEB-INF/register.jsp", request, response);
			}
		} else {
			// if not redirect him to the home page
			ControllerCommon.forwardTo("/WEB-INF/home.jsp", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	private void doRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// retrieve user credentials 
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		ControllerCommon.setError(request, "Problème d'identifiant / mot de passe");
		
		if (email != null && password != null && username != null 
				&& PasswordChief.checkPasswordStrength(password)) {
			
			// user can only be an maintainer because there 
			// is only one admin
			try {
				Maintainer m = MaintainerDAO.get(email);
				HttpSession session = request.getSession();
				
				if (m == null) {
					// user doesn't exist we can create it
					m = new Maintainer(username, email, password);
					
					if (m != null) {
						// add maintainer to the DB
						try {
							MaintainerDAO.insert(m);
						} catch (RuntimeException e) {
							m = null;
						} catch (AssertionError e) {
							m = null;
						}
						
						// user was created and inserted in the DB
						if (m != null) {
							ControllerCommon.removeError(request);
							session.setAttribute("maintainer", m);
						}
					} 
				}
			} catch (AssertionError e) {} // invalid email
		} 
		
		response.sendRedirect(request.getContextPath() + "/PanelController?action=home");
	}
}
