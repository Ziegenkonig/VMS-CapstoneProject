package com.vms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vms.models.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {
	
	@Query
	public Vendor findByName(String name);
	
	@Query
	public List<Vendor> findAllByOrderByNameAsc();
	
	//Only selects the names from the vendors table to reduce run time
	@Query (
		value = "SELECT name FROM vendors ORDER BY name ASC",
		nativeQuery = true
	)
	public List<String> findAllVendorNames();
}
