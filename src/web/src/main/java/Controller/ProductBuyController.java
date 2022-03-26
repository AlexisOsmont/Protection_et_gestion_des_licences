package Controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.LicenceDAO;
import DAO.SoftwareDAO;
import Utils.UserSession;
import Utils.ErrorMsg;
import model.Client;
import model.Licence;
import model.Software;

public class ProductBuyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String PRODUCT_BUY_ROUTE = "/product-buy/";
	
	// Controller
	public ProductBuyController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String url = request.getRequestURL().toString();
		
		int idx = url.indexOf(PRODUCT_BUY_ROUTE);
		String val = url.substring(idx + PRODUCT_BUY_ROUTE.length());
		
		if (idx > 0 && val != null) {
			int softwareId = Integer.valueOf(val);
			Software soft = SoftwareDAO.get(softwareId);
			if (soft != null) {
				request.setAttribute("product", soft);
				
				// retrieve the client object
				HttpSession session = request.getSession(false);
				UserSession s = (UserSession) session.getAttribute("user");
				Client client = s.getClient();
				
				// @TODO: send email to admin
				
				// create an entry in the DB
				
				String validity = request.getParameter("validity");
				int year = validity == null ? 0 : Integer.valueOf(validity);
				
				if (year > 0) {
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.YEAR, year);
					
					Licence licence = new Licence(client.getId(), soft.getId());
					licence.setStatus(Licence.Status.PENDING.ordinal());
					licence.setValidity(calendar.getTime());
					
					try {
						LicenceDAO.insert(licence);
						// redirect user to the page but add a success msg 
						ErrorMsg.setError(request, ErrorMsg.Severity.SUCCESS, ErrorMsg.MSG_LICENCE_REQUEST_SUCCESS);
						response.sendRedirect(request.getContextPath() + "/product/" + val);
						
					} catch (AssertionError e) {
						// redirect user to the page but add an error msg 
						ErrorMsg.setError(request, ErrorMsg.ERROR_LICENCE_ALREADY_OWNED);
						response.sendRedirect(request.getContextPath() + "/product/" + val);
					}
				} else {
					// redirect user to the page but add an error msg 
					ErrorMsg.setError(request, ErrorMsg.ERROR_LICENCE_INVALID_PARAMETERS);
					response.sendRedirect(request.getContextPath() + "/product/" + val);
				}
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
