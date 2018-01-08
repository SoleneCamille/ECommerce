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
	
	//setter pour l'injection d'ind�pendance
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categorie> getAllCategories() {

		// construire la requete JPQL
		String req = "select cat from Categorie as cat";

		// cr�er un query
		Query query = em.createQuery(req);

		// envoi de la requete et r�cup�ration du r�sultat
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
		em.merge(cat);
		return cat;
	}

	@Override
	public int deleteCategorie(int idCategorie) {
		//cr�ation de la requete SQL
		String req = "delete from Categorie as cat where cat.idCategorie = :pId";
		
		//creation du query
		Query query = em.createQuery(req);
		
		//assignation des param�tres de la requete
		query.setParameter("pId", idCategorie);
		
		return query.executeUpdate();
	}

	@Override
	public Categorie getCategorieByIdOrName(Categorie cat) {
		//creation de la requete JPQL
		String req = "select cat from Categorie as cat where cat.idCategorie=:pId "
				+ "or cat.nomCategorie=:pNom";
		
		//creation du query
		Query query = em.createQuery(req);
		
		//assignation des param�tres
		query.setParameter("pId", cat.getIdCategorie());
		query.setParameter("pNom", cat.getNomCategorie());
		
		//envoi de la requete et r�cup�ration du resultat
		Categorie catFind = (Categorie) query.getSingleResult();
			
		return catFind;
	}

}
