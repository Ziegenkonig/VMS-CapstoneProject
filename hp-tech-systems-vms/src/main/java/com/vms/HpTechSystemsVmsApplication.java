package com.vms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vms.utilities.Mail;

@EnableJpaRepositories("com.vms.repositories")
@SpringBootApplication
public class HpTechSystemsVmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HpTechSystemsVmsApplication.class, args);
		Mail.sendEmail();
	}
}
