package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.LicenceDAO;
import model.Licence;

public class AdminPanelSoftwareController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Controller
	public AdminPanelSoftwareController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Licence> licences = LicenceDAO.list(Licence.Status.PENDING);
		request.setAttribute("notification-list-size", licences.size());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/admin-software.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
