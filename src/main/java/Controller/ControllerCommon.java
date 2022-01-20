package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Admin;
import model.Maintainer;

public class ControllerCommon {

	private static final String ERROR_ATTRIBUTE = "error-msg";

	// redirection tool
	
	public static void forwardTo(String url, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		if (requestDispatcher != null) {
			requestDispatcher.forward(request, response);
		}
	}
	
	// username printing 
	
	public static String printUsername(HttpServletRequest request) {
		String result = "Bubule le Destructeur"; // I had to choose a default name 
												 // this one is pretty sweet
		
		HttpSession session = request.getSession();
		if (session != null) {
			Maintainer m = (Maintainer)session.getAttribute("maintainer");
			Admin adm = (Admin)session.getAttribute("admin");
			if (m != null) {
				result = m.getUsername();
			} else if (adm != null) {
				result = adm.getUsername();
			}
		}
		
		return result;
	}
	// error printing 
	
	public static String formatMsg(Severity level, String errorMsg) {
		String msg = null;
		switch (level) {

		case SUCCESS:
			msg = "<div class='alert alert-success' style='top: 15px; left: 15px;'"
				 +" role='alert'>Info: "+errorMsg+"</div>";
			break;

		case INFO:
			msg = "<div class='alert alert-info' style='top: 15px; left: 15px;'"
				 +"role='alert'>Info: "+errorMsg+"</div>";
			break;

		case WARNING:
			msg = "<div class='alert alert-warning' style='top: 15px; left: 15px;'"
				 +"role='alert'>Attention: "+errorMsg+"</div>";
			break;

		case ERROR:
			msg = "<div class='alert alert-danger' style='top: 15px; left: 15px;'"
				 +"role='alert'>Erreur: "+errorMsg+"</div>";
			break;

		default:
			break;
		}
		
		return msg;
	}

	public static void setError(HttpServletRequest request, String errorMsg) {
		setError(request, Severity.ERROR, errorMsg);
	}
	
	public static void setError(HttpServletRequest request, Severity level, String errorMsg) {
		if (request != null) {
			HttpSession session = request.getSession();
			if (session != null) {
				session.setAttribute(ERROR_ATTRIBUTE, formatMsg(level, errorMsg));
			}
		}
	}

	public static void removeError(HttpServletRequest request) {
		if (request != null) {
			HttpSession session = request.getSession();
			if (session != null) {
				session.removeAttribute(ERROR_ATTRIBUTE);
			}
		}
	}

	public static String getErrorMsg(HttpServletRequest request) {
		String msg = null;
		if (request != null) {
			HttpSession session = request.getSession();
			if (session != null) {
				msg = (String) session.getAttribute(ERROR_ATTRIBUTE);
			}
		}
		return msg;
	}

	public static String printErrorMsg(HttpServletRequest request) {
		String result = "";
		String msg = getErrorMsg(request);
		
		// remove the error from the session 
		removeError(request);
		
		if (msg != null) {
			// add script to make div fade out
			result+= "<script type='text/javascript'>"
			+ "			    let intervalID=0;"
			+ "			    window.onload=fadeout;"
			+ "			    function fadeout(){"
			+ "			      intervalID = setInterval(() => { "
			+ "					clearInterval(intervalID); "
			+ "					intervalID = setInterval(hide, 30);"
			+ "				  }, 3000);"
			+ "			    }"
			+ "			    function hide(){"
			+ "			      let elem=document.getElementById('popup-msg');"
			+ "			      let opacity ="
			+ "			 		Number(window.getComputedStyle(elem).getPropertyValue('opacity'));"
			+ "			      if(opacity>0){"
			+ "			         opacity=opacity-0.1;"
			+ "			         elem.style.opacity=opacity;"
			+ "			      } else {"
			+ "			         clearInterval(intervalID); "
			+ "			      }"
			+ "			    }"
			+ "      </script>";
			result += "<div id='popup-msg' class='fixed-top w-25'>" + msg + "</div>";
		}
		return result;
	}

	public enum Severity {
		SUCCESS, INFO, WARNING, ERROR;
	}
}
