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
import Utils.DynamicTableHelper;
import model.Client;
import model.Licence;
import model.Licence.Status;
import model.Software;

public class AdminPanelLicenceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String FILTER_ATTRIBUTE 		  = "filter";
	
	private static final String FILTER_BY_NONE 			  = "none";
	private static final String NONE_FILTER_MESSAGE 	  = "Aucun";
	
	private static final String FILTER_BY_SOFTWARE 		  = "bySoftware";
	private static final String SOFTWARE_FILTER_ATTRIBUTE = "softwareName";
	private static final String SOFTWARE_FILTER_MESSAGE   = "Logiciel";
	
	private static final String FILTER_BY_CLIENT 		  = "byClient";
	private static final String CLIENT_FILTER_ATTRIBUTE   = "clientMail";
	private static final String CLIENT_FILTER_MESSAGE     = "Client";
	
	private static final String FILTER_BY_STATUS 		  = "byStatus";
	private static final String STATUS_FILTER_ATTRIBUTE	  = "statusName";
	private static final String STATUS_FILTER_MESSAGE     = "Status";
	
	private static final String[][] AVAILABLE_FILTERS = {
			{FILTER_BY_NONE, FILTER_BY_NONE, NONE_FILTER_MESSAGE},
			{FILTER_BY_SOFTWARE, SOFTWARE_FILTER_ATTRIBUTE, SOFTWARE_FILTER_MESSAGE},
			{FILTER_BY_CLIENT, CLIENT_FILTER_ATTRIBUTE, CLIENT_FILTER_MESSAGE},
			{FILTER_BY_STATUS, STATUS_FILTER_ATTRIBUTE, STATUS_FILTER_MESSAGE}
	};
			
	// Controller
	public AdminPanelLicenceController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// create a list of list of all the informations we are going to show
		// in the table
		List<List<String>> li = null;

		// first apply filter to the list
		List<Licence> licences = getFilteredList(request);
				
		// then select the specified interval
		licences = DynamicTableHelper.getPageList(licences, request);

		// check if the list is empty or not
		if (licences != null && licences.size() > 0) {
			// if not create it
			li = new ArrayList<List<String>>();
			// now fill the list with the information we got
			for (Licence licence : licences) {
				Software soft = SoftwareDAO.get(licence.getSoftwareId());
				Client client = ClientDAO.get(licence.getClientId());
				
				// create a "tuple" of string
				List<String> tuple = new ArrayList<String>();
				tuple.add(String.valueOf(licence.getId()));
				tuple.add(soft.getName());
				tuple.add(client.getEmail());
				tuple.add(String.valueOf(licence.getStatus()));
				
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
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/admin-licence.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	// tools 
	
	private List<Licence> getFilteredList(HttpServletRequest request) {
		String filter 		   = request.getParameter(FILTER_ATTRIBUTE);
		List<Licence> licences = null;
		
		// check if a filter was supplied
		if (filter != null) {
			switch (filter) {
				case FILTER_BY_SOFTWARE:
					String softName = request.getParameter(SOFTWARE_FILTER_ATTRIBUTE);
					if (softName != null) {
						// try to retrieve the software by it's name
						Software soft = SoftwareDAO.get(softName);
						if (soft != null) {
							licences = LicenceDAO.listBySoftware(soft.getId());
						}
					}
					break;
				case FILTER_BY_CLIENT:
					String clientMail = request.getParameter(CLIENT_FILTER_ATTRIBUTE);
					if (clientMail != null) {
						// try to retrieve the client by it's mail
						Client client = ClientDAO.get(clientMail);
						if (client != null) {
							licences = LicenceDAO.listByClient(client.getId());
						}
					}
					break;
				case FILTER_BY_STATUS:
					String status = request.getParameter(STATUS_FILTER_ATTRIBUTE);
					if (status != null) {
						// check if the string correspond to a call to status.toString()
						Status[] statusArray = Licence.Status.values();
						for (Status state : statusArray) {
							String str = state.toString();
							if (str.compareTo(status) == 0) {
								licences = LicenceDAO.listByStatus(state);
								break;
							}
						}
					}
					break;
				case FILTER_BY_NONE:
					// no filter where applied
					licences = LicenceDAO.list();
					break;
				default:
					// unkown filter 
					break;
			}
		} else {
			// no filter where applied
			licences = LicenceDAO.list();
		}
		
		return licences;
	}
}

