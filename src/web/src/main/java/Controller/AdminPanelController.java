package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.LicenceDAO;
import model.Licence;

public class AdminPanelController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Controller
	public AdminPanelController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("notification-list", LicenceDAO.list(Licence.Status.PENDING));
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/admin-panel.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
