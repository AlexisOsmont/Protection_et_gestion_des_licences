package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ClientDAO;
import DAO.LicenceDAO;
import DAO.SoftwareDAO;
import model.Client;
import model.Licence;
import model.Software;

public class AdminPanelNotificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Controller
	public AdminPanelNotificationController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// create a list of list of all the informations we are going to show
		// in the notification board
		List<List<String>> li = new ArrayList<List<String>>();
		List<Licence> licences = LicenceDAO.list(Licence.Status.PENDING);
		for (Licence licence : licences) {
			Software soft = SoftwareDAO.get(licence.getSoftwareId());
			Client client = ClientDAO.get(licence.getClientId());
			
			List<String> tuple = new ArrayList<String>();
			tuple.add(String.valueOf(licence.getId()));
			tuple.add(soft.getName());
			tuple.add(client.getEmail());
			
			li.add(tuple);
		}
		
		request.setAttribute("notification-list", li);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/admin-notification.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
