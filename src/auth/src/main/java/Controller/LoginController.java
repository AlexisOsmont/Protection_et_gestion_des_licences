package Controller;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import Entity.User;
import Model.TicketsModel;
import Model.UserModel;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Controller
	public LoginController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// retrieve session
		HttpSession session = request.getSession();
		
		// retrieve service parameter
		String service = request.getParameter("service");
		
		if (service != null) {
			// we store it in the session
			session.setAttribute("service", service);
		} else {
			// in case it is set from another connection
			session.removeAttribute(service);
		}
		
		// then serve the page
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
		requestDispatcher.forward(request, response);
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();

		// validate the login 
		String mail = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (mail == null || password == null) {
			doGet(request, response);
			return;
		}
		
		String ticket = "";
		
		User user = null;
		try {
			user = UserModel.getUserByMail(mail);
		} catch (SQLException e) {
			request.setAttribute("errorMessage", "Connexion échouée dû à une erreur interne.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
		
		if (user == null) {
			request.setAttribute("errorMessage", "Connexion échouée, vérifiez vos identifiants.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
		
		// TODO : must implement becrypt check
		if (password.equals(user.getPassword())) {
			
			String url = (String) session.getAttribute("service");
			
			if (url == null) {
				session.setAttribute("logged", true);
				session.setAttribute("username", user.getUsername());
				session.setAttribute("mail", user.getMail());
				response.sendRedirect(request.getScheme() + "://" +  request.getServerName() + ":" + request.getServerPort() + "/home");
				return;
			}
			
			// On créer un ticket pour cet utilisateur
			try {
				ticket = TicketsModel.newTicket(user);
			} catch (SQLException e) {
				request.setAttribute("errorMessage", "Connexion échouée dû à une erreur interne.");
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
				return;
			}
			
			// better to remove service from session for other connections
			session.removeAttribute("service");
			
			// redirect to service url
			response.sendRedirect(url + "?ticket=" + ticket);
			return;
		}
		
		//TODO: set error login message or redirect to /home once created
		request.setAttribute("errorMessage", "Connexion échouée, vérifiez vos identifiants.");
		request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		return;
	}
}
