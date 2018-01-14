package fr.adaming.service;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Client;

@Local
public interface IClientService {
	
	// déclaration des méthodes
	public List<Client> getAllClients();

	public Client addClient(Client client);

	public Client updateClient(Client client);

	public int deleteClient(int idClient);

	public Client isExist(Client client);
	
	public Client getClientById(Client client);

}
