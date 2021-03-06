package Controller;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import Entity.User;
import Model.TicketsModel;
import Model.UserModel;
import Utils.Password;
import Utils.Validator;

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
		
		if (request.getParameter("disconnect") != null) {
			session.invalidate();
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
			requestDispatcher.forward(request, response);
		}
		
		if (session.getAttribute("logged") != null) {
			response.sendRedirect(request.getScheme() + "://" +  request.getServerName() + ":" + request.getServerPort() + "/home");
			return;
		}
		
		// retrieve service parameter
		String service = request.getParameter("service");
		
		if (service != null) {
			
			// Si l'utilisateur est déjà connecté on lui génère un ticket de suite
			Boolean logged = (Boolean) session.getAttribute("logged");
			if (logged != null && logged.booleanValue()) {
				String ticket = null;
				try {
					ticket = TicketsModel.newTicket(new User((String)session.getAttribute("username"),
													null, (String)session.getAttribute("mail")));
				} catch (SQLException e) {
					session.invalidate();
					request.setAttribute("errorMessage", "Connexion échouée dû à une erreur interne.");
					request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
					return;
				}
				
				if (ticket == null) {
					session.invalidate();
					request.setAttribute("errorMessage", "Connexion échouée dû à une erreur interne.");
					request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
					return;
				}
				
				response.sendRedirect(service + "?ticket=" + ticket);
				return;
			}
			
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
		} catch (AssertionError e) {
			request.setAttribute("errorMessage", "Connexion échouée. " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
		
		try {
			Validator.checkPasswordStrength(password);
		} catch (AssertionError e) {
			if (!mail.equals("admin@admin.fr") && !mail.equals("client@client.fr") ) {
				request.setAttribute("errorMessage", "Connexion échouée, vérifiez vos identifiants.");
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
				return;
			}
		}
		
		if (user == null) {
			request.setAttribute("errorMessage", "Connexion échouée, vérifiez vos identifiants.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
		
		if (Password.verifyPassword(user.getPassword(), password)) {
			
			String url = (String) session.getAttribute("service");
            // check is the service was given before in the get, if not try to retrieve it 
            // in the parameters of the post request 
            if (url == null) {
                url = (String) request.getParameter("service");
                // if the service url was supplied, store it in the session
                if (url != null) {
                    session.setAttribute("service", url);
                }
            }
  
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
		
		request.setAttribute("errorMessage", "Connexion échouée, vérifiez vos identifiants.");
		request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		return;
	}
}
