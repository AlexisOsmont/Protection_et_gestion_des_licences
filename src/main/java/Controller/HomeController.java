package Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import DAO.AnomalyDAO;
import DAO.MaintainerDAO;
import DAO.RessourceDAO;
import model.Anomaly;
import model.Maintainer;
import model.Ressource;

import java.util.*;

public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Controller
	public HomeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// create a session for the user
		HttpSession session = request.getSession(true);
		
		if (session.isNew()) {
			response.sendRedirect(request.getContextPath() + "/home");
		} else {
			// serve him the home page
			
			// we need to create the 'last resolved anomaly'
			request.setAttribute("last-resolved-anomaly", createLastResolvedAnomalyList());
			ControllerCommon.forwardTo("/WEB-INF/home.jsp", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	// tools 
	
	private String createLastResolvedAnomalyList() {
		String result = "";
		List<Integer> lastResolved = AnomalyDAO.getLastResolvedList(3);
		if (lastResolved != null) {
			for (Integer id : lastResolved) {
				Anomaly a = AnomalyDAO.get(id);
				Ressource r = RessourceDAO.get(a.getRessource());
				Maintainer m = MaintainerDAO.get(r.getMaintainer());
				
				result +="<div class='media text-muted pt-3'>"
				+ "			<p"
				+ "				class='mr-2 rounded'"
				+ "				style='width: 32px; height: 32px; background-color: #17a2b8'"
				+ "				data-holder-rendered='true'></p>"
				+ "			<p"
				+ "				class='media-body pb-3 mb-0 small lh-125 border-bottom border-gray'>"
				+ "				<strong class='text-left d-block text-gray-dark'>@Resp. Maintenance#"
				+					m.getUsername() + " - " + a.getResolutionDate() + "</strong>"
				+ 					"L'anomalie '" + a.getDescription() + "' de la ressource '" + r.getDescription()
				+					"' a été résolue."
				+ "			</p>"
				+ "		</div>";
			}	
		} else {
			result += "<div class='media text-muted pt-3'>Aucune Anomalie détéctée</div>";
		}		
		return result;
	}
}
