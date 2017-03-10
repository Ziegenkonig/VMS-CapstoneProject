package com.vms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vms.models.Invoice;
import com.vms.models.InvoiceStatus;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{
		
	//find all invoices by vendor name for ytd 
	@Query(
		value = "SELECT * FROM invoices i WHERE i.name = :vendor_name AND i.status = com.vms.InvoiceStatus.PENDING OR i.status = com.vms.InvoiceStatus.PAID", 
		nativeQuery=true
	)
	public List<Invoice> findByVendorYTD(@Param("vendor_name") String vendor_name);
	
	//find all invoices by status
	@Query
	public List<Invoice> findByStatus(InvoiceStatus status);
	
	//find all invoices by project
	@Query
	public List<Invoice> findByProjectIdOrderByPeriodStartDesc(int proj_id);
	
	//find all invoices by vendor
	@Query
	public List<Invoice> findByVendorIdOrderByPeriodStartDesc(int vendor_id);

}
