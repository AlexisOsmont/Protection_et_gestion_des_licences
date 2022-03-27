package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class APILicenceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String EMAIL		= "UserMail";
	private static final String PASSWORD	= "UserPassword";
	private static final String SOFT_ID 	= "SoftwareId";
	private static final String HW_HASH 	= "HardwareHash";
	
	private static final String CAS_SERVER_URL 		= "https://srv-dpi-proj-gestlic-auth.univ-rouen.fr:8443/";
	private static final String CAS_SERVER_LOGIN 	= "login";
	
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
	public APILicenceController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
		PrintWriter w = response.getWriter();
		
		// read request body
		StringBuffer req = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				req.append(line);
			}
		} catch (Exception e) {
			w.write("Error while reading body");
		}
		
		// expected JSON format, so parse body
		JSONObject result = null;
		try {
			JSONArray array = null;
			array = (JSONArray) new JSONParser().parse(req.toString());
			result = (JSONObject) array.get(0);
		} catch (ParseException e) {
			result = null;
			w.write("JSON parsing error");
		}
		
		// get and encode the attributes
		String email = URLEncoder.encode((String) result.get(EMAIL),
				Charset.defaultCharset());
		String password = URLEncoder.encode((String) result.get(PASSWORD),
				Charset.defaultCharset());
		long softId = (long) result.get(SOFT_ID);
		String hardwareHash = (String) result.get(HW_HASH);
		
		// build body of the 2nd request
		String requestBody = "email=" + email
				+ "&password=" + password
				+ "&action=login";

		/* 
		 * @TMP
		 * Such SSL context shall never be used in production environment.
		 */
	    SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCerts, new SecureRandom());
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}
	    
		HttpClient httpClient = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_1_1)
				.sslContext(sslContext) 
				.connectTimeout(Duration.ofSeconds(10))
				.build();
		
		// make a request to the CAS server to validate the ticket
		HttpRequest authReq = HttpRequest.newBuilder(
				URI.create(CAS_SERVER_URL + CAS_SERVER_LOGIN))
				.header("accept", "application/json")
				.POST(BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> resp = null;
		try {
			resp = httpClient.send(authReq, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		// check the status code
		if (resp != null && resp.statusCode() == 200) {
			// check if the query contain the ticket
			w.write("ça marche po");
		}
		
		// response.setHeader("Content-Type", "application/json");
	}

}
