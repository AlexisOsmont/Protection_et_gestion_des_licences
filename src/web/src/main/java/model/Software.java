package model;

public class Software {

	// attributes
	
	private int id;
	private String name;
    private String desc;
	
	// constructor
	
	public Software(int id, String name, String desc) {
		this.desc = desc;
		this.id = id;
		this.name = name;
	}
	
	// getters
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return desc;
	}
	
	// setters
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
