package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.ICommandeDao;
import fr.adaming.dao.ILignesCommandeDao;
import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Commande;
import fr.adaming.model.LignesCommande;
import fr.adaming.model.Produit;

@Stateful
public class LignesCommandeServiceImpl implements ILignesCommandeService {

	@EJB
	private ILignesCommandeDao ligneDao;

	@EJB
	private IProduitDao produitDao;

	@EJB
	private ICommandeDao commandeDao;

	// setters
	public void setLigneDao(ILignesCommandeDao ligneDao) {
		this.ligneDao = ligneDao;
	}

	public void setProduitDao(IProduitDao produitDao) {
		this.produitDao = produitDao;
	}

	public void setCommandeDao(ICommandeDao commandeDao) {
		this.commandeDao = commandeDao;
	}

	@Override
	public List<LignesCommande> getAllLignes(int idCommande) {
		return ligneDao.getAllLignes(idCommande);
	}

	@Override
	public LignesCommande addLigne(LignesCommande ligne, Commande comm, Produit p) {
		Produit pOut = produitDao.getProduitbyIdorName(p);
		ligne.setProduit(pOut);
		Commande cOut = commandeDao.getCommandeById(comm);
		ligne.setCommande(cOut);
		ligne.setQuantite(1);
		double prixTotal = p.getPrix()-(p.getPrix()*p.getRemise())/100;
		ligne.setPrix(prixTotal);

		return ligneDao.addLigne(ligne);
	}

	@Override
	public LignesCommande updateLigne(LignesCommande ligne, Commande comm, Produit p) {
		Produit pOut = produitDao.getProduitbyIdorName(p);
		ligne.setProduit(pOut);

		Commande cOut = commandeDao.getCommandeById(comm);
		ligne.setCommande(cOut);
		return ligneDao.updateLigne(ligne);
	}

	@Override
	public int deleteLigne(int idLigne) {
		return ligneDao.deleteLigne(idLigne);
	}

	@Override
	public LignesCommande getLigneById(LignesCommande ligne) {
		return ligneDao.getLigneById(ligne);
	}

}
