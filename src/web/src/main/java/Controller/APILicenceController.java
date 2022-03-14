package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class APILicenceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Controller
	public APILicenceController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Content-Type", "application/json");
		PrintWriter w = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("A", "b");
        w.write(obj.toJSONString());
	}

}
