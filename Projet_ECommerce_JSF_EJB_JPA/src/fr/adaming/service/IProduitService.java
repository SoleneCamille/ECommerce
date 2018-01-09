package fr.adaming.service;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;


@Local
public interface IProduitService {
	
	
	public Produit addProduit(Produit p, Categorie c);
	public int deleteProduit(int id);
	public Produit updateProduit (Produit p, Categorie c);
	public Produit getProduitbyIdorName(Produit p);
	public Produit getProduitByName(String name);
	public List<Produit> getProduitByCat(int id);

}
