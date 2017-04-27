package com.vms;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
	
	@Bean
    public ActiveUserStore activeUserStore(){
        return new ActiveUserStore();
    }
	
	//requires https://www.google.com/accounts/DisplayUnlockCaptcha to successfully authenticate login
	@Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
         
        //Using gmail
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("uofmcapstonebanana@gmail.com");
        mailSender.setPassword("alwaysmoneyinthebananastand123");
         
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");//Prints out everything on screen
         
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(HpTechSystemsVmsApplication.class, args);
	}
}
