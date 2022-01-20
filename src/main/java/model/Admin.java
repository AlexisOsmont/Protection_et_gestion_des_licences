package model;

import java.util.List;

/*
 * Modèle des administrateurs : hérite d'un utilisateur. En plus des
 * caractéristiques d'un utilisateur, un administrateur possède une liste de
 * responsables de maintenance (référencées par leurs identifiants) dont il est
 * responsable.
 * Un getter et un setter pour cette liste de ressources sont donc définis.
 */
public class Admin extends User {
	
	private List<Integer> maintainersList;
	
	public Admin(String username, String email, String password) {
		super(username, email, password);
		maintainersList = null;
	}
	
	public List<Integer> getMaintainersList() {
		return maintainersList;
	}
	
	public void setMaintainersList(List<Integer> maintainers) {
		if (maintainers == null) {
			throw new AssertionError("Invalid arguments !");
		}
		this.maintainersList = maintainers;
	}
}