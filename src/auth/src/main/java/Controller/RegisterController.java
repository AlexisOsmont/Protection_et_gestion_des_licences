package Controller;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import Entity.User;
import Model.UserModel;
import Utils.Password;
import Utils.Validator;

public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Controller
	public RegisterController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// retrieve session
		HttpSession session = request.getSession();
		
		if (session.getAttribute("logged") != null) {
			response.sendRedirect(request.getScheme() + "://" +  request.getServerName() + ":" + request.getServerPort() + "/home");
			return;
		}

		//render page
		render(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		// retrieving post params for registering user
		String username = request.getParameter("username");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		
		// if one of params missing 
		// no specific treatment
		if (username == null || mail == null || password == null) {
			render(request, response);
			return;
		}
		
		// Validation 
		try {
			Validator.checkUsername(username);
			Validator.checkEmail(mail);
			Validator.checkPasswordStrength(password);
		} catch (AssertionError e) {
			request.setAttribute("errorMessage", "Inscription échouée. <br/> " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
			return;
		}
		
		// if not creating the new user
		String salt = Password.generateSalt();
		String encryptedPass = null;
		try {
			encryptedPass = Password.cookPassword(salt, password);
		} catch (NoSuchAlgorithmException e1) {
			request.setAttribute("errorMessage", "Inscription échouée dû à une erreur interne. <br/> ");
			request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
			return;
		}
		User newUser = new User(username, encryptedPass, mail);
		
		try {
			UserModel.checkUserAlreadyExist(newUser);
			UserModel.insertUser(newUser);
		} catch (SQLException e) {
			// sending the error message to the view
			request.setAttribute("errorMessage", e.getMessage());
			// calling the view
			render(request, response);
			return;
		}
		
		// setting success code to the view
		request.setAttribute("errorMessage", "Inscription Réussie !");
	
		// calling the view
		render(request, response);
	}
	
	private void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
	}

}
