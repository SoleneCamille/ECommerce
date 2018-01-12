package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Client;

@Stateless
public class ClientDaoImpl implements IClientDao {

	@PersistenceContext(unitName = "PU_Projet") // pour l'injection d'un EM
	EntityManager em;

	// setter pour l'injection d'indépendance
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> getAllClients() {
		// construire la requete JPQL
		String req = "select c from Client as c";

		// créer un query
		Query query = em.createQuery(req);

		// envoi de la requete et récupération du result
		return query.getResultList();
	}

	@Override
	public Client addClient(Client client) {
		em.persist(client);
		return client;
	}

	@Override
	public Client updateClient(Client client) {
		em.merge(client);
		return client;
	}

	@Override
	public int deleteClient(int idClient) {
		// creation de la requete JPQL
		String req = "delete from Client as c where c.idClient=:pIdClient";

		// créer un query
		Query query = em.createQuery(req);

		// assignation des paramètres
		query.setParameter("pIdClient", idClient);

		return query.executeUpdate();
	}

	@Override
	public Client getClientById(Client client) {
		return em.find(Client.class, client.getIdClient());
	}

}
