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

import DAO.ClientDAO;
import DAO.LicenceDAO;
import Utils.LicenceGenerator;
import model.Client;
import model.Licence;

/*
 *  URL : /api/v1/Licence/requestLicence
 *  
 *  Possibles status codes return :
 *  	- 200 : OK, return the license file.
 *  	- 400 : Bad request, badly formatted JSON or incorrect JSON attributes
 *  			in the body.
 *  	- 401 : Unauthorized, authentication failed due to invalid credentials.
 *  	- 404 : Not found, no license with this client/software was found.
 *  	- 500 : Internal Server Error, while reading request body or sending the
 *  			request to CAS server for authentication, or idk.
 */
public class APILicenceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String EMAIL		= "UserMail";
	private static final String PASSWORD	= "UserPassword";
	private static final String SOFT_ID 	= "SoftwareId";
	private static final String HW_HASH 	= "HardwareHash";
	
	private static final String WEB_SERVER_URL 		= "https://srv-dpi-proj-gestlic-web.univ-rouen.fr:8443/";
	private static final String CAS_SERVER_URL 		= "https://srv-dpi-proj-gestlic-auth.univ-rouen.fr:8443/";
	private static final String CAS_SERVER_LOGIN 	= "login";
	private static final String CAS_SERVER_SERVICE 	= "service";
	
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

	/*
	 * curl -vv --insecure -X POST -H 'Content-Type: application/json'
	 * -d '[{"UserMail": "client@client.fr", "UserPassword": "clientpasswd", "SoftwareId": 1, "HardwareHash": "SBAYIDIFHBUZEFYGCJE"}]'
	 * https://srv-dpi-proj-gestlic-web.univ-rouen.fr:8443/api/v1/Licence/requestLicence
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
		PrintWriter w = response.getWriter();
		
		// read request body
		JSONObject result = readBody(request, response);
		if (result == null) {
			return;
		}
		
		// get and encode the attributes
		String email = "", password = "", hardwareHash = "";
		long softId = 0;
		try {
			email = (String) result.get(EMAIL);
			password = (String) result.get(PASSWORD);
			softId = (long) result.get(SOFT_ID);
			hardwareHash = (String) result.get(HW_HASH);
		}
		// problem with request format
		catch (NullPointerException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// build body of the 2nd request
		String requestBody = "email=" + URLEncoder.encode(email, Charset.defaultCharset())
				+ "&password=" + URLEncoder.encode(password, Charset.defaultCharset())
				+ "&action=login";
		
	    // Create the HTTP client
		HttpClient httpClient = createClient(); 
		
		// create the request with the CAS server to check the ticket
		HttpRequest authReq = createRequest(requestBody); 

		HttpResponse<String> resp = null;
		try {
			resp = httpClient.send(authReq, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
		// check the status code
		// redirection to home due to invalid credentails
		if (resp != null && resp.statusCode() == HttpServletResponse.SC_OK) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		// Got a 302 found, OK
		else if (resp != null && resp.statusCode() == HttpServletResponse.SC_FOUND) {
			// check if the query contain the ticket
			String location = resp.headers().map().get("Location").get(0);
			String ticket = URI.create(location).getQuery().split("=")[1];
			if (ticket != null) {
				// ok, authenticated user
				Client clt = ClientDAO.get(email);
				if (clt != null) {
					Licence license = LicenceDAO.get(clt.getId(), (int) softId);
					
					if (license == null) {
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					} else {
						license.setHardwareId(hardwareHash);
						String licenseFile = LicenceGenerator.generate(license);
						w.write(licenseFile);
					}
				}
			}
		}
	}
	
	// Private methods

	private HttpRequest createRequest(String body) {
		return HttpRequest.newBuilder()
				.POST(BodyPublishers.ofString(body))
				.uri(URI.create(CAS_SERVER_URL + CAS_SERVER_LOGIN + "?" + CAS_SERVER_SERVICE + "="
						+ (String) URLEncoder.encode(WEB_SERVER_URL, Charset.defaultCharset())))
				.setHeader("User-Agent", "API Licence Request")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.build();
	}

	private HttpClient createClient() {
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
		
		return HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_1_1)
				.sslContext(sslContext) 
				.connectTimeout(Duration.ofSeconds(10))
				.build();
	}

	private JSONObject readBody(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer req = new StringBuffer();
		String line = null;
		JSONObject result = null;
		
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				req.append(line);
			}
			
			JSONArray array = null;
			array = (JSONArray) new JSONParser().parse(req.toString());
			result = (JSONObject) array.get(0);
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			result = null;
		} catch (NullPointerException | ParseException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result = null;
		}
		return result;
	}
}
