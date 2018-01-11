package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Stateless
public class CommandeDaoImpl implements ICommandeDao {

	@PersistenceContext(unitName = "PU_Projet")
	EntityManager em;

	// setters pour l'injection de l'indépendance
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Commande> getAllCommandes() {
		// construire la requete JPQL
		String req = "select comm from Commande as comm";

		// créer un query
		Query query = em.createQuery(req);

		// envoi de la requete et récupération du résultat
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Commande> getAllCommandesFromClient(Client client) {
		// construire la requete JPQL
		String req = "select comm from Commande as comm where comm.client.idClient=:pIdClient";

		// créer un query
		Query query = em.createQuery(req);

		// assignation des paramètres
		query.setParameter("pIdClient", client.getIdClient());

		// envoi de la requete et récupération du résultat
		return query.getResultList();
	}

	@Override
	public Commande addCommande(Commande comm) {
		em.persist(comm);
		return comm;
	}

	@Override
	public Commande updateCommande(Commande comm) {
		em.merge(comm);
		return comm;
	}

	@Override
	public int deleteCommande(int idCommande) {
		// création de la requete JPQL
		String req = "delete from Commande as comm where comm.idCommande = :pId";

		// creation du query
		Query query = em.createQuery(req);

		// assignation des paramètres de la requete
		query.setParameter("pId", idCommande);

		return query.executeUpdate();
	}

	@Override
	public Commande getCommandeById(Commande comm) {
		em.find(Commande.class, comm.getIdCommande());
		return comm;
	}
}
