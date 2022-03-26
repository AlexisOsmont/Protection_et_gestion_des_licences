package model;

import java.util.Date;

public class Licence {

	// Inner private enum for the ressource status

	public enum Status {
		PENDING("En Attente"), 	// the licence was created but is not activated yet
		ACTIVATED("Activé"), 	// the licence is created and activated
		EXPIRED("Expiré"); 	    // the licence is expired
		
		String str;
		Status(String reason) {
			this.str = reason;
		}
		
		//override the inherited method
		@Override
		public String toString() {
			return str;
		}
	}

	// Attributes
	public static final int ID_NOT_SET = -1;
	private int id;
	private String hardwareId;
	private Status state;
	private Date validity;
	private int clientId;
	private int softwareId;

	/**
	 * @param clientId
	 * @param softwareId
	 */
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
	public int getId() {
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
		return softwareId;
	}

	/**
	 * @return the status of the licence object
	 */
	public int getStatus() {
		return state.ordinal();
	}
	
	public String getStatusString() {
		return state.toString();
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
	public void setId(int id) {
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
	 * @throws AssertionError
	 */
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
