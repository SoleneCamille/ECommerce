package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Categorie;

@Stateless
public class CategorieDaoImpl implements ICategorieDao {

	@PersistenceContext(unitName = "PU_Projet") // pour l'injection d'un EM
	EntityManager em;
	
	//setter pour l'injection d'indépendance
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categorie> getAllCategories() {

		// construire la requete JPQL
		String req = "select cat from Categorie as cat";

		// créer un query
		Query query = em.createQuery(req);

		// envoi de la requete et récupération du résultat
		return query.getResultList();
	}

	

	@Override
	public Categorie addCategorie(Categorie cat) {
		em.persist(cat);
		//envoi de la requete
		return cat;
	}

	@Override
	public Categorie updateCategorie(Categorie cat) {
		
		return null;
	}

	@Override
	public int deleteCategorie(int idCategorie) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Categorie getCategorieById(int idCategorie) {
		// TODO Auto-generated method stub
		return null;
	}

}
