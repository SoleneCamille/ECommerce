package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import fr.adaming.model.Categorie;
import fr.adaming.model.Client;
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "clientMB")
@ViewScoped
public class ClientManagedBean implements Serializable {

	// transformation de l'association uml en java
	// @EJB
	// private IClientService clientService;
	@EJB
	private ICategorieService catService;
	
	@EJB
	private IProduitService prodService;

	private Client client;
	private List<Categorie> listeCategories;
	private Categorie categorie;
	private HttpSession maSession;
	private List<Produit> listeProduit;
	
	// constructeur vide
	public ClientManagedBean() {
		this.client = new Client();
		this.categorie = new Categorie();

	}
	
	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
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

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	
	
	public HttpSession getMaSession() {
		return maSession;
	}

	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}

	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}

	// methodes
	public String consulterCategorie() {
		List<Produit> liste = prodService.getProduitByCat(this.categorie);
		this.listeProduit = new ArrayList<Produit>();

		if (liste != null) {
			for (Produit element : liste) {
				if (element.getPhoto()==null) {
					element.setImage(null);
				} else {
					element.setImage("data:image/png;base64," + Base64.encodeBase64String(element.getPhoto()));
				}
				
				this.listeProduit.add(element);
			}
			
			// ajout de la liste de produits dans la session
			maSession.setAttribute("listeProduits", this.listeProduit);

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas de produit dans cette catégorie"));

		}

		return "afficheProduitsClient";

	}

}
