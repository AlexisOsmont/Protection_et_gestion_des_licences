package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONValue;

import DAO.SoftwareDAO;
import model.Software;

public class APISoftwareController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Controller
	public APISoftwareController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Content-Type", "application/json");
		PrintWriter w = response.getWriter();
		
		List<Map<String, Object>> obj = new LinkedList<Map<String, Object>>();
		List<Software> softs = SoftwareDAO.list();
		
		for (Software soft : softs) {
			Map<String, Object> m = new LinkedHashMap<String, Object>();
			m.put("SoftwareId", soft.getId());
			m.put("SoftwareName", soft.getName());
			m.put("SoftwareDesc", soft.getDescription());
			obj.add(m);
		}
		
        w.write(JSONValue.toJSONString(obj));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
