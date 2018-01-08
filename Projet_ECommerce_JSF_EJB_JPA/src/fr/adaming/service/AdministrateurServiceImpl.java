package fr.adaming.service;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IAdministrateurDao;
import fr.adaming.model.Administrateur;

@Stateful
public class AdministrateurServiceImpl implements IAdministrateurService{

	@EJB //pour injecter DAO
	private IAdministrateurDao adminDao;
	
	
	//cr�ation de getters et setters n�cessaires � l'injection d�pendance
	public IAdministrateurDao getAdminDao() {
		return adminDao;
	}



	public void setAgentDao(IAdministrateurDao adminDao) {
		this.adminDao = adminDao;
	}



	@Override
	public Administrateur isExist(Administrateur a) throws Exception {
		//appel de la m�thode DAO
		return adminDao.isExist(a);
	}


}
