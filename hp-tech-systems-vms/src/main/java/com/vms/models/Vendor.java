package com.vms.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.vms.utilities.mail.Mail;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data //This annotation automatically generates getters/setters for this entity
@NoArgsConstructor
@Entity //Tells Hibernate that this class is to be treated as an entity
@Table(name = "vendors") //Specifies what the table name is
public class Vendor {

	//Primary Key
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vendorId;
	
	//Regular ol Attributes
	
	@NotNull()
	@Size(min = 2, max = 120, message = "{irregular.size}")
	@Pattern(regexp = "[a-zA-Z\\s-_.]*", message = "{irregular.pattern}")
	@Column(length = 120, nullable = false)
	private String name;
	
	@NotNull()
	@Size(min = 6, max = 64, message = "{email.size}")
	@Pattern(regexp = "[a-zA-Z0-9_-]*+@+[a-zA-Z0-9-]*+\\.+[A-Za-z]*{2,6}", message = "{email.pattern}")
	@Column(length = 64, nullable = false)
	private String primaryEmail;
	
	@NotNull()
	@Size(min = 2, max = 64, message = "{fullName.size}")
	@Pattern(regexp = "[a-zA-Z]*+\\s+[a-zA-Z]*", message = "{fullName.pattern}")
	@Column(length = 64, nullable = false)
	private String contactName;
	
	@NotNull()
	@Size(min = 5, max = 120, message = "{address.size}")
	@Pattern(regexp = "[0-9]*+\\s+[a-zA-Z0-9\\s.]*", message = "{address.pattern}")
	@Column(length = 120, nullable = false)
	private String address;
	
	@NotNull()
	@Size(min = 2, max = 64, message = "{city.size}")
	@Pattern(regexp = "[a-zA-Z]*", message = "{city.pattern}")
	@Column(length = 16, nullable = false)
	private String city;
	
	//No need to be authenticated, it is a dropdown box
	@Column(length = 2, nullable = false)
	private String state; //Should be two capital letters
	
	//@NotNull()
	@Size(min = 5, message = "{zipcode.size}")
	@Pattern(regexp = "[0-9]*{5}", message = "{zipcode.pattern}")
	@Column(length = 32, nullable = true)
	private String zipcode;
	
	@NotNull()
	@Size(min = 10, max = 20, message = "{phone.size}")
	@Pattern(regexp = "[0-9]*{10}|[0-9]*{11}", message = "{phone.pattern}")
	@Column(length = 20, nullable = false)
	private String phone; //not including non-numerical characters
	
	@OneToMany(mappedBy="vendor")//, cascade = CascadeType.ALL)
	private List<Project> projects;
	
	public String toString() {
		return name;
	}
	
	public void notifyInvoiceCompletion(){
		//this.email = the current instance of vendor that has an invoice created
		String message = "An employee has submitted an invoice. Go here to check it: http://localhost:8080/invoice";
		String subject = "An invoice has been created";
		Mail.sendEmail(this.primaryEmail, message, subject);
	}
	
}