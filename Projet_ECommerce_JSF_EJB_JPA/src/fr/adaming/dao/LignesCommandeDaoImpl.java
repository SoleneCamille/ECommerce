package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Commande;
import fr.adaming.model.LignesCommande;
import fr.adaming.model.Produit;

@Stateless
public class LignesCommandeDaoImpl implements ILignesCommandeDao {

	@PersistenceContext(unitName = "PU_Projet") // pour l'injection d'un EM
	EntityManager em;

	// setter pour l'injection d'indépendance
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LignesCommande> getAllLignes(int idCommande) {
		// construire la requete JPQL
		String req = "select l from LignesCommande as l where l.commande.idCommande=:pIdComm";

		// créer un query
		Query query = em.createQuery(req);

		// assignation des paramètres
		query.setParameter("pIdComm", idCommande);

		// envoi de la requete et récupération du result
		return query.getResultList();
	}

	@Override
	public LignesCommande addLigne(LignesCommande ligne) {
		em.persist(ligne);
		// envoi de la requete
		return ligne;
	}

	@Override
	public LignesCommande updateLigne(LignesCommande ligne) {
		em.merge(ligne);
		return ligne;
	}

	@Override
	public int deleteLigne(int idLigne) {
		// creation de la requete JPQL
		String req = "delete from LignesCommande as l where l.idLigne=:pIdLigne";

		// créer un query
		Query query = em.createQuery(req);

		// assignation des paramètres
		query.setParameter("pIdLigne", idLigne);

		return query.executeUpdate();
	}

	@Override
	public LignesCommande getLigneById(LignesCommande ligne) {
		return em.find(LignesCommande.class, ligne.getIdLigne());
	}

	@Override
	public LignesCommande getLigneByIdProduit(Produit p) {
		// construire la requete JPQL
		String req = "select l from LignesCommande as l where l.produit.idProduit=:pIdProd";

		// créer un query
		Query query = em.createQuery(req);

		// assignation des paramètres
		query.setParameter("pIdProd", p.getIdProduit());

		// envoi de la requete et récupération du result
		return (LignesCommande) query.getSingleResult();
	}

}
