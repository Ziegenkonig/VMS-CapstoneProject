package com.vms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vms.models.Invoice;
import com.vms.models.InvoiceStatus;
import com.vms.models.Paystub;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{
	//find all invoices by vendor name
	@Query(
		value = "SELECT * FROM invoices i WHERE i.name = :vendor_name", 
		nativeQuery=true
	)
	public List<Invoice> findByVendor(@Param("vendor_name") String vendor_name);
	
	//find all invoices by vendor name for ytd 
	@Query(
		value = "SELECT * FROM invoices i WHERE i.name = :vendor_name AND i.status = com.vms.InvoiceStatus.PENDING OR i.status = com.vms.InvoiceStatus.PAID", 
		nativeQuery=true
	)
	public List<Invoice> findByVendorYTD(@Param("vendor_name") String vendor_name);
	
	//find all invoices by status
	@Query
	public List<Invoice> findByStatus(InvoiceStatus status);
	
	public List<Invoice> findByProjectIdOrderByPeriodStartDesc(int proj_id);
}
