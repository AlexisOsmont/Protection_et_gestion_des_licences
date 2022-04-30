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
import Utils.DynamicTableHelper;
import model.Client;
import model.Licence;

public class AdminPanelClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String FILTER_ATTRIBUTE 		  = "filter";
	
	private static final String FILTER_BY_NONE 			  = "none";
	private static final String NONE_FILTER_MESSAGE 	  = "Aucun";
	
	private static final String FILTER_BY_CLIENT 		  = "byClient";
	private static final String CLIENT_FILTER_ATTRIBUTE   = "clientMail";
	private static final String CLIENT_FILTER_MESSAGE     = "Client";
	
	private static final String[][] AVAILABLE_FILTERS = {
			{FILTER_BY_NONE, FILTER_BY_NONE, NONE_FILTER_MESSAGE},
			{FILTER_BY_CLIENT, CLIENT_FILTER_ATTRIBUTE, CLIENT_FILTER_MESSAGE}
	};
	
	// Controller
	public AdminPanelClientController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// create a list of list of all the informations we are going to show
		// in the table
		List<List<String>> li = null;

		// first apply filter to the list
		List<Client> clients = getFilteredList(request);
				
		// then select the specified interval
		clients = DynamicTableHelper.getPageList(clients, request);

		// check if the list is empty or not
		if (clients != null && clients.size() > 0) {
			// if not create it
			li = new ArrayList<List<String>>();
			// now fill the list with the information we got
			for (Client client : clients) {
				
				// create a "tuple" of string
				List<String> tuple = new ArrayList<String>();
				tuple.add(String.valueOf(client.getId()));
				tuple.add(client.getUsername());
				tuple.add(client.getEmail());
				
				li.add(tuple);
			}
		}
		
		// retrieve the number of notification (just a side nav information)
		List<Licence> notif = LicenceDAO.listByStatus(Licence.Status.PENDING);
		request.setAttribute("notification-list-size", notif == null ? 0 : notif.size());
		
		// send the list of information we created
		request.setAttribute("array-content", li);
		// send the description of the filters
		request.setAttribute("filters-description", AVAILABLE_FILTERS);
		// send the current page, this is done in the getPageList function to save 
		// some computation time 
		// request.setAttribute("current-page", currentPage);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/admin-client.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	// tools 
	
	private List<Client> getFilteredList(HttpServletRequest request) {
		String filter 		   = request.getParameter(FILTER_ATTRIBUTE);
		List<Client>  clients  = null;
		
		// check if a filter was supplied
		if (filter != null) {
			switch (filter) {
				case FILTER_BY_CLIENT:
					String clientMail = request.getParameter(CLIENT_FILTER_ATTRIBUTE);
					if (clientMail != null) {
						// try to retrieve the client by it's mail
						Client client = ClientDAO.get(clientMail);
						if (client != null) {
							clients = new ArrayList<Client>();
							clients.add(client);
						}
					}
					break;
				case FILTER_BY_NONE:
					// no filter where applied
					clients = ClientDAO.list();
					break;
				default:
					// unkown filter 
					break;
			}
		} else {
			// no filter where applied
			clients = ClientDAO.list();
		}
		
		return clients;
	}
}
