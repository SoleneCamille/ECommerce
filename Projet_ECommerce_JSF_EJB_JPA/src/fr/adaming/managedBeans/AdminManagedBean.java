package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Administrateur;
import fr.adaming.model.Categorie;
import fr.adaming.service.IAdministrateurService;
import fr.adaming.service.ICategorieService;

@ManagedBean(name = "aMB")
@RequestScoped
public class AdminManagedBean implements Serializable {

	@EJB
	private IAdministrateurService adminService;

	@EJB
	private ICategorieService categorieService;

	private Administrateur admin;
	private List<Categorie> listeCategories;

	// constructeur vide
	public AdminManagedBean() {
		super();
		this.admin=new Administrateur();
	}

	// getters & setters

	public IAdministrateurService getAdminService() {
		return adminService;
	}

	public void setAdminService(IAdministrateurService adminService) {
		this.adminService = adminService;
	}

	public ICategorieService getCategorieService() {
		return categorieService;
	}

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	public Administrateur getAdmin() {
		return admin;
	}

	public void setAdmin(Administrateur admin) {
		this.admin = admin;
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

	public String seConnecter() {

		try {
			Administrateur aOut = adminService.isExist(this.admin);
			System.out.println(aOut);

			// récup de la liste de categories
			listeCategories = categorieService.getAllCategories();

			// ajouter la liste dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoriesList",
					listeCategories);

			// ajouter l'admin dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("adminSession", aOut);

			return "accueilAdmin";

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("L'identifiant ou le mot de passe est incorrect"));

		}
		return "login";
	}

}
