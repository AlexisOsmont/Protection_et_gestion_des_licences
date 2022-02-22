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

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
			response.sendRedirect("https://srv-dpi-proj-gestlic-auth.univ-rouen.fr:8443/login"
					+ "?service=https://srv-dpi-proj-gestlic-web.univ-rouen.fr:8443");
		} else {

			/* 
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
					URI.create("https://srv-dpi-proj-gestlic-auth.univ-rouen.fr:8443/login?validate=" + ticket))
					.header("accept", "application/json").build();

			HttpResponse<String> resp = null;
			try {
				resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

			// check the status code
			if (resp != null && resp.statusCode() == 200) {

				HttpSession session = request.getSession(false);
				session.setAttribute("logged", true);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product-buy.jsp");
				requestDispatcher.forward(request, response);

			} else {
				response.sendRedirect(request.getContextPath() + "/home");
			}
		}
	}

}
