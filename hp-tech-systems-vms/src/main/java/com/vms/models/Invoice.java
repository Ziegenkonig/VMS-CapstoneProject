package com.vms.models;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int invoice_id;
	
	@Column()
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status;
	
	@Column()
	private Date period_start;
	@Column()
	private Date period_end;
	@Column()
	private Date payment_due;
	@Column()
	private String recruiter;
	
	@Column()
	private double total_hours;
	@Column()
	private BigDecimal total_amt;
	
	//should this be here or FK to project
	@Column()
	private BigDecimal rate;
	
}
