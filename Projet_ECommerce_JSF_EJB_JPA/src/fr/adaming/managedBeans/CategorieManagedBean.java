
package fr.adaming.managedBeans;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "catMB")
@ViewScoped
public class CategorieManagedBean implements Serializable {


	// transformation de l'association UML en java
	@EJB
	private ICategorieService categorieService;

	@EJB
	private IProduitService produitService;

	private Categorie categorie;
	private List<Categorie> listeCategories;
	private List<Produit> listeProduits;

	private HttpSession maSession;
	private String image;

	private boolean indices;
	private boolean indiceProduit;

	public CategorieManagedBean() {
		this.categorie = new Categorie();
	}

	// méthode qui s'exécute après l'instanciation du managedBean
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	public boolean isIndices() {
		return indices;
	}

	public void setIndices(boolean indices) {
		this.indices = indices;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}
	
	

	public boolean isIndiceProduit() {
		return indiceProduit;
	}

	public void setIndiceProduit(boolean indiceProduit) {
		this.indiceProduit = indiceProduit;
	}

	// les méthodes métiers
	public String entrerSite() {
		// récupérer la liste de catégories
		List<Categorie> listOut = categorieService.getAllCategories();
		this.listeCategories = new ArrayList<Categorie>();

		for (Categorie element : listOut) {
			if (element.getPhoto() == null) {
				element.setImage(null);
			} else {
				element.setImage("data:image/png;base64," + Base64.encodeBase64String(element.getPhoto()));
			}
			this.listeCategories.add(element);
		}

		// ajouter la liste dans la session
		maSession.setAttribute("categoriesList", listeCategories);

		return "accueil";
	}

	public String ajouterCategorie() {
		this.categorie = categorieService.addCategorie(this.categorie);

		if (this.categorie != null) {
			// récupération de la nouvelle liste de la bd
			List<Categorie> listOut = categorieService.getAllCategories();
			this.listeCategories = new ArrayList<Categorie>();

			for (Categorie element : listOut) {
				if (element.getPhoto() == null) {
					element.setImage(null);
				} else {
					element.setImage("data:image/jpeg;base64," + Base64.encodeBase64String(element.getPhoto()));
				}
				this.listeCategories.add(element);
			}
			// mettre à jour la liste dans la session
			maSession.setAttribute("categoriesList", this.listeCategories);

			return "accueilAdmin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cette catégorie n'a pas pu être ajoutée !", null));
			return "login";
		}

	}

	public String modifierCategorie() {
		this.categorie = categorieService.updateCategorie(this.categorie);

		if (this.categorie != null) {
			// récupération de la nouvelle liste de la bd
			this.listeCategories = categorieService.getAllCategories();

			// mettre à jour la liste dans la session
			maSession.setAttribute("categoriesList", this.listeCategories);

			return "accueilAdmin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cette catégorie n'existe pas !", null));
			return "modifCat";
		}

	}

	public String supprimerCategorie() {
		int verif = categorieService.deleteCategorie(this.categorie.getIdCategorie());

		if (verif == 1) {
			// récupération de la nouvelle liste de la bd
			this.listeCategories = categorieService.getAllCategories();

			// mettre à jour la liste dans la session
			maSession.setAttribute("categoriesList", this.listeCategories);

			return "accueilAdmin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cette catégorie n'existe pas !", null));
			return "supprCat";
		}

	}

	public String consulterCategorie() {
		Categorie catFind = categorieService.getCategorieByIdOrName(this.categorie);
System.out.println("#########################");
System.out.println(catFind);


		if (catFind != null) {
			this.categorie = catFind;
			this.indices = true;

			List<Produit> liste = produitService.getProduitByCat(this.categorie);

			if (liste != null) {
				this.listeProduits = liste;
				this.indiceProduit = true;

			}

			else {
				this.indiceProduit = false;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Pas de produit dans cette catégorie"));

			}

		} else {
			this.indices = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cette catégorie n'existe pas !", null));
		}

		return "rechercheCat2";
	}

	// méthode pour transformer une image en table de byte
	public void upload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		// récupérer le contenu de l'image en byte
		byte[] contents = uploadedFile.getContents();

		// stocker le contenu dans l'attribut photo de categorie
		categorie.setPhoto(contents);
		// transforme byteArray en string (format base64)
		this.image = "data:image/png;base64," + Base64.encodeBase64String(contents);
	}
}
