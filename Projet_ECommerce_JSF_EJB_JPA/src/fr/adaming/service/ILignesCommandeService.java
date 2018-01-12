package fr.adaming.service;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Commande;
import fr.adaming.model.LignesCommande;
import fr.adaming.model.Produit;

@Local
public interface ILignesCommandeService {	

	// d�claration des m�thodes
	public List<LignesCommande> getAllLignes(int idCommande);

	public LignesCommande addLigne(LignesCommande ligne, Commande comm, Produit p);

	public LignesCommande updateLigne(LignesCommande ligne, Commande comm, Produit p);

	public int deleteLigne(int idLigne);

	public LignesCommande getLigneById(LignesCommande ligne);
	

}
