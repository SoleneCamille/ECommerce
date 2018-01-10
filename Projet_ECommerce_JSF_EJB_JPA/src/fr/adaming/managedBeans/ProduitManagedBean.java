package fr.adaming.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "pMB")
@RequestScoped
public class ProduitManagedBean {

	// UML en java

	@EJB
	private IProduitService produitService;

	private Produit produit;
	private List<Produit> listeProduit;
	private Categorie categorie;

	private HttpSession maSession;

	private String image;

	// constructeur
	public ProduitManagedBean() {

		this.produit = new Produit();
		this.categorie = new Categorie();

	}

	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	// getters et setters
	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	// methodes
	public String ajouterProduit() {

		Produit p = produitService.addProduit(this.produit, this.categorie);

		if (p != null) {
			// récupération de la nouvelle liste de la bd
			this.listeProduit = produitService.getProduitByCat(this.categorie);

			// mettre à jour la liste dans la session
			maSession.setAttribute("produitList", this.listeProduit);

			return "afficheProduit";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout a échoué"));
			return "ajoutProduit";
		}
	}

	public double calculPrix() {
		double prix = this.produit.getPrix() - (this.produit.getPrix() * this.produit.getRemise() / 100);
		return prix;
	}

	public String afficherProduit() {
		this.produit = produitService.getProduitbyIdorName(this.produit);

		if (this.produit != null) {
			maSession.setAttribute("produit", this.produit);

			return "afficheProduit";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Liste vide"));
			return "afficheProduit";
		}
	}

	public String supprimerProduit() {
		int verif = produitService.deleteProduit(this.produit.getIdProduit());
		if (verif == 1) {
			// this.listeProduit =
			// produitService.getProduitByCat(this.categorie.getIdCategorie());
			// maSession.setAttribute("produitList", this.listeProduit);
			return "rechercheCat";

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("echec suppression"));
			return "supprProduit";
		}

	}

	public String modifierProduit() {
		Produit p = produitService.updateProduit(this.produit, this.categorie);
		if (p != null) {
			maSession.setAttribute("produit", this.produit);
			return "rechercheCat";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("echec modification"));
			return "modifProduit";
		}
	}

	// méthode pour transformer une image en table de byte
	public void upload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		// récupérer le contenu de l'image en byte
		byte[] contents = uploadedFile.getContents();

		// stocker le contenu dans l'attribut photo de categorie
		produit.setPhoto(contents);
		// transforme byteArray en string (format base64)
		this.image = "data:image/png;base64," + Base64.encodeBase64String(contents);
	}

}
