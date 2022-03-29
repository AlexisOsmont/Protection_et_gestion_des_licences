package Controller;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.*;
import javax.servlet.http.*;

import Utils.ErrorMsg;
import Utils.UserConverter;
import Utils.UserSession;

public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String CAS_SERVER_URL 		= "https://srv-dpi-proj-gestlic-auth.univ-rouen.fr:8443/";
	private static final String CAS_SERVER_LOGIN 	= "login";
	private static final String CAS_SERVER_VALIDATE = "validate";
	private static final String CAS_SERVER_SERVICE 	= "service";
	private static final String CAS_SERVER_TICKET   = "ticket";
	
	private static final String WEB_SERVER_URL 		= "https://srv-dpi-proj-gestlic-web.univ-rouen.fr:8443/";
	private static final String WEB_SERVER_LOGIN 	= "login";
	
	/*
	 * Such SSL context shall never be used in production environment.
	 */
	private static TrustManager[] trustAllCerts = new TrustManager[]{
		     new X509TrustManager() {
		        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		            return null;
		        }
		        public void checkClientTrusted(
		            java.security.cert.X509Certificate[] certs, String authType) {
		        }
		        public void checkServerTrusted(
		            java.security.cert.X509Certificate[] certs, String authType) {
		        }
		    }
		};
	
	// Controller
	public LoginController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doLogin(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doLogin(request, response);
	}

	// tools

	private void doLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ticket = (String) request.getParameter("ticket");
		if (ticket == null) {
			// redirect user to CAS server
			response.sendRedirect(CAS_SERVER_URL + CAS_SERVER_LOGIN
					+ "?" + CAS_SERVER_SERVICE + "=" + WEB_SERVER_URL + WEB_SERVER_LOGIN);
		} else {

			/* 
			 * @TMP
			 * Such SSL context shall never be used in production environment.
			 */
		    SSLContext sslContext = null;
		    
			try {
				sslContext = SSLContext.getInstance("TLS");
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			
		    try {
				sslContext.init(null, trustAllCerts, new SecureRandom());
			} catch (KeyManagementException e1) {
				e1.printStackTrace();
			}
		    
			HttpClient httpClient = HttpClient.newBuilder()
					.version(HttpClient.Version.HTTP_1_1)
					.sslContext(sslContext) 
					.connectTimeout(Duration.ofSeconds(10))
					.build();
			

			// make a request to the CAS server to validate the ticket
			HttpRequest req = HttpRequest.newBuilder(
					URI.create(CAS_SERVER_URL + CAS_SERVER_VALIDATE + "?" + CAS_SERVER_TICKET + "=" + ticket))
					.header("accept", "application/json").build();

			HttpResponse<String> resp = null;
			try {
				resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

			// check the status code
			if (resp != null && resp.statusCode() == 200 && resp.body() != null) {
				
				String route = null;
				UserConverter user = new UserConverter(resp.body());
				UserSession s = null;
				if (user.isAdmin()) {
					s = new UserSession(true, user.getAdmin());
					route = "/admin/notification";
				} else {
					s = new UserSession(false, user.getClient());
					route = "/product-list";
				}
				
				HttpSession session = request.getSession(true);
				session.setAttribute("user", s);
				ErrorMsg.setError(request, ErrorMsg.Severity.SUCCESS, ErrorMsg.MSG_AUTHENTIFICATED);
				response.sendRedirect(request.getContextPath() + route);
				
			} else {
				// @TODO set an error 
				response.sendRedirect(request.getContextPath() + "/home");
			}
			
		}
	}

}
