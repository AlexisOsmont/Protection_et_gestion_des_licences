package Controller;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import DAO.AnomalyDAO;
import DAO.MaintainerDAO;
import DAO.RessourceDAO;
import Parser.ParserUtils;
import model.Anomaly;
import model.Maintainer;
import model.Ressource;

public class RessourceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Controller
	public RessourceController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// work only if user has set the session
		HttpSession session = request.getSession();

		String strId = null;
		String action = null;

		// work only if user has set the session
		if (session != null) {

			if (request.getQueryString() != null) {
				// parse the query string
				strId = request.getParameter("id");
				action = request.getParameter("action");

				if (action == null && strId != null) {
					// user is requesting a 'real' ressource
					ressourcePageOf(strId, request, response);
					
				} else if (action != null && strId == null) {
					// user send data to add anomaly
					addAnomalyToRessource(request, response);
				} 
			} else {
				// user is just requesting the template ressource page
				request.setAttribute("res-description", "#Placeholder");
				request.setAttribute("res-location", "#Placeholder");
				request.setAttribute("res-maintainer", "#Placeholder");
				request.setAttribute("res-id", "#Placeholder");
				request.setAttribute("disp-info", true);
				
				// create an empty list
				request.setAttribute("res-anomaly-list", createAnomalyList(-1));
				ControllerCommon.forwardTo("/WEB-INF/ressource.jsp", request, response);
			}

		} else {
			// if not redirect him to the home page
			ControllerCommon.forwardTo("/WEB-INF/home.jsp", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	private void ressourcePageOf(String strId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(strId);
		Ressource res = RessourceDAO.get(id);
		if (res != null) {
			Maintainer m = MaintainerDAO.get(res.getMaintainer());
			request.setAttribute("res-description", res.getDescription());
			request.setAttribute("res-location", res.getLocation());
			request.setAttribute("res-maintainer", m.getUsername());
			
			request.setAttribute("res-id", Integer.toString(res.getID()));
			request.setAttribute("disp-info", false);

			request.removeAttribute("res-anomaly-list");
			request.setAttribute("res-anomaly-list", createAnomalyList(id));
			ControllerCommon.forwardTo("/WEB-INF/ressource.jsp", request, response);
		} else {
			// this id doesn't correspond to an id -> warn user
			ControllerCommon.setError(request, ControllerCommon.Severity.WARNING, "La ressouce indiqué n'existe pas");
			response.sendRedirect(request.getContextPath() + "/home");
		}
	}

	private void addAnomalyToRessource(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// setup error msg just in case
		ControllerCommon.setError(request, "Impossible d'ajouter l'anomalie");
		String redirect = request.getContextPath() + "/ressource";

		// user send form data
		String resId = request.getParameter("resid");
		String knownAnomaly = request.getParameter("known-anomaly");
		// escape user input to protect against XSS
		String anomalyDesc = ParserUtils.escapeHtml(request.getParameter("anomaly-desc"));

		// user either choose a anomaly that already exist
		// or created a new one
		Anomaly newAnomaly = null;
		int id = -1;

		if (knownAnomaly != null && knownAnomaly.equals("none") && anomalyDesc != null && anomalyDesc.length() > 2
				&& anomalyDesc.length() < 64) {
			// check that the description of the anomaly isn't too short
			// or too long

			id = Integer.parseInt(resId);
			if (id >= 0) {
				newAnomaly = new Anomaly(anomalyDesc, id);

				AnomalyDAO.insert(newAnomaly);
				ControllerCommon.setError(request, ControllerCommon.Severity.SUCCESS, "Anomalie Ajouté");
				redirect += "?id=" + id;
			}

		} else if (knownAnomaly != null && !knownAnomaly.equals("none")) {

			id = Integer.parseInt(knownAnomaly);
			newAnomaly = AnomalyDAO.get(id);

			// if anomaly was resolved we just need to update it
			if (newAnomaly.isResolved()) {
				newAnomaly.setStatus(Anomaly.Status.PENDING.ordinal());
				AnomalyDAO.update(newAnomaly);
			}
			// if the anomaly wasn't resolved we don't need to do anything
			ControllerCommon.setError(request, ControllerCommon.Severity.SUCCESS, "Anomalie Ajouté");
			redirect += "?id=" + id;
		}

		response.sendRedirect(redirect);
	}

	// use anomaly list to 'hide' the ressource id
	private String createAnomalyList(int ressourceId) {
		String result = "<input type='text' style='display:none' name='resid' value='" + ressourceId + "'/> "
				+ "<select name='known-anomaly' class='custom-select' id='inputGroupSelect04'>"
				+ "		<option value='none' selected>Incidents répertoriés...</option>";

		if (ressourceId != -1) {
			List<Integer> list = AnomalyDAO.getList(ressourceId);
			for (Integer id : list) {
				Anomaly a = AnomalyDAO.get(id);
				result += "<option value='" + a.getID() + "'>" + a.getDescription() + "</option>";
			}
		}
		result += "</select>";

		return result;
	}
}
