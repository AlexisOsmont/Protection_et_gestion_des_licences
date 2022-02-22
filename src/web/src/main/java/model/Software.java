package model;

public class Software {

	// Attributes
	public static final int ID_NOT_SET = -1;
	private int id;
	private String name;
	private String description;

	public Software(String name, String description) {
		this.id = ID_NOT_SET;
		this.name = name;
		this.description = description;
	}

	// Getters

	/**
	 * return the id of the software object if set, if not return Software.ID_NOT_SET
	 * 
	 * @return the id of the software object
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return the name of the software object
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description of the software object
	 */
	public String getDescription() {
		return description;
	}

	// Setters
	
	/**
	 * Update the id of the software object
	 * 
	 * @param id new id for this software
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Update the name of the software object
	 * 
	 * @param newName the new name for this software
	 */
	public void setUsername(String newName) {
		this.name = newName;
	}

	/**
	 * Update the description of the software object
	 * 
	 * @param newDescription the new description for the software
	 */
	public void setEmail(String newDescription) {
		this.description = newDescription;
	}
}
