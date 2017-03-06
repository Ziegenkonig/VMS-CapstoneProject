package com.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vms.models.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
	Vendor findByName(String name);
}
