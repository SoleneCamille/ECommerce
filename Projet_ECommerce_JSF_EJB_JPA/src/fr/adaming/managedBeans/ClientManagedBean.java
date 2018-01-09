package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;

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

	private HttpSession maSession;

	//constructeur vide
	public ClientManagedBean() {
		this.client = new Client();
	}

	//getters et setters
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
	
	//m�thodes m�tiers
	public String entrerSite(){
		return null;
	}
	
	

}
