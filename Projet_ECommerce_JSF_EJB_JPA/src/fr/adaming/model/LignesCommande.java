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

	//déclaration des constructeurs
	public LignesCommande() {
		super();
	}
	
	
	
	

}
