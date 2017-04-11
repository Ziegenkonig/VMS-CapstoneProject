package com.vms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import security.ActiveUserStore;

@EnableJpaRepositories("com.vms.repositories")
@SpringBootApplication
@EnableCaching
public class HpTechSystemsVmsApplication {

	@Bean

    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    public ActiveUserStore activeUserStore(){
        return new ActiveUserStore();

    }
	
	public static void main(String[] args) {
		SpringApplication.run(HpTechSystemsVmsApplication.class, args);
	}
}
