package com.vms.utilities;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//This class is used to encode strings using the spring security library
//This class also has secure random number generator

public class HashSlingingSlasher {
	//Secure random for randomly generating secure bits
	private SecureRandom random;
	
	//Constructor to intialize the SecureRandom once -- it's expensive
	public HashSlingingSlasher() {
	
		this.random = new SecureRandom();
		
	}
	
	//Hashes the info and returns the result
	public String encode(String info) {
		
		//Create a BCryptPasswordEncoder object so that we can call its methods
		BCryptPasswordEncoder infoEncoder = new BCryptPasswordEncoder();
		
		//encodes info and returns the hashed form using infoEncoder object
		return infoEncoder.encode(info);
	}
	
	//Takes two strings, and uses an encoder to check if the two are equivalent
	public boolean decode(String info, String hashedInfo) {
		
		//Create a BCryptPasswordEncoder object so that we can call its methods
		BCryptPasswordEncoder infoDecoder = new BCryptPasswordEncoder();
		
		//Checks the two strings using infoDecoder and returns true/false
		return infoDecoder.matches(info, hashedInfo);
	}
	
	//Generates a new, secure, randomly generated string that is used to access the registration page
	public String secureRegistration() {
		
		return new BigInteger(75, random).toString(32);
		
	}
	
	
	//Testing -- results in success
//	public static void main(String[] args) {
//		
//		Encoder encoder = new Encoder();
//		
//		String info = "password";
//		
//		String hashedInfo = encoder.encode(info);
//		
//		System.out.println( encoder.decode(info, hashedInfo) );
//		
//		String info = encoder.secureRegistration();
//		
//		String hashedInfo = encoder.encode(info);
//		System.out.println("Database value: " + hashedInfo);
//		
//		System.out.println("Match: " + encoder.decode(info, hashedInfo) );
//	}
}
