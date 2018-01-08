package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Categorie;

@Local
public interface ICategorieDao {
	
	//déclaration des méthodes
	public List<Categorie> getAllCategories();
	
	public Categorie addCategorie(Categorie cat);
	
	public Categorie updateCategorie(Categorie cat);
	
	public int deleteCategorie(int idCategorie);
	
	public Categorie getCategorieByIdOrName(Categorie cat);

}
