package Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ErrorMsg {

	private static final String ERROR_ATTRIBUTE = "error-msg";
	private static final String MSG_TIMER 		= "4000";
	
	public static final String MSG_AUTHENTIFICATED 				= "Authentification reussie";
	
	public static final String MSG_LICENCE_REQUEST_SUCCESS 		= "Votre demande de licence à été transmise";
	public static final String ERROR_LICENCE_ALREADY_OWNED 		= "Vous possédez déjà ce logiciel";
	public static final String ERROR_LICENCE_INVALID_PARAMETERS = "Les paramètres envoyés sont invalides";
	
	public enum Severity {
		SUCCESS, INFO, WARNING, ERROR;
	}
	
	public static String formatMsg(Severity level, String errorMsg) {
		String msg = null;
		switch (level) {

		case SUCCESS:
			msg = "<div class='alert alert-success' style='top: 15px; left: 15px;'" + " role='alert'>Info: " + errorMsg
					+ "</div>";
			break;

		case INFO:
			msg = "<div class='alert alert-info' style='top: 15px; left: 15px;'" + "role='alert'>Info: " + errorMsg
					+ "</div>";
			break;

		case WARNING:
			msg = "<div class='alert alert-warning' style='top: 15px; left: 15px;'" + "role='alert'>Attention: "
					+ errorMsg + "</div>";
			break;

		case ERROR:
			msg = "<div class='alert alert-danger' style='top: 15px; left: 15px;'" + "role='alert'>Erreur: " + errorMsg
					+ "</div>";
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
			result += "<script type='text/javascript'>" + "			    let intervalID=0;"
					+ "			    window.onload=fadeout;" + "			    function fadeout(){"
					+ "			      intervalID = setInterval(() => { "
					+ "					clearInterval(intervalID); "
					+ "					intervalID = setInterval(hide, 30);" + "				  }, "+MSG_TIMER+");"
					+ "			    }" + "			    function hide(){"
					+ "			      let elem=document.getElementById('popup-msg');" + "			      let opacity ="
					+ "			 		Number(window.getComputedStyle(elem).getPropertyValue('opacity'));"
					+ "			      if(opacity>0){" + "			         opacity=opacity-0.1;"
					+ "			         elem.style.opacity=opacity;" + "			      } else {"
					+ "			         clearInterval(intervalID); " + "			      }" + "			    }"
					+ "      </script>";
			result += "<div id='popup-msg' class='fixed-top w-25'>" + msg + "</div>";
		}
		return result;
	}

}
