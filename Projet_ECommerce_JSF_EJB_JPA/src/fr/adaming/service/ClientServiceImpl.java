package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IClientDao;
import fr.adaming.model.Client;

@Stateful
public class ClientServiceImpl implements IClientService {

	// transformation de l'association UML en java
	@EJB
	private IClientDao clientDao;

	// setter pour le client DAO
	public void setClientDao(IClientDao clientDao) {
		this.clientDao = clientDao;
	}

	@Override
	public List<Client> getAllClients() {
		return clientDao.getAllClients();
	}

	@Override
	public Client addClient(Client client) {
		return clientDao.addClient(client);
	}

	@Override
	public Client updateClient(Client client) {
		Client clientFind = clientDao.getClientById(client);

		if (clientFind != null) {
			return clientDao.updateClient(client);
		} else {
			return null;
		}
	}

	@Override
	public int deleteClient(int idClient) {
		return clientDao.deleteClient(idClient);
	}

	@Override
	public Client getClientById(Client client) {
		return clientDao.getClientById(client);
	}

}
