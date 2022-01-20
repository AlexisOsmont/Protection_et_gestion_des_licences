package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import DAO.AnomalyDAO;
import DAO.MaintainerDAO;
import DAO.RessourceDAO;

import model.Maintainer;
import model.Admin;
import model.Anomaly;
import model.Ressource;

// Interface for passing a function to createRessourceList method
interface Callable {
	public String call(String path, Integer resId);
}

public class PanelController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Controller
	public PanelController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processConnection(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processConnection(request, response);
	}

	// tools

	private String createPendingAnomalyList(Maintainer m, String ref) {
		String result = "";

		List<Integer> listOfRessource = RessourceDAO.getList(m.getID());
		for (Integer resID : listOfRessource) {

			Ressource res = RessourceDAO.get(resID);
			List<Integer> listOfAnomaly = AnomalyDAO.getList(resID);
			for (Integer AnomalyID : listOfAnomaly) {

				Anomaly a = AnomalyDAO.get(AnomalyID);
				if (!a.isResolved()) {

					result += "<div class='col'>" + "<div class='card bg-dark shadow-sm'>" + "<div class='card-body'>"
							+ "<p class='card-text'>" + "<strong>Ressource</strong>: <br>" + res.getDescription()
							+ "<br> <br>" + a.getDescription() + "</p>"
							+ "<div class='d-flex justify-content-between align-items-center'>" + "<a href='" + ref
							+ a.getID() + "' class='btn btn-sm btn-outline-secondary'>Résolu</a>"
							+ "<small class='text-muted'>" + a.getDeclarationDate() + "</small>" + "</div>" + "</div>"
							+ "</div>" + "</div>";
				}
			}
		}

		return result;
	}

	private String createPaginationForTable(int pageId, int nbOfElem, String ref) {
		String result = "<div class='row'>" + "<div class='col-12 d-flex justify-content-end'>"
				+ "<nav aria-label='pagination'>" + "<ul class='pagination'>";

		// we assume that there will be always 5 cells in the pagination
		// now there are 3 cases:

		// page <= 2 -> active link is cell(1) or cell(2)
		// page >= 3 && page < list.size - 2 -> active link is in the middle
		// page >= list.size - 2 -> active link is cell(4) or cell(5)

		// (note: pageId is in [0...N-1])

		boolean[] arr = new boolean[] { false, false, false, false, false };
		int[] ids = new int[] { 0, 0, 0, 0, 0 };

		if (pageId <= 1) {
			arr[pageId] = true;
			ids[0] = 0;
			ids[1] = 1;
			ids[2] = 2;
			ids[3] = 3;
			ids[4] = 4;

		} else if (pageId >= 2 && pageId < nbOfElem - 2) {
			arr[2] = true;
			ids[0] = pageId - 2;
			ids[1] = pageId - 1;
			ids[2] = pageId;
			ids[3] = pageId + 1;
			ids[4] = pageId + 2;

		} else if (pageId >= nbOfElem - 2) {
			arr[nbOfElem - pageId] = true;
			ids[0] = nbOfElem - 4;
			ids[1] = nbOfElem - 3;
			ids[2] = nbOfElem - 2;
			ids[3] = nbOfElem - 1;
			ids[4] = nbOfElem;
		}

		result += (arr[0] ? "<li class='active'>" : "<li>") + "<a class='page-link' href='" + ref + ids[0] + "'>"
				+ (ids[0] + 1) + "</a></li>"

				+ (arr[1] ? "<li class='active'>" : "<li>") + "<a class='page-link' href='" + ref + ids[1] + "'>"
				+ (ids[1] + 1) + "</a></li>"

				+ (arr[2] ? "<li class='active'>" : "<li>") + "<a class='page-link' href='" + ref + ids[2] + "'>"
				+ (ids[2] + 1) + "</a></li>"

				+ (arr[3] ? "<li class='active'>" : "<li>") + "<a class='page-link' href='" + ref + ids[3] + "'>"
				+ (ids[3] + 1) + "</a></li>"

				+ (arr[4] ? "<li class='active'>" : "<li>") + "<a style='border-top-right-radius: .25rem; "
				+ "border-bottom-right-radius: .25rem;' " + "class='page-link' href='" + ref + ids[4] + "'>"
				+ (ids[4] + 1) + "</a></li>";

		result += "</ul>" + "</nav>" + "</div>" + "</div>";
		return result;
	}

	private String createRessourceList(Maintainer m, String action, String pageAttr, String path, Callable f) {

		int pageId = pageAttr == null ? 0 : Integer.parseInt(pageAttr);
		int itemPerPage = 5;

		// get the list of ressource
		List<Integer> listOfRes = RessourceDAO.getList(m.getID());

		int lowerBound = pageId * itemPerPage;
		int upperBound = Math.min((pageId + 1) * itemPerPage, listOfRes.size());

		if (lowerBound > upperBound) { // user try to request an out of bound page
			// serve him the first one
			lowerBound = 0;
			upperBound = Math.min(itemPerPage, listOfRes.size());
			;
			pageId = 0;
		}

		// get the sublist of ressource user want to see
		List<Integer> subList = listOfRes.subList(lowerBound, upperBound);

		String result = "<table" + "		class='table table-hover table-striped table-dark table-bordered'>"
				+ "		<thead>" + "			<tr>" + "				<th scope='col'>ID</th>"
				+ "				<th scope='col'>Nom</th>" + "				<th scope='col'>Localisation</th>"
				+ "				<th style='white-space: nowrap; width: 10%;' scope='col'>Action</th>"
				+ "			</tr>" + "		</thead>" + "		<tbody>";

		for (Integer resId : subList) {

			// create a row for each ressource of the sublist
			Ressource elem = RessourceDAO.get(resId);
			
//			result += "<tr>" + "<th scope='row'>" + resId + "</th>" + "<td>" + elem.getDescription() + "</td>" + "<td>"
//					+ elem.getLocation() + "</td>" + "<td>" + "<div class='d-flex justify-content-center'>"
//					+ "<a href='" + path + "/PanelController?action=remove&resid=" + resId + "' "
//					+ "class='btn btn-sm btn-danger oneliner-btn'><span" + "aria-hidden='true'>&times;</span></a>"
//					+ "</div>" + "</td>" + "</tr>";
			
			result += "<tr>" + "<th scope='row'>" + resId + "</th>" + "<td>" + elem.getDescription() + "</td>" + "<td>"
					+ elem.getLocation() + "</td>" + "<td>" + "<div class='d-flex justify-content-center'>"
					+ f.call(path, resId)
					+ "</div>" + "</td>" + "</tr>";
		}

		result += "</tbody></table>";

		// then add the pagination
		result += createPaginationForTable(pageId, listOfRes.size(), path + "/PanelController?action="+action+"&page=");

		return result;
	}

	private String createMaintainerList(Admin adm, String pageAttr, String path) {
		int pageId = pageAttr == null ? 0 : Integer.parseInt(pageAttr);
		int itemPerPage = 5;

		// get the list of Maintainer
		List<Integer> listOfMain = MaintainerDAO.getList(adm.getID());

		int lowerBound = pageId * itemPerPage;
		int upperBound = Math.min((pageId + 1) * itemPerPage, listOfMain.size());

		if (lowerBound > upperBound) { // user try to request an out of bound page
			// serve him the first one
			lowerBound = 0;
			upperBound = Math.min(itemPerPage, listOfMain.size());
			pageId = 0;
		}

		// get the sublist of Maintainer user want to see
		List<Integer> subList = listOfMain.subList(lowerBound, upperBound);

		String result = "<table" + "		class='table table-hover table-striped table-dark table-bordered'>"
				+ "		<thead>" + "			<tr>" + "				<th scope='col'>ID</th>"
				+ "				<th scope='col'>Nom</th>" + "				<th scope='col'>Mail</th>"
				+ "				<th style='white-space: nowrap; width: 10%;' scope='col'>Action</th>"
				+ "			</tr>" + "		</thead>" + "		<tbody>";

		for (Integer mainId : subList) {

			// create a row for each maintainer of the sublist
			Maintainer elem = MaintainerDAO.get(mainId);
			result += "<tr>" + "<th scope='row'>" + mainId + "</th>" + "<td>" + elem.getUsername() + "</td>" + "<td>"
					+ elem.getEmail() + "</td>" + "<td>" + "<div class='d-flex justify-content-center'>";

			if (elem.isActive()) {
				result += "<a href='" + path + "/PanelController?action=invalidate&mainid=" + mainId + "' "
						+ "class='btn btn-sm btn-danger oneliner-btn'><span aria-hidden='true'>&times;</span></a>";
			} else {
				result += "<a href='" + path + "/PanelController?action=validate&mainid=" + mainId + "' "
						+ "class='btn btn-sm btn-success oneliner-btn'>O</a>";
			}

			result += "</div>" + "</td>" + "</tr>";
		}

		result += "</tbody></table>";

		// then add the pagination
		result += createPaginationForTable(pageId, listOfMain.size(), path + "/PanelController?action=list&page=");

		return result;
	}
	
	private String createMaintainerToRemoveList(Admin adm, String pageAttr, String path, 
			String checkName) {
		int pageId = pageAttr == null ? 0 : Integer.parseInt(pageAttr);
		int itemPerPage = 5;

		// get the list of Maintainer
		List<Integer> listOfMain = MaintainerDAO.getList(adm.getID());

		int lowerBound = pageId * itemPerPage;
		int upperBound = Math.min((pageId + 1) * itemPerPage, listOfMain.size());

		if (lowerBound > upperBound) { // user try to request an out of bound page
			// serve him the first one
			lowerBound = 0;
			upperBound = Math.min(itemPerPage, listOfMain.size());
			pageId = 0;
		}

		// get the sublist of Maintainer user want to see
		List<Integer> subList = listOfMain.subList(lowerBound, upperBound);

		String result = "<table" 
				+ "		class='table table-hover table-striped table-dark table-bordered'>"
				+ "		<thead>" 
				+ "			<tr>" 
				+ "				<th scope='col'>#</th>"
				+ "				<th scope='col'>ID</th>"
				+ "				<th scope='col'>Nom</th>" 
				+ "			</tr>" 
				+ "		</thead>" 
				+ "		<tbody>";

		for (Integer mainId : subList) {

			// create a row for each maintainer of the sublist
			Maintainer elem = MaintainerDAO.get(mainId);
			result += "<tr>" 
					+ "<th scope='row'>" 
					+ "<div>"
					+ "  <input style='margin-left: 15px;' class='form-check-input' type='checkbox' "
					+ "   		name='"+checkName+"' id='"+mainId+"' value='"+mainId+"' aria-label='...'>"
					+ "</div>" 
					+ "</th>" + "<td>" + mainId + "</td>" 
					+ "<td>" + elem.getUsername() + "</td></tr>";
		}

		result += "</tbody></table>";

		// then add the pagination
		result += createPaginationForTable(pageId, listOfMain.size(), path);

		return result;
	}

	private void doHome(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// redirect user on home by default
		String redirect = "/WEB-INF/home.jsp";
		ControllerCommon.setError(request, "Erreur de Session");

		HttpSession session = request.getSession();
		if (session != null) {

			// check if user is a maintainer or an admin
			Maintainer m = (Maintainer) session.getAttribute("maintainer");
			Admin adm = (Admin) session.getAttribute("admin");
			if (m != null) {
				// user is a maintainer
				// we need to generate the home page for him
				ControllerCommon.removeError(request);

				if (m.isActive()) {
					request.setAttribute("pending-anomaly", createPendingAnomalyList(m,
							request.getContextPath() + "/PanelController?action=resolved&anomalyId="));

					redirect = "/WEB-INF/controlPanel/maintainer/maintainerPanel.jsp";
				} else {
					redirect = "/WEB-INF/controlPanel/maintainer/maintainerInactive.jsp";
				}
			} else if (adm != null) {
				// user is an administrator
				// we need to generate the home page for him
				ControllerCommon.removeError(request);
				request.setAttribute("maintainer-list",
						createMaintainerList(adm, request.getParameter("page"), request.getContextPath()));
				redirect = "/WEB-INF/controlPanel/admin/adminPanel.jsp";
			}
		}

		ControllerCommon.forwardTo(redirect, request, response);
	}

	private void doList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// redirect user on home by default
		String redirect = "/WEB-INF/home.jsp";
		ControllerCommon.setError(request, "Erreur de Session");

		HttpSession session = request.getSession();
		if (session != null) {

			// check if user is a maintainer or an admin
			Maintainer m = (Maintainer) session.getAttribute("maintainer");
			Admin adm = (Admin) session.getAttribute("admin");
			if (m != null) {
				// user is a maintainer
				// we need to generate the home page for him

				ControllerCommon.removeError(request);

				if (m.isActive()) {
					request.setAttribute("ressource-list",
							createRessourceList(m, "list", request.getParameter("page"), request.getContextPath(), 
							new Callable() {
								public String call(String path, Integer resId) {
									return "<a href='" + path + "/PanelController?action=remove&resid=" + resId + "' "
											+ "class='btn btn-sm btn-danger oneliner-btn'><span" 
											+ "aria-hidden='true'>&times;</span></a>";
								}
							}));

					redirect = "/WEB-INF/controlPanel/maintainer/maintainerPanelList.jsp";
				} else {
					redirect = "/WEB-INF/controlPanel/maintainer/maintainerInactive.jsp";
				}
			} else if (adm != null) {
				// user is an administrator
				// we need to generate the home page for him
				ControllerCommon.removeError(request);
				request.setAttribute("maintainer-list",
						createMaintainerList(adm, request.getParameter("page"), request.getContextPath()));

				redirect = "/WEB-INF/controlPanel/admin/adminPanel.jsp";
			}
		}

		ControllerCommon.forwardTo(redirect, request, response);
	}

	private void doAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// redirect user on home by default
		String redirect = "/WEB-INF/home.jsp";
		ControllerCommon.setError(request, "Erreur de Session");

		HttpSession session = request.getSession();
		if (session != null) {

			// check if user is a maintainer or an admin
			Maintainer m = (Maintainer) session.getAttribute("maintainer");
			Admin adm = (Admin) session.getAttribute("admin");
			if (m != null) {
				// user is a maintainer

				ControllerCommon.removeError(request);
				if (m.isActive()) {
					redirect = "/WEB-INF/controlPanel/maintainer/maintainerPanelAdd.jsp";
				} else {
					redirect = "/WEB-INF/controlPanel/maintainer/maintainerInactive.jsp";
				}
			} else if (adm != null) {
				// user is an administrator
				// we need to generate the home page for him
				ControllerCommon.removeError(request);
				redirect = "/WEB-INF/controlPanel/admin/adminPanelAdd.jsp";
			}
		}

		ControllerCommon.forwardTo(redirect, request, response);
	}

	private void doAddAnomaly(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// redirect user on home by default
		String redirect = "/WEB-INF/login.jsp";
		ControllerCommon.setError(request, "Erreur de Session");

		HttpSession session = request.getSession();
		if (session != null) {

			// check if user is a maintainer (because for now only maintainer
			// should end up on this page
			Maintainer m = (Maintainer) session.getAttribute("maintainer");
			if (m != null) {
				// user is a maintainer
				// and what to add something in the database

				if (m.isActive()) {
					String resLoc = request.getParameter("localisation");
					String resDesc = request.getParameter("description");

					if (resLoc != null && resDesc != null) {
						// unsure that both location and description where supplied
						Ressource res = new Ressource(resLoc, resDesc, m.getID());
						// try to add it to the DB

						try {
							RessourceDAO.insert(res);

							ControllerCommon.setError(request, ControllerCommon.Severity.SUCCESS, "Ressource Ajouté");

							redirect = "/WEB-INF/controlPanel/maintainer/maintainerPanelAdd.jsp";
						} catch (RuntimeException e) {
						} // failed -> user is redirected to home

					}
				} else {
					redirect = "/WEB-INF/controlPanel/maintainer/maintainerInactive.jsp";
				}
			}
		}

		ControllerCommon.forwardTo(redirect, request, response);
	}

	private void doRemove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// redirect user on home by default
		String redirect = "/WEB-INF/login.jsp";
		ControllerCommon.setError(request, "Erreur de Session");

		HttpSession session = request.getSession();
		if (session != null) {

			// check if user is a maintainer or an admin
			Maintainer m = (Maintainer) session.getAttribute("maintainer");
			Admin adm = (Admin) session.getAttribute("admin");
			if (m != null) {
				// user is a maintainer
				if (m.isActive()) {

					// prepare redirection & error msg in case of error
					ControllerCommon.setError(request, "Impossible de Supprimer la ressource");
					redirect = "/WEB-INF/controlPanel/maintainer/maintainerPanelList.jsp";

					// check which ressource user want to remove
					String resId = request.getParameter("resid");

					if (resId != null) {

						int id = Integer.parseInt(resId);
						// try to remove from the DB
						try {

							Ressource res = RessourceDAO.get(id);
							RessourceDAO.delete(res);

							// don't forget to update the maintainer
							m.setRessourcesList(RessourceDAO.getList(m.getID()));

							ControllerCommon.setError(request, ControllerCommon.Severity.SUCCESS, "Ressource Supprimé");

						} catch (RuntimeException e) {
						}
					}

					// update the view too
					// for now we don't keep track of which page the user was browsing
					// so the best we can do is serve him the first page of the table
					request.setAttribute("ressource-list",
							createRessourceList(m, "list", null, request.getContextPath(), 
							new Callable() {
								public String call(String path, Integer resId) {
									return "<a href='" + path + "/PanelController?action=remove&resid=" + resId + "' "
											+ "class='btn btn-sm btn-danger oneliner-btn'><span" 
											+ "aria-hidden='true'>&times;</span></a>";
								}
							}));

				} else {
					redirect = "/WEB-INF/controlPanel/maintainer/maintainerInactive.jsp";
				}
			} else if (adm != null) {
				// user is an administrator
				// we need to generate the home page for him
				ControllerCommon.removeError(request);
				redirect = "/WEB-INF/controlPanel/admin/adminPanelDel.jsp";
				
				String left = request.getParameter("page-left");
				String right = request.getParameter("page-right");
				
				String leftId = request.getParameter("mainId-left");
				String rightId = request.getParameter("mainId-right");
				
				if (left != null && right != null) {
					
					// user request the list 
					request.setAttribute("maintainer-list-left",
							createMaintainerToRemoveList(adm, left, 
									request.getContextPath() + "/PanelController?action=remove&page-right="
									+right+"&page-left=", "mainId-left"));
					
					request.setAttribute("maintainer-list-right",
							createMaintainerToRemoveList(adm, right, 
									request.getContextPath() + "/PanelController?action=remove&page-left="
									+left+"&page-right=", "mainId-right"));
					
				} else if (leftId != null && rightId != null) {
					// user make a choice and send data
					
					// prepare error msg in case of error
					ControllerCommon.setError(request, "Impossible de Supprimer cette utilisateur");
					
					// check if operation is legal 
					int idToRemove = Integer.parseInt(leftId);
					int idToGive = Integer.parseInt(rightId);
					
					if (idToRemove >= 0 && idToGive >= 0 && idToRemove != idToGive) {
						// give all the ressource list of Maintainer#idToRemove
						// to Maintainer#idToGive then remove Maintainer#idToRemove
						
						Maintainer toRemove = MaintainerDAO.get(idToRemove);
						Maintainer toGive = MaintainerDAO.get(idToGive);
						
						if (toRemove != null && toGive != null) {
							// retrieve the list of the ressource to swap
							List<Integer> resList = RessourceDAO.getList(idToRemove);
							for (Integer id : resList) {
								Ressource res = RessourceDAO.get(id);
								res.setMaintainer(idToGive);
								
								try {
									RessourceDAO.update(res);
								} catch (RuntimeException e) {}
								
							}
							
							// now all the ressources were updated
							// remove the maintainer from the DB
							
							try {
								MaintainerDAO.delete(toRemove);
								ControllerCommon.setError(request, ControllerCommon.Severity.SUCCESS, 
										"Utilisateur supprimé");
							} catch (RuntimeException e) {}
							
						}
					}
					
					// then update the view
					request.setAttribute("maintainer-list-left",
							createMaintainerToRemoveList(adm, null, 
									request.getContextPath() + "/PanelController?action=remove&page-right=0"
									+"&page-left=", "mainId-left"));
					
					request.setAttribute("maintainer-list-right",
							createMaintainerToRemoveList(adm, null, 
									request.getContextPath() + "/PanelController?action=remove&page-left=0"
									+"&page-right=", "mainId-right"));
				} else {
					
					// serve the default page
					request.setAttribute("maintainer-list-left",
							createMaintainerToRemoveList(adm, null, 
									request.getContextPath() + "/PanelController?action=remove&page-right=0"
									+"&page-left=", "mainId-left"));
					
					request.setAttribute("maintainer-list-right",
							createMaintainerToRemoveList(adm, null, 
									request.getContextPath() + "/PanelController?action=remove&page-left=0"
									+"&page-right=", "mainId-right"));
				}
			}
		}

		ControllerCommon.forwardTo(redirect, request, response);

	}
	
	private void addToPrintingList(HttpServletRequest request, String resId) {
		HttpSession session = request.getSession();
		Integer id = Integer.parseInt(resId);
		// add the id to the session
		List<Integer> l = (List<Integer>) session.getAttribute("resList-part");
		if (l == null) {
			// create the list first 
			l = new ArrayList<Integer>();
			session.setAttribute("resList-part", l);
		}
		// add item to list if they are not already in
		if (l.contains(id)) {
			// print an error
			ControllerCommon.setError(request, ControllerCommon.Severity.WARNING, 
					"Ressource déja présente dans la liste d'impression");
		} else {
			l.add(id);
			ControllerCommon.setError(request, ControllerCommon.Severity.INFO, 
					"Ressource ajouté");	
		}
	}
	
	private void removeFromPrintingList(HttpServletRequest request, String resId) {
		HttpSession session = request.getSession();
		Integer id = Integer.parseInt(resId);
		// add the id to the session
		List<Integer> l = (List<Integer>) session.getAttribute("resList-part");
		if (l != null) {
			
			// check if item is in the list
			if (l.contains(id)) {
				// remove it
				l.remove(id);
				ControllerCommon.setError(request, ControllerCommon.Severity.INFO, 
						"Ressource supprimé");
			} else {
				// print an error
				ControllerCommon.setError(request, ControllerCommon.Severity.WARNING, 
						"Ressource absente de la liste d'impression");
	
			}
		}
	}
	
	private void doPrint(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Maintainer m = (Maintainer) (session == null ? null : session.getAttribute("maintainer"));
		if (session != null && m != null) {
			
			if (m.isActive()) {
				String strList = request.getParameter("resList");
				if (strList != null) {
					
					// user want to print a list of ressource
					// retrieve the list in the session
					List<Integer> imgList = (List<Integer>) session.getAttribute("resList-part");
					if (imgList != null) {
						request.setAttribute("img-list", imgList);
						session.removeAttribute("resList-part");
						ControllerCommon.forwardTo("/WEB-INF/printer.jsp", request, response);
					} else {
						ControllerCommon.setError(request, "Un problème est survenu");
						// redirect user to the same page but this time we will 
						// serve him the page, and indicate an error
						response.sendRedirect(request.getContextPath() + "/PanelController?action=print");
					}
				
					
				} else {
					// user is just requesting the print page
						
					// user choose a ressource to add to printing list
					String resIdToAdd = request.getParameter("add-resid");
					String resIdToRemove = request.getParameter("remove-resid");
					
					if (resIdToAdd != null) {
						// add ressource to printing list
						addToPrintingList(request, resIdToAdd);
						
					} else if (resIdToRemove != null) {
						// remove ressource from printing list
						removeFromPrintingList(request, resIdToRemove);
						
					} 
					
					// re-use the list construction
					request.setAttribute("ressource-list",
							createRessourceList(m, "print", request.getParameter("page"), request.getContextPath(), 
							new Callable() {
								
								public String call(String path, Integer resId) {
									List<Integer> l = (List<Integer>) session.getAttribute("resList-part");
									if (l != null && l.contains(resId)) {
										// button to remove
										return "<a href='" + path + "/PanelController?action=print&remove-resid=" + resId + "' "
										+ "class='btn btn-sm btn-danger'><span" 
										+ "aria-hidden='true'>supprimer</span></a>";
									} else {
										// button to add
										return "<a href='" + path + "/PanelController?action=print&add-resid=" + resId + "' "
										+ "class='btn btn-sm btn-info'><span" 
										+ "aria-hidden='true'>ajouter</span></a>";
									}
								}
							}));

					ControllerCommon.forwardTo("/WEB-INF/controlPanel/maintainer/maintainerPanelPrint.jsp", request, response);
				} 	
			} else {
				// redirect user inactive maintainer page
				ControllerCommon.removeError(request);
				ControllerCommon.forwardTo("/WEB-INF/controlPanel/maintainer/maintainerInactive.jsp", request, response);
			}
			
		} else {
			// redirect user on home by default
			ControllerCommon.setError(request, "Erreur de Session");
			ControllerCommon.forwardTo("/WEB-INF/home.jsp", request, response);
		}
	}
	
	private void doSignout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// redirect user on home 
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}
		
		response.sendRedirect(request.getContextPath() + "/home");
	}

	private void doResolved(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// redirect user on home by default
		String redirect = "/WEB-INF/home.jsp";
		ControllerCommon.setError(request, "Erreur de Session");

		HttpSession session = request.getSession();
		if (session != null) {

			// check if user is a maintainer (because for now only maintainer
			// should end up on this page
			Maintainer m = (Maintainer) session.getAttribute("maintainer");
			if (m != null) {
				// user is a maintainer

				// check if he is active or not
				if (m.isActive()) {
					String anomalyId = request.getParameter("anomalyId");

					if (anomalyId != null) {

						// prepare error msg
						ControllerCommon.setError(request, "Impossible de Supprimer l'anomalie");
						redirect = "/WEB-INF/controlPanel/maintainer/maintainerPanel.jsp";

						int id = Integer.parseInt(anomalyId);
						Anomaly a = AnomalyDAO.get(id);
						a.setStatus(Anomaly.Status.RESOLVED.ordinal());
						
						// try to update it from the DB
						try {
							AnomalyDAO.update(a);
							ControllerCommon.setError(request, ControllerCommon.Severity.SUCCESS, "Anomalie Résolu");
						} catch (RuntimeException e) {
						} catch (AssertionError e) {}

						// update the view too
						request.setAttribute("pending-anomaly", createPendingAnomalyList(m,
								request.getContextPath() + "/PanelController?action=resolved&anomalyId="));

					}
				} else {
					redirect = "/WEB-INF/controlPanel/maintainer/maintainerInactive.jsp";
				}
			}
		}

		ControllerCommon.forwardTo(redirect, request, response);
	}

	private void doValidate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// redirect user on home by default
		String redirect = "/WEB-INF/home.jsp";

		ControllerCommon.setError(request, "Erreur de Session");

		HttpSession session = request.getSession();
		if (session != null) {

			Admin adm = (Admin) session.getAttribute("admin");
			if (adm != null) {

				redirect = "/WEB-INF/controlPanel/admin/adminPanel.jsp";
				ControllerCommon.setError(request, "Impossible de valider cette utilisateur");

				// retrieve the id of the maintainer
				String mainId = request.getParameter("mainid");
				if (mainId != null) {

					int id = Integer.parseInt(mainId);
					// try to update the DB
					try {

						Maintainer m = MaintainerDAO.get(id);
						if (!m.isActive()) {
							m.setStatus(Maintainer.Status.ACTIVE.ordinal());
							MaintainerDAO.update(m);
						}

						ControllerCommon.setError(request, ControllerCommon.Severity.SUCCESS, "Utilisateur valider");

					} catch (RuntimeException e) {
					}
				}

				// update the view too
				// for now we don't keep track of which page the user was browsing
				// so the best we can do is serve him the first page of the table
				request.setAttribute("maintainer-list", createMaintainerList(adm, null, request.getContextPath()));
			}
		}

		ControllerCommon.forwardTo(redirect, request, response);
	}

	private void doInvalidate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// redirect user on home by default
		String redirect = "/WEB-INF/home.jsp";

		ControllerCommon.setError(request, "Erreur de Session");

		HttpSession session = request.getSession();
		if (session != null) {

			Admin adm = (Admin) session.getAttribute("admin");
			if (adm != null) {

				redirect = "/WEB-INF/controlPanel/admin/adminPanel.jsp";
				ControllerCommon.setError(request, "Impossible d'invalider cette utilisateur");

				// retrieve the id of the maintainer
				String mainId = request.getParameter("mainid");
				if (mainId != null) {

					int id = Integer.parseInt(mainId);
					// try to update the DB
					try {

						Maintainer m = MaintainerDAO.get(id);
						if (m.isActive()) {
							m.setStatus(Maintainer.Status.INACTIVE.ordinal());
							MaintainerDAO.update(m);
						}

						ControllerCommon.setError(request, ControllerCommon.Severity.SUCCESS, "Utilisateur invalider");

					} catch (RuntimeException e) {
					}
				}

				// update the view too
				// for now we don't keep track of which page the user was browsing
				// so the best we can do is serve him the first page of the table
				request.setAttribute("maintainer-list", createMaintainerList(adm, null, request.getContextPath()));
			}
		}

		ControllerCommon.forwardTo(redirect, request, response);
	}

	private void processConnection(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = null;
		
		if (request.getQueryString() != null) {
			action = request.getParameter("action");
		}

		if (action != null) {
			switch (action) {

			case "home":
				doHome(request, response);
				break;

			case "list":
				doList(request, response);
				break;

			case "add":
				doAdd(request, response);
				break;

			case "remove":
				doRemove(request, response);
				break;

			case "signout":
				doSignout(request, response);
				break;

			// --- Specific to Maintainers --- //

			case "addAnomaly":
				doAddAnomaly(request, response);
				break;

			case "resolved":
				doResolved(request, response);
				break;
				
			case "print":
				doPrint(request, response);
				break;

			// --- Specific to Administrator --- //

			case "validate":
				doValidate(request, response);
				break;

			case "invalidate":
				doInvalidate(request, response);
				break;
				
			default:
				// redirect user on home on default
				ControllerCommon.forwardTo("/WEB-INF/home.jsp", request, response);
				break;
			}
		} else {
			// redirect to home on error
			ControllerCommon.forwardTo("/WEB-INF/home.jsp", request, response);
		}
	}
}
