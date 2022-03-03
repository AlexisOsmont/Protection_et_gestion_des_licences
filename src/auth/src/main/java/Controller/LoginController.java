package Controller;

import java.io.*;
//import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.servlet.*;
import javax.servlet.http.*;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Controller
	public LoginController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// retrieve session
		HttpSession session = request.getSession();
		
		// retrieve service parameter
		String service = request.getParameter("service");
		
		if (service != null) {
			// we store it in the session
			session.setAttribute("service", service);
		}
		
		// then serve the page
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
		requestDispatcher.forward(request, response);
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();

		// validate the login 
		String mail = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (mail == null || password == null) {
			return;
		}
				
		String url = (String) session.getAttribute("service");
		
		if (url != null) {
			String ticket = "";
			
			// Hard coding for now
			if (mail.equals("admin@admin.fr") && password.equals("adminpasswd")) {
				ticket = "123456789";
			}
			if (mail.equals("client@client.fr") && password.equals("clientpasswd")) {
				ticket = "987654321";
			}
			
			// better to remove service from session for other connections
			session.removeAttribute("service");
			
			// redirect to service url
			response.sendRedirect(url + "?ticket=" + ticket);
			return;
		}
		
		//TODO: set error login message or redirect to /home once created
		response.sendRedirect(request.getContextPath() + "/login");
		return;
	}
	
	
	// tools
	
	// Generate ticket
//	private String generateTicket() {
//		
//		/*
//		// Create a SecureRandom object (Java CSPRNG)
//    	SecureRandom random = new SecureRandom();
//        byte bytes[] = new byte[32];
//    	// Generate next 32 bytes in bytes
//        random.nextBytes(bytes);
//        */
//    	
//		byte bytes[] = {20,10,30,5,40,50,70,80};
//		
//    	// Encode into string value with base64
//    	Encoder encoder = Base64.getUrlEncoder();
//    	return encoder.encodeToString(bytes);
//	}
	
//	private boolean isValidTicket(String ticket) {
//		boolean valid = false;
//		if (ticket != null) {
//			// add code to validate into the DB
//			byte b[] = {20,10,30,5,40,50,70,80};
//			Decoder decoder = Base64.getUrlDecoder();
//			valid = b.equals(decoder.decode(ticket));
//		}
//		return valid;
//	}
	
//	private void doLogin(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		
//		// validate the login 
//		String email = request.getParameter("email");
//		String password = request.getParameter("password");
//		
//		if (validateUserCredentials(email, password)) {
//			// now send redirect
//			HttpSession session = request.getSession(false);
//			if (session != null) {
//				String url = (String)session.getAttribute("service");
//				response.sendRedirect(url + "?ticket=" + generateTicket());	
//			}
//		} else {
//			// send an error
//			response.sendRedirect(request.getContextPath() + "/login");
//		}
//	}
	
	
}
