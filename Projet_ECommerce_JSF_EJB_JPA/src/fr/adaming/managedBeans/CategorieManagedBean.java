package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Categorie;
import fr.adaming.service.ICategorieService;

@ManagedBean(name = "catMB")
@RequestScoped
public class CategorieManagedBean implements Serializable {

	// transformation de l'association UML en java
	@EJB
	private ICategorieService categorieService;

	private Categorie categorie;
	private List<Categorie> listeCategories;

	private HttpSession maSession;

	public CategorieManagedBean() {
		this.categorie = new Categorie();
	}

	// méthode qui s'exécute après l'instanciation du managedBean
	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	//getters et setters
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
	
	//les méthodes métiers
	public String ajouterCategorie() {
		this.categorie = categorieService.addCategorie(this.categorie);
		System.out.println("qshshj######################");
		
		if (this.categorie!=null) {
			//récupération de la nouvelle liste de la bd
			this.listeCategories = categorieService.getAllCategories();
			
			//mettre à jour la liste dans la session
			maSession.setAttribute("categoriesList", this.listeCategories);
			
			return "accueilAdmin";
		} else {
			return "login";
		}
		
	}
	


}
