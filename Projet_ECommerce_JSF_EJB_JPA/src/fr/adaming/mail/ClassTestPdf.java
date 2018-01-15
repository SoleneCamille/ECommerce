package fr.adaming.mail;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.Phrase;
//import com.lowagie.text.pdf.PdfPCell;
//import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.mail.smtp.SMTPTransport;

import fr.adaming.model.Commande;
import fr.adaming.model.LignesCommande;
import fr.adaming.service.CommandeServiceImpl;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILignesCommandeService;
import fr.adaming.service.LignesCommandeServiceImpl;

import com.lowagie.text.DocumentException;

public class ClassTestPdf {

	public static void main(String[] args) throws FileNotFoundException, DocumentException, AddressException, MessagingException {
		// TODO Auto-generated method stub

		//@EJB
		//ILignesCommandeService ligneService=new LignesCommandeServiceImpl() ;
		//ICommandeService commandeService=new CommandeServiceImpl();
		
		   
		//création pdf
		   
		/* Create a new Document object */
		Document document = new Document();
		
		try {
		    /* Associate the document with a PDF writer and an output stream */
		    PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\inti-0257\\Desktop\\formation\\Commande.pdf"));
	 
		    /* Open the document (ready to add items) */
		    document.open();
	 
		    /* Populate the document (add items to it) */ 
//		    Commande comDefaut = new Commande();
//			comDefaut.setIdCommande(1);
		//	List<LignesCommande> liste=ligneService.getAllLignes(5);
			
		document.add(new Paragraph("Récapitulatif de votre commande"));
		
		
		PdfPTable table = new PdfPTable(5);
//	      
//	      //On créer l'objet cellule.
	      PdfPCell cell;
//	      
	      cell = new PdfPCell(new Phrase("Liste des produits commandés"));
	      cell.setColspan(5);
	      table.addCell(cell);
	      
	      table.addCell("nom du produit");
	      table.addCell("quantité");
	      table.addCell("prix avant remise");
	      table.addCell("remise");
	      table.addCell("prix après remise");
	      
	 
	     // cell = new PdfPCell(new Phrase("Fusion de 2 cellules de la première colonne"));
	      //cell.setRowspan(3);
	      //table.addCell(cell);
	 
	      //contenu du tableau.
	      table.addCell("bonnet");
	      table.addCell(Integer.toString(2));
	      table.addCell(Double.toString(15));
	      table.addCell(Double.toString(10));
	      table.addCell(Double.toString(13.5));
	      
	      document.add(table);
	      
	      
	      
	      PdfPTable table2 = new PdfPTable(2);
	   
      
	      cell = new PdfPCell(new Phrase("Montant total"));
	      cell.setColspan(2);
	      table2.addCell(cell);
	      
	      table2.addCell("prix total avant remise");
	      table2.addCell("prix total après remise");
	     
	      table.addCell(Double.toString(30));
	      table.addCell(Double.toString(25));
      
	      document.add(table2);
		
		System.out.println("pdf cree");
		}
		catch(DocumentException e) {
		    /* Oups */
		    System.err.println(e);
		}
		finally {
		    /* Don't forget to close the document! */
		    document.close();
		   
		}
	    
	 
		//Envoi du mail contenant le pdf	

        Properties props = System.getProperties();
        props.put("mail.smtps.host","smtp.gmail.com");
        props.put("mail.smtps.auth","true");
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("application.j2ee@gmail.com"));;
        msg.setRecipients(Message.RecipientType.TO,
        InternetAddress.parse("jegonday.solene@gmail.com", false));
        msg.setSubject("winterIsComing "+System.currentTimeMillis());
        msg.setText("Votre commande est validée ");
        msg.setSentDate(new Date());
        
        Multipart multipart = new MimeMultipart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Votre commande:");
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource("C:\\Users\\inti-0257\\Desktop\\formation\\Commande.pdf");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("commande.pdf");
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


