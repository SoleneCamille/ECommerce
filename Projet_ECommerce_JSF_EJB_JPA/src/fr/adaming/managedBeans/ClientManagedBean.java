package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import com.sun.mail.smtp.SMTPTransport;


import fr.adaming.model.Categorie;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "clientMB")
@ViewScoped
public class ClientManagedBean implements Serializable {

	// transformation de l'association uml en java
	 @EJB
	 private IClientService clientService;
	@EJB
	private ICategorieService catService;
	
	@EJB
	private IProduitService prodService;
	
	@EJB
	private ICommandeService comService;
	
//	@EJB
//	private IClientService clientService;

	private Client client;
	private List<Categorie> listeCategories;
	private Categorie categorie;
	private HttpSession maSession;
	private List<Produit> listeProduit;
	private Commande commande;
	// constructeur vide
	public ClientManagedBean() {
		this.client = new Client();
		this.categorie = new Categorie();
		this.commande=new Commande();

	}
	
	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	// getters et setters
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

	public void setCatService(ICategorieService catService) {
		this.catService = catService;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	
	
	public HttpSession getMaSession() {
		return maSession;
	}

	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}

	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}
	
	
	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	
	

	public ICommandeService getComService() {
		return comService;
	}

	public void setComService(ICommandeService comService) {
		this.comService = comService;
	}
	
	
	

//	public void setClientService(IClientService clientService) {
//		this.clientService = clientService;
//	}

	// methodes
	public String consulterCategorie() {
		List<Produit> liste = prodService.getProduitByCat(this.categorie);
		this.listeProduit = new ArrayList<Produit>();

		if (liste != null) {
			for (Produit element : liste) {
				if (element.getPhoto()==null) {
					element.setImage(null);
				} else {
					element.setImage("data:image/png;base64," + Base64.encodeBase64String(element.getPhoto()));
				}
				
				this.listeProduit.add(element);
			}
			
			// ajout de la liste de produits dans la session
			maSession.setAttribute("listeProduits", this.listeProduit);

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas de produit dans cette catégorie"));

		}

		return "afficheProduitsClient";

	}

	public String getPrixCommande(){
		this.commande.setIdCommande(1);
		
		
		
		double prixAvant=comService.getPrixTotalAvantRemise(this.commande);
		this.commande.setPrixAvant(prixAvant);
		
		double prixApres=comService.getPrixTotalApresRemise(this.commande);		
		this.commande.setPrixApres(prixApres);
		maSession.setAttribute("com", this.commande);
		return "ValidationPanier";
		

	}
	
	public String ajouterClient(){
		clientService.addClient(this.client);
		return "envoiMail";
	}
	
	
	public void envoiMail() throws AddressException, MessagingException{

        Properties props = System.getProperties();
        props.put("mail.smtps.host","smtp.gmail.com");
        props.put("mail.smtps.auth","true");
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("application.j2ee@gmail.com"));;
        msg.setRecipients(Message.RecipientType.TO,
        InternetAddress.parse(this.client.getEmail(), false));
        msg.setSubject("winterIsComing "+System.currentTimeMillis());
        msg.setText("Votre commande est validée ");
        msg.setSentDate(new Date());
        
        Multipart multipart = new MimeMultipart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Votre commande:");
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource("Bureau\\pieceJointe.pdf");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("Bureau\\pieceJointe.pdf");
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        
        
       
        SMTPTransport t =
            (SMTPTransport)session.getTransport("smtps");
        t.connect("smtp.gmail.com", "application.j2ee@gmail.com", "adamingintijee");
        t.sendMessage(msg, msg.getAllRecipients());
        System.out.println("Mail envoyé");
        t.close();
	}
	
}
