package Controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import DAO.AdminDAO;
import DAO.MaintainerDAO;
import model.Admin;
import model.Maintainer;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Controller
	public LoginController() {
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
			
			if (action != null && action.equals("login")) {
				doLogin(request, response);
			} else {
				// user is just requesting the login page
				ControllerCommon.forwardTo("/WEB-INF/login.jsp", request, response);
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
	
	// tools
	
	private void doLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// retrieve user credentials 
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		ControllerCommon.setError(request, "Problème d'identifiant / mot de passe");
		
		if (email != null && password != null) {
			
			// first check if user is a maintainer, if not maybe it's an admin
			try {
				Maintainer m = MaintainerDAO.get(email);
				Admin adm = AdminDAO.get(email); 
				HttpSession session = request.getSession();
				
				if (m != null && m.matchPassword(password)) {
					// user is a maintainer
					ControllerCommon.removeError(request);
					session.setAttribute("maintainer", m);
					
				} else if (adm != null && adm.matchPassword(password)) {
					// user is an admin
					ControllerCommon.removeError(request);
					session.setAttribute("admin", adm);
				} 
			} catch (AssertionError e) {ControllerCommon.setError(request, e.getMessage());}
		} 
	
		response.sendRedirect(request.getContextPath() + "/PanelController?action=home");
	}
	
	
}
