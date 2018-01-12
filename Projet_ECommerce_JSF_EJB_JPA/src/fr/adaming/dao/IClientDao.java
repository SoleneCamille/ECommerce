package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Client;


@Local
public interface IClientDao {

	// d�claration des m�thodes
	public List<Client> getAllClients();

	public Client addClient(Client client);

	public Client updateClient(Client client);

	public int deleteClient(int idClient);

	public Client getClientById(Client client);

}
