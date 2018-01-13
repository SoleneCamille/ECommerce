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
		int quantite=ligne.getQuantite();
		Produit pOut = produitDao.getProduitbyIdorName(p);
		pOut.setSelectionne(true);
		
		ligne.setProduit(pOut);
		Commande cOut = commandeDao.getCommandeById(comm);
		ligne.setCommande(cOut);
		ligne.setQuantite(quantite);
		double prixTotal = ligne.getQuantite()*(p.getPrix()-(p.getPrix()*(p.getRemise()/100)));
		ligne.setPrix(prixTotal);
		System.out.println("###############################################MB prix apres remise"+(ligne.getPrix()));
		System.out.println("###############################################MB prix avant remise"+(ligne.getPrix())/(1-(p.getRemise()/100)));
		ligne.setPrixAvantRemise((ligne.getPrix())/(1-(p.getRemise()/100)));

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
	public int deleteLigne(LignesCommande ligne) {
		Produit pOut = ligne.getProduit();
		pOut.setSelectionne(false);
		produitDao.updateProduit(pOut);
		return ligneDao.deleteLigne(ligne.getIdLigne());
	}

	@Override
	public LignesCommande getLigneById(LignesCommande ligne) {
		return ligneDao.getLigneById(ligne);
	}

	@Override
	public LignesCommande getLigneByIdProduit(Produit p) {
		return ligneDao.getLigneByIdProduit(p);
	}

}
