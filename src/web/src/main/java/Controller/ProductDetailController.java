package Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import DAO.LicenceDAO;
import DAO.SoftwareDAO;
import Utils.UserSession;
import model.Client;
import model.Software;

public class ProductDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String PRODUCT_ROUTE = "/product/";
	
	// Controller
	public ProductDetailController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String url = request.getRequestURL().toString();
		int idx = url.indexOf(PRODUCT_ROUTE);
		
		if (idx > 0) {
			int softwareId = Integer.valueOf(url.substring(idx + PRODUCT_ROUTE.length()));
			Software soft = SoftwareDAO.get(softwareId);
			if (soft != null) {
				request.setAttribute("product", soft);
				
				// retrieve the client object
				HttpSession session = request.getSession(false);
				UserSession s = (UserSession) session.getAttribute("user");
				Client client = s.getClient();
				
				// check if the software is owned by the client
				boolean isOwned = LicenceDAO.get(client.getId(), softwareId) != null;;
				request.setAttribute("is-owned", isOwned);
				
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
