package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="administrateurs")
public class Administrateur implements Serializable{
	
	//d�claration des attributs
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_a")
	private int idAgent;
	private String mail;
	private String mdp;

	//transformation de l'association UML en java
	@OneToMany(mappedBy="admin")
	private List<Categorie> listeCategories;
	
	
	
	//d�claration des constructeurs
	public Administrateur() {
		super();
	}


	public Administrateur(String mail, String mdp) {
		super();
		this.mail = mail;
		this.mdp = mdp;
	}


	public Administrateur(int idAgent, String mail, String mdp) {
		super();
		this.idAgent = idAgent;
		this.mail = mail;
		this.mdp = mdp;
	}


	//d�claration des getters et setters
	public int getIdAgent() {
		return idAgent;
	}


	public void setIdAgent(int idAgent) {
		this.idAgent = idAgent;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getMdp() {
		return mdp;
	}


	public void setMdp(String mdp) {
		this.mdp = mdp;
	}


	public List<Categorie> getListeCategories() {
		return listeCategories;
	}


	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}
	
	

}
