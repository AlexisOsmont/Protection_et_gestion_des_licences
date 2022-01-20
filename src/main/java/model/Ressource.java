package model;

import java.util.List;

/*
 * Modèle des ressources : les ressources possèdent un identifiant, une
 * localisation, une description, une listes d'anomalies (référencées par
 * leurs identifiants) et sont associées à un responsable de maintenance.
 * Pour chacun des ces attributs un getter et un setter sont définis.
 */
public class Ressource {
	
	// Attributes

	private int id;
	private int maintainerId;
	private String location;
	private String description;
	private List<Integer> anomalyList;
	
	// Constructor
	
	public Ressource(String location, String description,
			int maintainerId) {
		if (location == null || description == null) {
			throw new AssertionError("Invalid arguments !");
		}
		this.id = -1;
		this.maintainerId = maintainerId;
		this.location = location;
		this.description = description;
		this.anomalyList = null;
	}
	
	// Getters
	
	public int getID() {
		return id;
	}
	
	public int getMaintainer() {
		return maintainerId;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<Integer> getAnomalyList() {
		return anomalyList;
	}
	
	// Setters
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setMaintainer(int newMaintainerId) {
		this.maintainerId = newMaintainerId;
	}
	
	public void setLocation(String newLocation) {
		if (newLocation == null) {
			throw new AssertionError("A ressource need a valid location !");
		}
		this.location = newLocation;
	}
	
	public void setDescription(String newDescription) {
		if (newDescription == null) {
			throw new AssertionError("A ressource need a valid description !");
		}
		this.description = newDescription;
	}
	
	public void setAnomalyList(List<Integer> anomalies) {
		if (anomalies == null) {
			throw new AssertionError("Invalid arguments !");
		}
		this.anomalyList = anomalies;
	}
}