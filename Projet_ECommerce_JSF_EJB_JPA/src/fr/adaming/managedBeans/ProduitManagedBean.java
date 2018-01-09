package fr.adaming.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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

	// constructeur

	public ProduitManagedBean() {

		this.produit = new Produit();
		this.categorie=new Categorie();
		
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
	
	//methodes

	public String ajouterProduit(){
		
		
		Produit p=produitService.addProduit(this.produit, this.categorie );
		
		if (p!=null) {
			//récupération de la nouvelle liste de la bd
			this.listeProduit = produitService.getProduitByCat(this.categorie.getIdCategorie());
			
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
		this.listeProduit=produitService.getProduitByCat(categorie.getIdCategorie());
		
		if(this.listeProduit!=null){
		maSession.setAttribute("produitList", this.listeProduit);
		
		return "afficheProduit";}
		else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Liste vide"));
			return "afficheProduit";
		}
	}
}
