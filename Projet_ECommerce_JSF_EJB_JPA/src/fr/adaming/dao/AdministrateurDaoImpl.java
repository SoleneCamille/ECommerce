package fr.adaming.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Administrateur;

public class AdministrateurDaoImpl implements IAdministrateurDao {
	@PersistenceContext(unitName = "PU_projet") // pour l'injection d'un EM
	EntityManager em;

	@Override
	public Administrateur isExist(Administrateur a) throws Exception {

		// construire la requete JPQL
		String req = "select a from Administrateur as a where a.mail=:pMail and a.mdp=:pMdp";

		// créer un query
		Query query = em.createQuery(req);

		// assignation des paramètres de la requete
		query.setParameter("pMail", a.getMail());
		query.setParameter("pMdp", a.getMdp());

		// envoi de la requete et récup de l'agent
		Administrateur aOut = (Administrateur) query.getSingleResult();

		return aOut;
	}
}
