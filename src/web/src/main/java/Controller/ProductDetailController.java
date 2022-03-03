package Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import DAO.SoftwareDAO;
import model.Software;

public class ProductDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Controller
	public ProductDetailController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String url = request.getRequestURL().toString();
		int idx = url.indexOf("/product/");
		
		if (idx > 0) {
			int softwareId = Integer.valueOf(url.substring(idx+"/product/".length()));
			Software soft = SoftwareDAO.get(softwareId);
			if (soft != null) {
				request.setAttribute("product", soft);
				// @TMP
				request.setAttribute("is-owned", false);
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product-detail.jsp");
				requestDispatcher.forward(request, response);
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/home");
		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
