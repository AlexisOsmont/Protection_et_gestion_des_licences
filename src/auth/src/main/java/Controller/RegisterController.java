package Controller;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import Entity.User;
import Model.UserModel;
import Utils.Validator;

public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Controller
	public RegisterController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
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
		User newUser = new User(username, password, mail);
		
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
