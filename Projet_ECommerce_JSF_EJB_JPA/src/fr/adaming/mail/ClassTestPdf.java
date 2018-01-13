package fr.adaming.mail;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.ejb.EJB;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import fr.adaming.model.Commande;
import fr.adaming.model.LignesCommande;
import fr.adaming.service.ILignesCommandeService;
import fr.adaming.service.LignesCommandeServiceImpl;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
public class ClassTestPdf {

	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		// TODO Auto-generated method stub

		
		ILignesCommandeService ligneService = new LignesCommandeServiceImpl();
		
		   
			/* Create a new Document object */
			Document document = new Document();
			 PdfWriter.getInstance(document, new FileOutputStream("Commande2.pdf"));
			try {
			    /* Associate the document with a PDF writer and an output stream */
			    PdfWriter.getInstance(document, new FileOutputStream("Commande2.pdf"));
		 
			    /* Open the document (ready to add items) */
			    document.open();
		 
			    /* Populate the document (add items to it) */ 
			    Commande comDefaut = new Commande();
				comDefaut.setIdCommande(1);
				List<LignesCommande> liste=ligneService.getAllLignes(comDefaut.getIdCommande());
			document.add((Element) liste);
			    //populate(document);
			}
			catch(Exception e) {
			    /* Oups */
			    System.err.println(e);
			}
			finally {
			    /* Don't forget to close the document! */
			    document.close();
			   
			}
		    
		 
		    /** 
			Populate the document by adding some elements
		    */
//		    public void populate(Document document) throws DocumentException {
//			/* add a news paragraph */
//		    	Commande comDefaut = new Commande();
//				comDefaut.setIdCommande(1);
//			document.add(ligneService.getAllLignes(comDefaut.getIdCommande()));
//		    }
		 
		    /**
			Main program.
		 
			Build and run an instance of HelloiText.
		    */
//		    public static void main(String[] args) {
//			HelloiText  app = new HelloiText();
//			app.run();
//		    }
//		
		
		
	}

}
