package fr.adaming.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.MailSessionDefinition;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ClassTestMail2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// Recipient's email ID needs to be mentioned.
			String to = "jegonday.solene@gmail.com";// change accordingly
			String from = "jegonday.solene@gmail.com";// change accordingly
			final String username = "jegonday.solene@gmail.com";// change
																// accordingly
			final String password = "56asuano94";// change accordingly
			String host = "smtp.gmail.com";
			String subject = "Test Java mail";
			String textMessage = "Bonjour!";

			Boolean sessionDebug = false;

			Properties props = System.getProperties();

			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.starttls.required", "true");

			java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			// Get the Session object.
			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(sessionDebug);

			// Create a default MimeMessage object.
			Message msg = new MimeMessage(mailSession);

			// Set From: header field of the header.
			msg.setFrom(new InternetAddress(from));

			InternetAddress[] address = { new InternetAddress(to) };
			// Set To: header field of the header.
			msg.setRecipients(Message.RecipientType.TO, address);

			// Set Subject: header field
			msg.setSubject(subject);
			msg.setSentDate(new Date());

			// Now set the actual message
			msg.setText(textMessage);

			// Send message
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(host, username, password);
			Transport.send(msg, msg.getAllRecipients());
			transport.close();

			System.out.println("Sent message successfully....");
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

}
