package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ClientDAO;
import DAO.LicenceDAO;
import DAO.SoftwareDAO;
import Utils.ErrorMsg;
import model.Client;
import model.Licence;
import model.Software;
import model.Licence.Status;

public class AdminPanelLicenceDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String LICENCE_ROUTE    = "/admin/licence/";
	private static final String ACTION 			 = "action";
	private static final String UPDATE_STATUS 	 = "updateStatus";
	private static final String STATUS_ATTRIBUTE = "statusName";
	
	// Controller
	public AdminPanelLicenceDetailController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String url = request.getRequestURL().toString();
		int idx = url.indexOf(LICENCE_ROUTE);
		
		if (idx > 0) {
			int licenceId = Integer.valueOf(url.substring(idx + LICENCE_ROUTE.length()));
			Licence licence = LicenceDAO.get(licenceId);
			if (licence != null) {

				// just for the notifications
				List<Licence> licences = LicenceDAO.listByStatus(Licence.Status.PENDING);
				request.setAttribute("notification-list-size", licences.size());
				
				// add (licence, client, software) object in order to print detail 
				// for this licence
				request.setAttribute("client", ClientDAO.get(licence.getClientId()));
				request.setAttribute("software", SoftwareDAO.get(licence.getSoftwareId()));
				request.setAttribute("licence", licence);
				
				// now check if there is a query string 
				// an update should look like
				//
				// 	   <url> ? ACTION = UPDATE_STATUS & STATUS_ATTRIBUTE = <status>
				
				String action = request.getParameter(ACTION);
				String status = null;
				Status newState = null;
				
				if (action != null && action.equals(UPDATE_STATUS)) {
					// update the status of the licence
					status = request.getParameter(STATUS_ATTRIBUTE);
					if (status != null) {
						// check if the string correspond to a call to status.toString()
						Status[] statusArray = Licence.Status.values();
						for (Status state : statusArray) {
							String str = state.toString();
							if (str.compareTo(status) == 0) {
								newState = state;
								break;
							}
						}
					}
					
					// now check if we find a valid state
					if (newState != null) {
						licence.setStatus(newState.ordinal());
						try {
							LicenceDAO.updateStatus(licence);
							ErrorMsg.setError(request, ErrorMsg.Severity.SUCCESS, 
									ErrorMsg.MSG_LICENCE_UPDATED);
							
						} catch (RuntimeException e) {
							// failed to update this licence
							ErrorMsg.setError(request, ErrorMsg.ERROR_LICENCE_UPDATE_FAILED);
						}
					} else {
						// no parameters where supplied 
						// or doesn't correspond to a valid state
						ErrorMsg.setError(request, ErrorMsg.ERROR_LICENCE_INVALID_PARAMETERS);
					}
				} 
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/admin-licence-detail.jsp");
				requestDispatcher.forward(request, response);
			} else {
				// redirect user to the page but add an error msg 
				ErrorMsg.setError(request, ErrorMsg.ERROR_LICENCE_INVALID_PARAMETERS);
				response.sendRedirect(request.getContextPath() + "/home");
			}
		} else {
			ErrorMsg.setError(request, ErrorMsg.ERROR_LICENCE_INVALID_PARAMETERS);
			response.sendRedirect(request.getContextPath() + "/home");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
