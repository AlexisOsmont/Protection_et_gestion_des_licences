package model;

import java.sql.Date;

public class Licence {

	// Inner private enum for the ressource status

	public enum Status {
		PENDING, 	// the licence was created but is not activated yet
		ACTIVATED, 	// the licence is created and activated
		EXPIRED; 	// the licence is expired
	}

	// Attributes
	public static final int ID_NOT_SET = -1;
	private int id;
	private String hardwareId;
	private Status state;
	private Date validity;
	private int clientId;
	private int softwareId;

	public Licence(int clientId, int softwareId) {
		// these attributes are mandatory
		this.id = ID_NOT_SET;
		this.clientId = clientId;
		this.softwareId = softwareId;
		this.state = Status.PENDING;
		// these attributes may not be set
		this.hardwareId = null;
		this.validity = null;
	}

	// Getters

	/**
	 * return the id of the licence object if set, if not return Licence.ID_NOT_SET
	 * 
	 * @return the id of the licence object
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return the id of the client which buy this licence
	 */
	public int getClientId() {
		return clientId;
	}

	/**
	 * @return the id of the software protected by this licence
	 */
	public int getSoftwareId() {
		return clientId;
	}

	/**
	 * @return the status of the licence object
	 */
	public Status getState() {
		return state;
	}

	/**
	 * @return the hardware id of the machine of the client who bought this licence
	 *         if set, return null if not.
	 */
	public String getHardwareId() {
		return hardwareId;
	}

	/**
	 * @return the date until which the licence is valid
	 */
	public Date getValidity() {
		return validity;
	}

	// Setters

	/**
	 * Update the id of the licence object
	 * 
	 * @param id new id for this licence
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Update the client id of the licence object
	 * 
	 * @param clientId the new id of a client for this licence
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	/**
	 * Update the software id of the licence object
	 * 
	 * @param softwareId the new id of a software for this licence
	 */
	public void setSoftwareId(int softwareId) {
		this.softwareId = softwareId;
	}

	/**
	 * Update the status of this licence object
	 * 
	 * @param state the new state of this licence
	 */
	public void setState(Status state) {
		this.state = state;
	}

	/**
	 * Update the hardware id of the licence object
	 * 
	 * @param hardwareId the new hardware id for this licence
	 */
	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	/**
	 * Update the validity of the licence object
	 * 
	 * @param newDate the new validity for this licence
	 */
	public void setValidity(Date newDate) {
		this.validity = newDate;
	}
}
