package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Produit;

@Stateful
public class ProduitServiceImpl implements IProduitService {

	@EJB
	private IProduitDao produitDao;
	
	//setter produitDao	
	
	public void setProduitDao(IProduitDao produitDao) {
		this.produitDao = produitDao;
	}
	
	
	
	//methodes

	@Override
	public Produit addProduit(Produit p) {
	return produitDao.addProduit(p);
	}

	@Override
	public int deleteProduit(int id) {
	return produitDao.deleteProduit(id);
	}

	@Override
	public Produit updateProduit(Produit p) {
	return produitDao.updateProduit(p);
	}

	@Override
	public Produit getProduitbyId(int id) {
		return produitDao.getProduitbyId(id);
	}

	@Override
	public Produit getProduitByName(String name) {
		return produitDao.getProduitByName(name);
	}



	@Override
	public List<Produit> getProduitByCat(int id) {
		return produitDao.getProduitByCat(id);
	}
	
	

}
