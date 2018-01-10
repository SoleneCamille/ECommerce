package fr.adaming.mail;

import java.util.Properties;
import java.util.Date;

import javax.mail.*;

import javax.mail.internet.*;

import com.sun.mail.smtp.*;


public class TestMail3 {

	public static void main(String[] args) throws AddressException, MessagingException {
		// TODO Auto-generated method stub

		
		


	
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
		        SMTPTransport t =
		            (SMTPTransport)session.getTransport("smtps");
		        t.connect("smtp.gmail.com", "application.j2ee@gmail.com", "adamingintijee");
		        t.sendMessage(msg, msg.getAllRecipients());
		        System.out.println("Mail envoyé");
		        t.close();
		    }
		
	
	
	

}
