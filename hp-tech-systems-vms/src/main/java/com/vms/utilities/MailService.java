package com.vms.utilities;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.vms.models.Employee;
import com.vms.models.Invoice;
import com.vms.models.Paystub;
import com.vms.models.Timesheet;
import com.vms.models.Vendor;
import com.vms.services.EmployeeService;
import com.vms.services.VendorService;

@Service
public class MailService {
	
	@Autowired
    JavaMailSender mailSender;
	
	@Autowired
	EmployeeService empService = new EmployeeService();
	@Autowired
	VendorService vendService;
	
	String testEmail = "ziegenkonigreality@gmail.com";
//	String testEmail = "ktred63@gmail.com";
	String vmsEmail = "uofmcapstonebanana@gmail.com";
 
    public void sendEmail(Object object, String type) {
 
        MimeMessagePreparator preparator;
        switch(type) {
        case("paystub"):
        	preparator = generatePaystubNotification((Paystub) object);
        	break;
        case("timesheetAvailable"):
        	preparator = generateTimesheetAvailableNotification((Timesheet) object);
    		break;
        case("timesheetSubmitted"):
        	preparator = generateTimesheetSubmittedNotification((Timesheet) object);
    		break;
        case("timesheetAlmostDue"):
        	preparator = generateTimesheetAlmostDueNotification((Timesheet) object);
    		break;
        case("employeeRegistration"):
        	preparator = generateEmployeeRegistrationNotification((Employee) object);
    		break;
        case("timesheetCorrection"):
        	preparator = generateTimesheetReturnedNotification((Timesheet) object);
			break;
        case("invoiceReady"):
        	preparator = generateInvoiceReadyNotification((Invoice) object);
			break;
    	default: 
    		preparator = null;
        }
        
 
        try {
            mailSender.send(preparator);
            System.out.println("Message Send...Hurray");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private MimeMessagePreparator generatePaystubNotification(final Paystub ps) {
 
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(new InternetAddress(vmsEmail));
                //Employee e = empService.findOne(ps.getEmpId());
                //mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(e.getEmail()));
                //for testing
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(testEmail));
                mimeMessage.setText("Dear " + ps.getFirstname() + " " + ps.getLastname()
                        + ", your paystub for the period " + ps.getPeriodStart() + " to " + ps.getPeriodEnd()
                        + " has been issued.");
                mimeMessage.setSubject("Paystub Issued");
            }
        };
        return preparator;
    }
    
    private MimeMessagePreparator generateTimesheetAvailableNotification(final Timesheet t) {
    	 
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(new InternetAddress(vmsEmail));
                Employee e = t.getEmployee();//empService.findOne(ps.getEmpId());
                //mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(e.getEmail()));
                //for testing
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(testEmail));
                mimeMessage.setText("Dear " + e.getFirstname() + " " + e.getLastname()
                        + ", your timesheet for the period starting" + t.getWeekStarting()
                        + " is now available.");
                mimeMessage.setSubject("Timesheet Available");
            }
        };
        return preparator;
    }
    
    private MimeMessagePreparator generateTimesheetSubmittedNotification(final Timesheet t) {
   	 
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(new InternetAddress(vmsEmail));
                Employee e = t.getEmployee();//empService.findOne(ps.getEmpId());
                //mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(e.getEmail()));
                //for testing
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(testEmail));
                mimeMessage.setText("Dear Admin"
                        + ", " + e.getFirstname() + " " + e.getLastname()
                        + " has submitted a timesheet for approval.");
                mimeMessage.setSubject("Timesheet Submitted");
            }
        };
        return preparator;
    }
    
    private MimeMessagePreparator generateTimesheetReturnedNotification(final Timesheet t) {
     	 
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(new InternetAddress(vmsEmail));
                Employee e = t.getEmployee();//empService.findOne(ps.getEmpId());
                //mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(e.getEmail()));
                //for testing
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(testEmail));
                mimeMessage.setText("Dear " + e.getFirstname() + " " + e.getLastname()
		                + ", your timesheet for the period starting" + t.getWeekStarting()
		                + " has been returned by an admin for correction.");
                mimeMessage.setSubject("Timesheet Returned for Correction");
            }
        };
        return preparator;
    }
    
    //not finished - requires scheduling checks and due date in timesheet
    private MimeMessagePreparator generateTimesheetAlmostDueNotification(final Timesheet t) {
      	 
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(new InternetAddress(vmsEmail));
                Employee e = t.getEmployee();//empService.findOne(ps.getEmpId());
                //mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(e.getEmail()));
                //for testing
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(testEmail));
                mimeMessage.setText("Dear " + e.getFirstname() + " " + e.getLastname()
		                + ", your timesheet for the period starting" + t.getWeekStarting()
		                + " is due by.");
                mimeMessage.setSubject("Timesheet Submission Deadline Approaching");
            }
        };
        return preparator;
    }
    
    //not finished - requires hash for unique email link
    private MimeMessagePreparator generateEmployeeRegistrationNotification(final Employee e) {
  		System.out.println("test");
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(new InternetAddress(vmsEmail));
                //mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(e.getEmail()));
                //for testing
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(e.getEmail()));
                mimeMessage.setText("Dear future employee, you may complete your registration using the link below.\n"
		                + "http://localhost:8080/register/" + e.getRegistrationUrl());
                mimeMessage.setSubject("Complete Your Registration with HpTechSystems' VMS");
            }
        };
        return preparator;
    }
    
    private MimeMessagePreparator generateInvoiceReadyNotification(final Invoice i) {
    	 
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(new InternetAddress(vmsEmail));
                Vendor v = vendService.findOne(i.getVendorId());
                //mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(v.getPrimaryEmail()));
                //for testing
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(testEmail));
                mimeMessage.setText("Dear " + v.getContactName()
		                + ", an invoice for the period " + i.getPeriodStart() + " to " + i.getPeriodEnd()
		                + " has been generated. Payment is due by " + i.getPaymentDue() + ".");
                mimeMessage.setSubject("HpTechSystems Invoice Ready");
            }
        };
        return preparator;
    }
	
}