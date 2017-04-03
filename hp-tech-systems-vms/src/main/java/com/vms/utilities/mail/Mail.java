package com.vms.utilities.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.vms.models.Employee;

public class Mail {
	public static void sendEmail() {
		MailSender sender = new MailSender();
		try {
			Session session = Session.getInstance(sender.getProps(), new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(sender.getUsername(), sender.getPassword());
				}
			});

			// -- Create a new message --
			Message msg = new MimeMessage(session);

			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress("uofmcapstonebanana@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jbhtcher@memphis.edu", false));
			// -- Set the Subject message -- 
			msg.setSubject("InvoiceNotice");
			// -- Set the text message --
			Employee testEmployee = new Employee();
			testEmployee.setFirstname("Josh");
			msg.setText("pls");
			
			// -- set the date --
			msg.setSentDate(new Date());
			Transport.send(msg);
			System.out.println("Message sent.");
		} catch (MessagingException e) {
			System.out.println("Erreur d'envoi, cause: " + e);
		}
	}
	}

