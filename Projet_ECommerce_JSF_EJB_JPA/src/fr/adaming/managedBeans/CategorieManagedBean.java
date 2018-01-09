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
import fr.adaming.service.ICategorieService;

@ManagedBean(name = "catMB")
@ViewScoped
public class CategorieManagedBean implements Serializable {

	// transformation de l'association UML en java
	@EJB
	private ICategorieService categorieService;

	private Categorie categorie;
	private List<Categorie> listeCategories;

	private HttpSession maSession;
	private String image;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	// les m�thodes m�tiers
	public String entrerSite() {
		// r�cup�rer la liste de cat�gories
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
		System.out.println("------------   aprers ajout" + categorie.getPhoto().length);
		this.categorie = categorieService.addCategorie(this.categorie);
		
		if (this.categorie != null) {
			// r�cup�ration de la nouvelle liste de la bd
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

	public String consulterCategorie() {
		Categorie catFind = categorieService.getCategorieByIdOrName(this.categorie);

		if (catFind != null) {
			this.categorie = catFind;
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cette cat�gorie n'existe pas !", null));
		}

		return "rechercheCat";
	}

	// m�thode pour transformer une image en table de byte
	public void upload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		System.out.println("------------   " + uploadedFile.getFileName());
		// r�cup�rer le contenu de l'image en byte
		byte[] contents = uploadedFile.getContents();

		// stocker le contenu dans l'attribut photo de categorie
		categorie.setPhoto(contents);
		System.out.println("------------   " + categorie.getPhoto().length);
		// transforme byteArray en string (format base64)
		this.image = "data:image/png;base64," + Base64.encodeBase64String(contents);
	}
}
