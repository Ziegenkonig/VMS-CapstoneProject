package com.vms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import security.ActiveUserStore;

@EnableJpaRepositories("com.vms.repositories")
@SpringBootApplication
public class HpTechSystemsVmsApplication {

	@Bean
    public ActiveUserStore activeUserStore(){
        return new ActiveUserStore();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(HpTechSystemsVmsApplication.class, args);
	}
}
