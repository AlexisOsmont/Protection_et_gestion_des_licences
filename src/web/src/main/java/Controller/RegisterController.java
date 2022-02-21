package Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Controller
	public RegisterController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// serve the register page
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/register.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doRegister(request, response);
	}
	
	private void doRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// register a user
		// for now every one get the the page they want
		response.sendRedirect(request.getContextPath() + "/home");
	}
}
