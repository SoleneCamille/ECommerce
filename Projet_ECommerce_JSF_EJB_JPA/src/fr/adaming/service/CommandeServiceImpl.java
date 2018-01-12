package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IClientDao;
import fr.adaming.dao.ICommandeDao;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Stateful
public class CommandeServiceImpl implements ICommandeService {

	@EJB
	private ICommandeDao commandeDao;

	@EJB
	private IClientDao clientDao;

	// setters
	public void setCommandeDao(ICommandeDao commandeDao) {
		this.commandeDao = commandeDao;
	}

	@Override
	public List<Commande> getAllCommandes() {
		return commandeDao.getAllCommandes();
	}

	@Override
	public List<Commande> getAllCommandesFromClient(Client client) {
		return commandeDao.getAllCommandesFromClient(client);
	}

	@Override
	public Commande addCommande(Commande comm, Client client) {
		Client cOut = clientDao.getClientById(client);
		comm.setClient(cOut);
		return commandeDao.addCommande(comm);
	}

	@Override
	public Commande updateCommande(Commande comm, Client client) {
		Client cOut = clientDao.getClientById(client);
		comm.setClient(cOut);
		return commandeDao.updateCommande(comm);
	}

	@Override
	public int deleteCommande(int idCommande) {
		return commandeDao.deleteCommande(idCommande);
	}

	@Override
	public Commande getCommandeById(Commande comm) {
		return commandeDao.getCommandeById(comm);
	}

}
