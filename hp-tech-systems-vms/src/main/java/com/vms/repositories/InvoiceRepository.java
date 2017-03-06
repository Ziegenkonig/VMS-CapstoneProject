package com.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vms.models.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{

}
