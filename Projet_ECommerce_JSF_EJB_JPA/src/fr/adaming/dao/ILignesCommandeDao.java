package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Commande;
import fr.adaming.model.LignesCommande;
import fr.adaming.model.Produit;

@Local
public interface ILignesCommandeDao {

	// déclaration des méthodes
	public List<LignesCommande> getAllLignes(int idCommande);

	public LignesCommande addLigne(LignesCommande ligne);

	public LignesCommande updateLigne(LignesCommande ligne);

	public int deleteLigne(int idLigne);

	public LignesCommande getLigneById(LignesCommande ligne);
	
	public LignesCommande getLigneByIdProduit (Produit p);

}
