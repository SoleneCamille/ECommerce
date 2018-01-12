package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Commande;
import fr.adaming.model.LignesCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.ILignesCommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean (name="lMB")
@RequestScoped
public class LignesCommandeManagedBean implements Serializable{

	//transformation de l'association UML en java
	@EJB
	private ILignesCommandeService ligneService;
	
	@EJB
	private IProduitService produitService;
	
	private LignesCommande ligne;
	private List<LignesCommande> listeLignes;
	private Produit produit;
	
	private HttpSession maSession;

	public LignesCommandeManagedBean() {
		this.ligne = new LignesCommande();
	}
	
	public LignesCommande getLigne() {
		return ligne;
	}

	public void setLigne(LignesCommande ligne) {
		this.ligne = ligne;
	}

	public List<LignesCommande> getListeLignes() {
		return listeLignes;
	}

	public void setListeLignes(List<LignesCommande> listeLignes) {
		this.listeLignes = listeLignes;
	}

	public void setLigneService(ILignesCommandeService ligneService) {
		this.ligneService = ligneService;
	}
	
	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	public String ajouterLigne() {
		Commande comDefaut = new Commande();
		comDefaut.setIdCommande(1);
		this.ligne = ligneService.addLigne(this.ligne, comDefaut, this.produit);
		
		//récupérer la liste de lignes dont la commande est nulle
		this.listeLignes = ligneService.getAllLignes(comDefaut.getIdCommande());
		
		for (LignesCommande element : listeLignes) {
			System.out.println(element.getIdLigne());
		}
		
		//ajout de la liste dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lignesListe", this.listeLignes);
		
		return "panier";
	}
	
	

}
