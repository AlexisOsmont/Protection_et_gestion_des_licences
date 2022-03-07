package Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import DAO.SoftwareDAO;

public class ProductListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Controller
	public ProductListController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("product-list", SoftwareDAO.list());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product-list.jsp");
		requestDispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
