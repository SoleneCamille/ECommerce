package fr.adaming.managedBeans;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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

	private HttpSession maSession;

	// constructeur

	public ProduitManagedBean() {

		this.produit = new Produit();
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

	//methodes
	
	public String ajouterProduit(){
this.produit = produitService.addProduit(this.produit);
		
		if (this.produit!=null) {
			//récupération de la nouvelle liste de la bd
			this.listeProduit = produitService.getProduitByCat(produit.getCategorie().getIdCategorie());
			
			//mettre à jour la liste dans la session
			maSession.setAttribute("produitList", this.listeProduit);
			
			return "afficheProduit";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout a échoué"));
			return "ajoutProduit";
		}
	}
	public double calculPrix(){
		double prix=this.produit.getPrix()-(this.produit.getPrix()*this.produit.getRemise()/100);
		return prix;
	}
	
	public String afficherProduit(){
		this.listeProduit=produitService.getProduitByCat(produit.getCategorie().getIdCategorie());
		
		if(this.listeProduit!=null){
		maSession.setAttribute("produitList", this.listeProduit);
		
		return "afficheProduit";}
		else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Liste vide"));
			return "afficheProduit";
		}
	}
}
