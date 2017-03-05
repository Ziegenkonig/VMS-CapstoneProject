package com.vms.models;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Invoice {

	@Id @GeneratedValue()
	private int invoice_id;
	
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status;
	
	private Date period_start;
	private Date period_end;
	private Date payment_due;
	private String recruiter;
	
	private double total_hours;
	private BigDecimal total_amt;
	
	//should this be here or FK to project
	private BigDecimal rate;
	
}
