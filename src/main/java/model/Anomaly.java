package model;

import java.sql.Date;

/*
 * Modèle des anomalies : une anomalie possède un identifiant, une ressource
 * associée (référencé par son identifiant), une description et un état.
 * Son état peut être "en attente" ou "résolu".
 * Elle possède également une date de déclaration, date à laquelle l'anomalie
 * est declaré par un utilisateur. Enfin, si elle a été résolu, elle possède
 * une date de résolution.
 * Pour chacun de ces attributs un getter et un setter sont définis.
 */
public class Anomaly {
	
	// Inner private enum for the ressource status
	
	public enum Status {
	    PENDING,
	    RESOLVED;
	}
	
	// Attributes

	private int id;
	private int ressourceId;
	private String description; 
	private Status state;
	private Date declarationDate;
	private Date resolutionDate;
	
	public Anomaly(String description, int ressourceId) {
		if (description == null) {
			throw new AssertionError("Invalid arguments !");
		}
		this.id = -1;
		this.description = description;
		this.state = Status.PENDING;
		this.ressourceId = ressourceId;
	}
	
	// Getters
	
	public int getID() {
		return this.id;
	}
	
	public int getRessource() {
		return this.ressourceId;
	}
	
	public int getStatus() {
		return this.state.ordinal();
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Date getDeclarationDate() {
		return this.declarationDate;
	}
	
	public Date getResolutionDate() {
		return this.resolutionDate;
	}
	
	// Setters
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setRessource(int newRessourceId) {
		this.ressourceId = newRessourceId;
	}
	
	public void setStatus(int integerCode) {
		boolean valid = false;
		for (Status s : Status.values()) {
			if (s.ordinal() == integerCode) {
				this.state = s;
				valid = true;
			}
		}
		if (!valid) {
			throw new AssertionError("Invalid arguments !");
		}
	}
	
	public void setDescription(String newDescription) {
		if (newDescription == null) {
			throw new AssertionError("Invalid arguments !");
		}
		this.description = newDescription;
	}
	
	public void setDeclarationDate(Date declared) {
		if (declared == null) {
			throw new AssertionError("Invalid arguments !");
		}
		this.declarationDate = declared;
	}
	
	public void setResolutionDate(Date resolved) {
		
		// could be null because it's isn't resolved yet
//		if (resolved == null) {
//			throw new AssertionError("Invalid arguments !");
//		}
		this.resolutionDate = resolved;
	}
	
	// Utils functions
	
	public boolean isResolved() {
		return this.state == Status.RESOLVED;
	}
}