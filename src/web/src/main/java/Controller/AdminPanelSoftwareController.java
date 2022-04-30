package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.LicenceDAO;
import DAO.SoftwareDAO;
import Utils.DynamicTableHelper;
import model.Licence;
import model.Software;

public class AdminPanelSoftwareController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String FILTER_ATTRIBUTE 		  = "filter";
	
	private static final String FILTER_BY_NONE 			  = "none";
	private static final String NONE_FILTER_MESSAGE 	  = "Aucun";
	
	private static final String FILTER_BY_SOFTWARE 		  = "bySoftware";
	private static final String SOFTWARE_FILTER_ATTRIBUTE = "softwareName";
	private static final String SOFTWARE_FILTER_MESSAGE   = "Logiciel";
	
	private static final String[][] AVAILABLE_FILTERS = {
			{FILTER_BY_NONE, FILTER_BY_NONE, NONE_FILTER_MESSAGE},
			{FILTER_BY_SOFTWARE, SOFTWARE_FILTER_ATTRIBUTE, SOFTWARE_FILTER_MESSAGE}
	};
	
	// Controller
	public AdminPanelSoftwareController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// create a list of list of all the informations we are going to show
		// in the table
		List<List<String>> li = null;

		// first apply filter to the list
		List<Software> softwares = getFilteredList(request);
				
		// then select the specified interval
		softwares = DynamicTableHelper.getPageList(softwares, request);

		// check if the list is empty or not
		if (softwares != null && softwares.size() > 0) {
			// if not create it
			li = new ArrayList<List<String>>();
			// now fill the list with the information we got
			for (Software soft : softwares) {
				
				// create a "tuple" of string
				List<String> tuple = new ArrayList<String>();
				tuple.add(String.valueOf(soft.getId()));
				tuple.add(soft.getName());
				tuple.add(String.valueOf(soft.getPrice()));
				
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
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/admin-software.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	// tools 
	
	private List<Software> getFilteredList(HttpServletRequest request) {
		String filter 	     	 = request.getParameter(FILTER_ATTRIBUTE);
		List<Software> softwares = null;
		
		// check if a filter was supplied
		if (filter != null) {
			switch (filter) {
			case FILTER_BY_SOFTWARE:
				String softName = request.getParameter(SOFTWARE_FILTER_ATTRIBUTE);
				if (softName != null) {
					// try to retrieve the software by it's name
					Software soft = SoftwareDAO.get(softName);
					if (soft != null) {
						softwares = new ArrayList<Software>();
						softwares.add(soft);
					}
				}
				break;
				case FILTER_BY_NONE:
					// no filter where applied
					softwares = SoftwareDAO.list();
					break;
				default:
					// unkown filter 
					break;
			}
		} else {
			// no filter where applied
			softwares = SoftwareDAO.list();
		}
		
		return softwares;
	}

}
