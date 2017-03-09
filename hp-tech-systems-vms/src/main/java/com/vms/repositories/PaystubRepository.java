package com.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vms.models.Paystub;

public interface PaystubRepository extends JpaRepository<Paystub, Integer>{

}
