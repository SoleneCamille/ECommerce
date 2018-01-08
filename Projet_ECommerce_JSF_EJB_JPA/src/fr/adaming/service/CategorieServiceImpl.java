package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Stateful
public class CategorieServiceImpl implements ICategorieService {

	// transformation de l'association uml en java
	@EJB
	private ICategorieDao categorieDao;

	// setter pour la categorie DAO
	public void setCategorieDao(ICategorieDao categorieDao) {
		this.categorieDao = categorieDao;
	}

	@Override
	public List<Categorie> getAllCategories() {
		return categorieDao.getAllCategories();
	}

	@Override
	public Categorie addCategorie(Categorie cat) {
		return categorieDao.addCategorie(cat);
	}

	@Override
	public Categorie updateCategorie(Categorie cat) {
		Categorie catFind = categorieDao.getCategorieByIdOrName(cat);
		if (catFind != null) {
			return categorieDao.updateCategorie(cat);
		} else {
			return null;
		}
	}

	@Override
	public int deleteCategorie(int idCategorie) {	
		return categorieDao.deleteCategorie(idCategorie);
	}

	@Override
	public Categorie getCategorieByIdOrName(Categorie cat) {
		return categorieDao.getCategorieByIdOrName(cat);
	}

}
