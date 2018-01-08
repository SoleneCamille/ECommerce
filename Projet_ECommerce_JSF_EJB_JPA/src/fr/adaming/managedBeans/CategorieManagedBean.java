package fr.adaming.managedBeans;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import fr.adaming.model.Categorie;
import fr.adaming.service.ICategorieService;

@ManagedBean(name = "catMB")
@RequestScoped
public class CategorieManagedBean implements Serializable {

	// transformation de l'association UML en java
	@EJB
	private ICategorieService categorieService;

	// private Part file;
	private Categorie categorie;
	private List<Categorie> listeCategories;

	private HttpSession maSession;

	public CategorieManagedBean() {
		this.categorie = new Categorie();
	}

	// m�thode qui s'ex�cute apr�s l'instanciation du managedBean
	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	// getters et setters
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	// public Part getFile() {
	// return file;
	// }
	//
	// public void setFile(Part file) {
	// this.file = file;
	// }

	// les m�thodes m�tiers
	public String ajouterCategorie() {
		this.categorie = categorieService.addCategorie(this.categorie);

		if (this.categorie != null) {
			// r�cup�ration de la nouvelle liste de la bd
			this.listeCategories = categorieService.getAllCategories();

			// mettre � jour la liste dans la session
			maSession.setAttribute("categoriesList", this.listeCategories);

			return "accueilAdmin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cette cat�gorie n'a pas pu �tre ajout�e !", null));
			return "login";
		}

	}

	public String modifierCategorie() {
		this.categorie = categorieService.updateCategorie(this.categorie);

		if (this.categorie != null) {
			// r�cup�ration de la nouvelle liste de la bd
			this.listeCategories = categorieService.getAllCategories();

			// mettre � jour la liste dans la session
			maSession.setAttribute("categoriesList", this.listeCategories);

			return "accueilAdmin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cette cat�gorie n'existe pas !", null));
			return "modifCat";
		}

	}

	public String supprimerCategorie() {
		int verif = categorieService.deleteCategorie(this.categorie.getIdCategorie());

		if (verif == 1) {
			// r�cup�ration de la nouvelle liste de la bd
			this.listeCategories = categorieService.getAllCategories();

			// mettre � jour la liste dans la session
			maSession.setAttribute("categoriesList", this.listeCategories);
			
			return "accueilAdmin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cette cat�gorie n'existe pas !", null));
			return "supprCat";
		}
		
	}

}
