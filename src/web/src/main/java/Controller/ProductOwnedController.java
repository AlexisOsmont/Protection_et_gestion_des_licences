package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.LicenceDAO;
import DAO.SoftwareDAO;
import Utils.UserSession;
import model.Client;
import model.Licence;
import model.Software;

public class ProductOwnedController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductOwnedController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		UserSession s = (UserSession) session.getAttribute("user");
		Client client = s.getClient();
		
		List<Licence> ownedLicence = LicenceDAO.list(client.getId());
		List<Software> softwareList = new ArrayList<Software>();

		for (Licence licence : ownedLicence) {
			int softwareId = licence.getSoftwareId();
			Software soft = SoftwareDAO.get(softwareId);
			softwareList.add(soft);
		}
		
		request.setAttribute("product-list", softwareList);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product-owned.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
