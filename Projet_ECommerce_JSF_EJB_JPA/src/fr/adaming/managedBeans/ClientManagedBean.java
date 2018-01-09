package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import fr.adaming.model.Categorie;
import fr.adaming.model.Client;
import fr.adaming.service.ICategorieService;

@ManagedBean(name = "clientMB")
@RequestScoped
public class ClientManagedBean implements Serializable {

	// transformation de l'association uml en java
	// @EJB
	// private IClientService clientService;
	@EJB
	private ICategorieService catService;

	private Client client;
	private List<Categorie> listeCategories;


	// constructeur vide
	public ClientManagedBean() {
		this.client = new Client();
	}

	// getters et setters
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

	public void setCatService(ICategorieService catService) {
		this.catService = catService;
	}

	// méthodes métiers
	public String entrerSite() {
		//récupérer la liste de catégories
		List<Categorie> listOut = catService.getAllCategories();
		this.listeCategories = new ArrayList<Categorie>();
		
		for(Categorie element:listOut) {
			if (element.getPhoto()==null) {
				element.setImage(null);
			} else {
				element.setImage("data:image/png;base64,"+Base64.encodeBase64String(element.getPhoto()));
			}
			this.listeCategories.add(element);
		}

		// ajouter la liste dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoriesList", listeCategories);

		return "accueil";
	}

}
