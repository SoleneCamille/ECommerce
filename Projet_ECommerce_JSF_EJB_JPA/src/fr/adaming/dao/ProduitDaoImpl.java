package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Stateless
public class ProduitDaoImpl implements IProduitDao {

	@PersistenceContext(unitName = "PU_Projet")
	EntityManager em;

	// setters pour l'injection de l'ind�pendance
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Produit addProduit(Produit p) {

		em.merge(p);
		return p;

	}

	@Override
	public int deleteProduit(int id) {
		// cr�ation de la requete JPQL
		String req = "delete from Produit as p where p.idProduit =:pIdProduit ";

		// cr�ation du query
		Query query = em.createQuery(req);

		// assignation des param�tres
		query.setParameter("pIdProduit", id);

		// envoi de la requete
		int verif = query.executeUpdate();
		return verif;
	}

	@Override
	public Produit updateProduit(Produit p) {
		em.merge(p);
		// envoi de la requete et recup du resultat
		return p;
	}

	@Override
	public Produit getProduitbyIdorName(Produit p) {

		// creation de la requete JPQL
		String req = "select p from Produit as p where p.idProduit=:pId " + "or p.designation=:pNom";

		// creation du query
		Query query = em.createQuery(req);

		// assignation des param�tres
		query.setParameter("pId", p.getIdProduit());
		query.setParameter("pNom", p.getDesignation());

		// envoi de la requete et r�cup�ration du resultat
		Produit pFind = (Produit) query.getSingleResult();

		return pFind;

	}

	@Override
	public Produit getProduitByName(String name) {
		Produit pFind = em.find(Produit.class, name);
		return pFind;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Produit> getProduitByCat(Categorie c) {
		// construire la requete JPQL
		String req = "select p from Produit as p WHERE p.categorie.idCategorie=:pIdC";

		// cr�er un query
		Query query = em.createQuery(req);
		query.setParameter("pIdC", c.getIdCategorie());

		// envoi de la requete et r�cup�ration du r�sultat
		return query.getResultList();
	}

}
