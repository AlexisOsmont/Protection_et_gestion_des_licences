package Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Controller
	public HomeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/index.jsp");
		requestDispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
