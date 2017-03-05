package com.vms.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

//This cannot run until we get a persistence.xml file inside of META-INF

public class ModelsTesting {
	
	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("JPAExamples");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		//Testing that the entity as a whole works
		Vendor vendor0 = new Vendor();
		vendor0.setVendor_id(12345);
		vendor0.setPrimary_email("testmail@testing.com");
		vendor0.setName("Testing Incorporated");
		vendor0.setContact_name("GLaDOS");
		vendor0.setAddress("Not Black Mesa");
		vendor0.setCity("CapitalCity");
		vendor0.setState("WA");
		vendor0.setPhone("555-555-5555");
		
		System.out.println(vendor0);
		
		trans.begin();
		em.persist(vendor0);
		trans.commit();
		
		//Testing if the entity will respond to a transaction request
		trans.begin();
		Vendor vendor1 = em.find(Vendor.class, 1);
		System.out.println(vendor1);
		trans.commit();
	}
	
	
}
