package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import DAO.LicenceDAO;
import DAO.SoftwareDAO;
import Utils.ErrorMsg;
import model.Licence;
import model.Software;


public class AdminPanelNewSoftwareController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String SOFTWARE_NAME             = "software-name";
	private static final String SOFTWARE_DESCRIPTION      = "software-desc";
	private static final String SOFTWARE_PRICE            = "price";
	private static final String SOFTWARE_PRICE_MULTIPLIER = "price-multiplier";
	private static final String SOFTWARE_ICON_FILE        = "icon-file";
	
	// Controller
	public AdminPanelNewSoftwareController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// retrieve the number of notification (just a side nav information)
		List<Licence> notif = LicenceDAO.listByStatus(Licence.Status.PENDING);
		request.setAttribute("notification-list-size", notif == null ? 0 : notif.size());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/admin-new-software.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Software soft        = null;
		String softName      = getParameter(request, SOFTWARE_NAME);
		String softDesc      = getParameter(request, SOFTWARE_DESCRIPTION);
		String softPrice     = getParameter(request, SOFTWARE_PRICE);
		String softPriceMult = getParameter(request, SOFTWARE_PRICE_MULTIPLIER);
		
		int price     = softPrice != null ? Integer.valueOf(softPrice) : 0;
		int priceMult = softPriceMult != null ? Integer.valueOf(softPriceMult) : 0;
		
		InputStream inputStream = null ;
		Part part = request.getPart(SOFTWARE_ICON_FILE);
		
		// first check if the mandatory arguments where supplied
		if (softName != null && softDesc != null && softPrice != null 
		 && softPriceMult != null && price > 0 && priceMult > 0) {
			
			soft = new Software(softName, softDesc);
			soft.setPrice(price);
			soft.setPriceMultiplier(priceMult);
			// now try to insert the software inside the database
			try {
				SoftwareDAO.insert(soft);
				// now try to insert the image if it was supplied
				if (part != null && soft != null) {
					inputStream = part.getInputStream();
					byte[] data = inputStream.readAllBytes();
					soft.setImg(data);
					SoftwareDAO.updateImage(soft);
				}
				
				// success
				ErrorMsg.setError(request, ErrorMsg.Severity.SUCCESS, 
						ErrorMsg.MSG_SOFTWARE_CREATED);
				
			} catch (Exception e) {
				// insertion failed
				soft = null;
				// set an error
				ErrorMsg.setError(request, ErrorMsg.ERROR_SOFTWARE_CREATION_FAILED);
			}			
		} else {
			// invalid parameters
			ErrorMsg.setError(request, ErrorMsg.ERROR_SOFTWARE_INVALID_PARAMETERS);
		}
		
		response.sendRedirect(request.getContextPath() + "/admin/software");
	
	}
	
	// tools 
	
	/**
	 * This function is useful since request.getParameter doesn't work
	 * when the encoding type of the form is set to "multipart/form-data"
	 * 
	 * @param request the request object
	 * @param name the name of the parameter 
	 * @return a string representing the single value of the parameter
	 * @throws IOException
	 * @throws ServletException
	 */
	private String getParameter(HttpServletRequest request, String name) 
			throws IOException, ServletException {
		Part part = request.getPart(name);
		InputStream inputStream = part.getInputStream();
		return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
	}
	
}
