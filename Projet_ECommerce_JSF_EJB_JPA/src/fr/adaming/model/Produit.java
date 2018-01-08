package fr.adaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="produits")
public class Produit implements Serializable{
	
	//déclaration des attributs
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_p")
	private Long idProduit;
	private String designation;
	private String description;
	private double prix;
	private int quantite;
	private boolean selectionne;
	private byte[] photo;
	
	
	//transformation de l'association simple de l'UML en java
	//@ManyToOne
	//@JoinColumn(name="categorie_id", referencedCol ="id_cat)
	//private Categorie categorie;
	
	//déclaration des constructeurs
	public Produit() {
		super();
	}


	public Produit(String designation, String description, double prix, int quantite, boolean selectionne,
			byte[] photo) {
		super();
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}


	public Produit(Long idProduit, String designation, String description, double prix, int quantite,
			boolean selectionne, byte[] photo) {
		super();
		this.idProduit = idProduit;
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}

	//déclaration des getters et setters
	public Long getIdProduit() {
		return idProduit;
	}


	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getPrix() {
		return prix;
	}


	public void setPrix(double prix) {
		this.prix = prix;
	}


	public int getQuantite() {
		return quantite;
	}


	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}


	public boolean isSelectionne() {
		return selectionne;
	}


	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
	}


	public byte[] getPhoto() {
		return photo;
	}


	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	
	
	

}
