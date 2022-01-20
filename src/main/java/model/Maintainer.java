package model;

import java.util.List;

/*
 * Modèle des responsables de maintenance : hérite d'un utilisateur. En plus
 * des caractéristiques d'un utilisateur, un responsable de maintenance possède
 * une listes de ressoures (référencées par leurs identifiants) dont il est
 * responsable.
 * Il possède également un statut qui permet de savoir s'il a été validé par
 * l'administrateur et donc s'il possède effectivement les droits d'un
 * responsable de maintenance.
 * Un getter et un setter pour cette liste de ressources sont donc définis.
 */
public class Maintainer extends User {
	
	// Private enum for the maintainer status
	
	public enum Status {
		PENDING,
		ACTIVE,
		INACTIVE;
	}
	
	// Attributes
	
	private List<Integer> ressourceList;
	private Status state;
	
	// Constructor
	
	public Maintainer(String username, String email, String password) {
		super(username, email, password);
		ressourceList = null;
		state = Status.PENDING;
	}
	
	// Getter
	
	public List<Integer> getRessourceList() {
		return ressourceList;
	}
	
	public int getStatus() {
		return this.state.ordinal();
	}
	
	// Setter
	
	public void setRessourcesList(List<Integer> ressources) {
		if (ressources == null) {
			throw new AssertionError("Invalid arguments !");
		}
		this.ressourceList = ressources;
	}
	
	public void setStatus(int status) {
		boolean valid = false;
		for (Status s : Status.values()) {
			if (s.ordinal() == status) {
				this.state = s;
				valid = true;
			}
		}
		if (!valid) {
			throw new AssertionError("Invalid arguments !");
		}
	}
	
	// Utils functions
	
	public boolean isActive() {
		return this.state == Status.ACTIVE;
	}
}
