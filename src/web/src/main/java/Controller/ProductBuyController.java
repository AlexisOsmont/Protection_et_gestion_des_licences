package Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProductBuyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Controller
	public ProductBuyController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product-buy.jsp");
		requestDispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
