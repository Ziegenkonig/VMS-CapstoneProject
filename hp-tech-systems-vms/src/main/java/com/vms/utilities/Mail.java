package com.vms.utilities;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.vms.models.*;

public class Mail {
	public static void sendEmail() {
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		final String username = "uofmcapstonebanana@gmail.com";//
		final String password = "";
		try {
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			// -- Create a new message --
			Message msg = new MimeMessage(session);

			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress("uofmcapstonebanana@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("", false));
			// -- Set the Subject message -- 
			msg.setSubject("Invoice Notice");
			// -- Set the text message --
			Employee testEmployee = new Employee();
			testEmployee.setFirstname("Josh");
			msg.setText(generateTextMessage(testEmployee));
			
			// -- set the date --
			msg.setSentDate(new Date());
			Transport.send(msg);
			System.out.println("Message sent.");
		} catch (MessagingException e) {
			System.out.println("Erreur d'envoi, cause: " + e);
		}
	}
	private static String generateTextMessage(Employee employee){
		//get an employee as input to the method, along with some othe rkind of necessary details, then 
		//convert it into a string that can be sent as a text message in the email. 
		String greeting = "Hello " + employee.getFirstname() + ", this is your name!\n";
		
		// -- Want to generate a URL that links to the invoices left to fill out, or the invoices that need to be viewed or whatever --
		String url = "";
		
		return "" + greeting;
	}
	private String timesheetSubmittedNotification(Employee employee){
		//employee has submitted their timesheet, so the employer needs to know
		String output = "" + employee.getFirstname() + " " + employee.getLastname() + " has submitted a timesheet";
		return output;
	}
	}

