package Controller;

import java.io.*;
import java.security.SecureRandom;
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
		
		// serve the login page, first check is the session exist
		HttpSession session = request.getSession(false);
		if (session == null) {
			// if not create it
			session = request.getSession(true);
		}
		
		// retrieve the service parameter
		String serviceUrl = (String)request.getParameter("service");
		String ticket = (String)request.getParameter("validate");
		if (serviceUrl != null) {
			// if used store it in the session
			session.setAttribute("service", serviceUrl);
			// then serve the page
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
			requestDispatcher.forward(request, response);
			
		} else if (isValidTicket(ticket)) {
			// send 200 OK response
	   
	        // write information about user
	        ServletOutputStream sout = response.getOutputStream();
	        String content = "{\"user\":\"John\"}";
	        sout.print(content);
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doLogin(request, response);
	}
	
	// tools
	
	private boolean validateUserCredentials(String email, String password) {
		// for now
		return true;
	}
	
	// Generate ticket
	private String generateTicket(String email, String password) {
		
		/*
		// Create a SecureRandom object (Java CSPRNG)
		SecureRandom sr = new SecureRandom();
    	byte b[] = new byte[32];
    	// Generate next 32 bytes in b
    	sr.nextBytes(b);
    	*/
		
		byte b[] = {20,10,30,5,40,50,70,80};
    	// Encode into string value with base64
    	Encoder encoder = Base64.getUrlEncoder();
    	return encoder.encodeToString(b);
	}
	
	private boolean isValidTicket(String ticket) {
		boolean valid = false;
		if (ticket != null) {
			// add code to validate into the DB
			byte b[] = {20,10,30,5,40,50,70,80};
			Decoder decoder = Base64.getUrlDecoder();
			valid = b.equals(decoder.decode(ticket));
		}
		return valid;
	}
	
	private void doLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// validate the login 
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (validateUserCredentials(email, password)) {
			// now send redirect
			HttpSession session = request.getSession(false);
			if (session != null) {
				String url = (String)session.getAttribute("service");
				response.sendRedirect(url + "?ticket=" + generateTicket(email, password));	
			}
		} else {
			// send an error
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}
	
	
}
