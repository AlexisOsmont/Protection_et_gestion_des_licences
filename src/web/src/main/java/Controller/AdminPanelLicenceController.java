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

public class AdminPanelLicenceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int 	ROW_NUMBER = 10; 
	
	private static final String PAGE_ATTRIBUTE 			  = "page";
	private static final String FILTER_ATTRIBUTE 		  = "filter";
	
	private static final String FILTER_BY_SOFTWARE 		  = "bySoftware";
	private static final String SOFTWARE_FILTER_ATTRIBUTE = "softwareName";
	
	private static final String FILTER_BY_CLIENT 		  = "byClient";
	private static final String CLIENT_FILTER_ATTRIBUTE   = "clientMail";
	
	private static final String FILTER_BY_STATUS 		  = "byStatus";
	private static final String STATUS_FILTER_ATTRIBUTE	  = "statusName";
	
	// Controller
	public AdminPanelLicenceController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// create a list of list of all the informations we are going to show
		// in the table
		List<List<String>> li = new ArrayList<List<String>>();
		List<Licence> licences = LicenceDAO.list();
		
		String filter = (String) request.getAttribute(FILTER_ATTRIBUTE);
		String page   = (String) request.getAttribute(PAGE_ATTRIBUTE);
		int cut 	  = 0;
		
		// first check if any filter was applied
		
		// no filter were applied, take the first ROW_NUMBER result
		// filter=bysoft&softId=2
		
		// then check for the page number, if no page number are supplied return the first one
		if (page != null) {
			// check for the page number (a page contains ROW_NUMBER result)
			cut = Integer.valueOf(page);
			System.out.println(page);
			cut = cut < 0 ? 0 : cut * ROW_NUMBER;
		} else {
			System.out.println("NULL");
		}
		
		// get the sublist corresponding to the page asked
		licences = licences.subList(
				Math.min(cut, licences.size()), 
				Math.min(cut + ROW_NUMBER, licences.size())
		);
		
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
		
		// retrieve the number of notification
		List<Licence> notif = LicenceDAO.list(Licence.Status.PENDING);
		request.setAttribute("notification-list-size", notif == null ? 0 : notif.size());
		
		request.setAttribute("array-content", li);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/admin-licence.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}

