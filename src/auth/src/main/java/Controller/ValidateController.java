package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entity.User;
import Model.TicketsModel;
import Utils.Validator;

public class ValidateController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	// Controller
	public ValidateController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String ticket = request.getParameter("ticket");
		
		// does ticket exists 
		if (ticket == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter w = response.getWriter();
			w.write("Ticket manquant.");
			return;
		}
		
		// test ticket structure
		try {
			Validator.checkTicket();
		} catch (AssertionError e) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			PrintWriter w = response.getWriter();
			w.write("Ticket non valide.");
			return;
		}
		
		
		// Hardcode for now
		if (ticket.equals("123456789")) {
			//valid ticket
			response.setStatus(HttpServletResponse.SC_OK);
			
			response.setHeader("Content-Type", "application/json");
			
			//set user information
			PrintWriter w = response.getWriter();
			w.write("{\"username\":\"admin\",\"mail\":\"admin@admin.fr\"}");
			return;
		}
		if (ticket.equals("987654321")) {
			//valid ticket
			response.setStatus(HttpServletResponse.SC_OK);
			
			response.setHeader("Content-Type", "application/json");
			
			//set user information
			PrintWriter w = response.getWriter();
			w.write("{\"username\":\"client\",\"mail\":\"client@client.fr\"}");
			return;
		}
		
		// TODO : Récupérer le ticket si valide : recuperer info user.
		try {
			if (!TicketsModel.checkTicket(ticket)) {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				PrintWriter w = response.getWriter();
				w.write("Ticket inexistant.");
				return;
			}
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			PrintWriter w = response.getWriter();
			w.write("Erreur interne lors de la validation du ticket. " + e.getMessage());
			return;
		}
		
		// A partir d'ici le ticket est valide.
		// Récupérons les informations de l'utilisateur.
		
		User user;
		try {
			user = TicketsModel.getUserWithTicket(ticket);
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			PrintWriter w = response.getWriter();
			w.write("Erreur interne lors de la validation du ticket " + ticket + ". " + e.getMessage());
			return;
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Content-Type", "application/json");
		//set user information
		PrintWriter w = response.getWriter();
		w.write("{\"username\":\"" + user.getUsername() + "\",\"mail\":\"" + user.getMail() + "\"}");
		return;
	}
	
}