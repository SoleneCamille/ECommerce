package fr.adaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="lignes")
public class LignesCommande implements Serializable {
	
	//declaration des attributs
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_l")
	private int idLigne;
	private int quantite;
	private int prix;
	
	//transformation de l'association UML en java
	@ManyToOne
	@JoinColumn(name="produit_id", referencedColumnName="id_p")
	private Produit produit;
	
	@ManyToOne
	@JoinColumn(name="commande_id", referencedColumnName="id_comm")
	private Commande commande;

	//d�claration des constructeurs
	public LignesCommande() {
		super();
	}

	public LignesCommande(int quantite, int prix) {
		super();
		this.quantite = quantite;
		this.prix = prix;
	}

	public LignesCommande(int idLigne, int quantite, int prix) {
		super();
		this.idLigne = idLigne;
		this.quantite = quantite;
		this.prix = prix;
	}

	public int getIdLigne() {
		return idLigne;
	}

	public void setIdLigne(int idLigne) {
		this.idLigne = idLigne;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	
	
}