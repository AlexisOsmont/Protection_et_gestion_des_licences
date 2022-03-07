package model;

public class Software {

	// Attributes
	public static final int ID_NOT_SET = -1;
	
	private int id;
	private String name;
	private String description;
	private byte[] imgData;
	private int price;
	private int priceMultiplier;

	/**
	 * @param name
	 * @param description
	 */
	public Software(String name, String description) {
		this.id = ID_NOT_SET;
		this.name = name;
		this.description = description;
		this.imgData = null;
		this.price = 0;
		this.priceMultiplier = 0;
	}

	// Getters

	/**
	 * return the id of the software object if set, if not return
	 * Software.ID_NOT_SET
	 * 
	 * @return the id of the software object
	 */
	public int getId() {
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

	/**
	 * @return the price of the software object
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @return the price multiplier of the software object
	 */
	public int getPriceMultiplier() {
		return priceMultiplier;
	}

	/**
	 * @return the image of the software object as byte array
	 */
	public byte[] getImg() {
		return imgData;
	}

	// Setters

	/**
	 * Update the id of the software object
	 * 
	 * @param id new id for this software
	 */
	public void setId(int id) {
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

	/**
	 * Update the price of the software object
	 * 
	 * @param newPrice the new price for the software
	 */
	public void setPrice(int newPrice) {
		this.price = newPrice;
	}

	/**
	 * Update the price multiplier of the software object
	 * 
	 * @param newMultiplier the new multiplier of the price for the software
	 */
	public void setPriceMultiplier(int newMultiplier) {
		this.priceMultiplier = newMultiplier;
	}
	
	/**
	 * Update the image of the software object 
	 * @param newImgData the new image for the software 
	 */
	public void setImg(byte[] newImgData) {
		this.imgData = newImgData;
	}
}
