package com.smartcontact.services;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailServices {

	public boolean sendEmail(String subject, String message, String to) {
		
		boolean f = false;
		String host = "smtp.gmail.com";
		
		String from = "honeygain999@gmail.com";
		
		Properties properties = System.getProperties();
		
		
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth","true");
		
		Session session = Session.getInstance(properties , new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("honeygain999@gmail.com","pijefiniicwepguc");
			}
		});
		
		session.setDebug(true);

		MimeMessage m = new MimeMessage(session);
		try {
			
			m.setFrom(from);
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			m.setSubject(subject);
			// m.setText(message);
			m.setContent(message,"text/html");
			Transport.send(m);
			System.out.println("sent success...");
			f = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;
	}
}
