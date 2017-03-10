package com.vms.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data; 

@Data //standard getters/setters
@Entity
@Table(name="invoices")
public class Invoice {

	@Id @GeneratedValue
	private int invoiceId;
	// reference
	private int projectId;
	private int vendorId;
	
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status;
	
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate createdDate;
	
//fields to store info from other tables
	//from project
	private BigDecimal rate; //timesheet.projemp.project.pay_rate
	
	private double totalHours; //sum(timesheet.no_hours) over timesheet list
	private BigDecimal totalAmt; //total_hours * rate
	
	//pay period start and end (14/7 day interval)
	private LocalDate periodStart;
	private LocalDate periodEnd;

	// one month from period_end
	private LocalDate paymentDue;
	
	//from vendor.contact_name
	private String recruiter;
	
	//from vendor
	private String name;
	private String address;
	private String city;
	private String state;
	private String phone;
	
	//employer info saved in global variables somewhere
	
	//constructor
	public Invoice(List<Timesheet> timesheets) {
		//Setting timesheets
		this.timesheets = timesheets;
		Timesheet ts = timesheets.get(0); //grabbing the first timesheet in the list
		
		//Setting period_start/period_end based on info from timesheet
		this.periodStart = ts.getWeek_starting();
		ProjectEmployee proj_emp = ts.getProjemp();
		if (proj_emp.getEmployee().getPay_period() == 2)
			this.periodEnd = this.periodStart.plusDays(14);
		else
			this.periodEnd = this.periodStart.plusDays(7);
		
		//Setting payment_due
		this.paymentDue = this.paymentDue.plusMonths(1);
		
		//Grabbing the vendor out of the timesheet object and using it to set all kinds of info
		Project proj = proj_emp.getProject();
		this.projectId = proj.getProject_id();
		Vendor vendor = proj.getVendor();
		this.vendorId = vendor.getVendor_id();
		this.name = vendor.getName();
		this.address = vendor.getAddress();
		this.city = vendor.getCity();
		this.state = vendor.getState();
		this.phone = vendor.getPhone();
		this.recruiter = vendor.getContactName();
		
		//Setting rate
		this.rate = proj_emp.getProject().getBillingRate();
		
		//Setting total_hours
		this.totalHours = 0;
		for(Timesheet i : timesheets)
			this.totalHours += i.getNoHours();
		
		//Setting total_amount
		this.totalAmt = this.rate.multiply(BigDecimal.valueOf(totalHours));
	}
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "invoices", cascade = CascadeType.ALL)
    private List<Timesheet> timesheets; 
	
	//Called before .save
	@PrePersist
	protected void onCreate() {
		createdDate = LocalDate.now();
	}
	
	//toString
	public String toString() {
		return ("Vendor: " + name + 
				" Dates: " + periodStart + " - " + periodEnd);
	}
	
}
