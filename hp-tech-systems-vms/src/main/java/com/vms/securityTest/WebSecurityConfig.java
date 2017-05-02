package com.vms.securityTest;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//dataSource is basically a reference point to our DB for security to pull from
	@Autowired
	private DataSource dataSource;
//	//We need to specify an encoder so that we can check our raw passwords against the hashed ones inside the DB
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
//	//Our bean for the encoder
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
//	//Our bean for the users, not sure if we need this anymore
	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("user").password("password").roles("USER").build());
		return manager;
	}

//	//This is our authentication configuration station, where we specify what security should be checking the
//	//usernames and passwords against
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication()
				.dataSource(dataSource) //Our database
				.usersByUsernameQuery("SELECT username,password,active FROM employees WHERE username=?") //Query for regular users, not sure how to specify specific roles yet
				.authoritiesByUsernameQuery("SELECT username,permission_level FROM employees WHERE username=?") //not sur eif this is needed
				.passwordEncoder(bCryptPasswordEncoder); //specify encoder that security uses to match raw vs hashed password
	}
	
	//This configuration station is for general things in security
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/register/**", "/emailConfirmationNotification/**", "/emailConfirmation/**").permitAll()
				.antMatchers("/admin", "/admin/**", "/inviteEmployee", 
							 "/project/new", "/projects/all", "/project/view**", "/project/edit**",  "/project/addEmployee**",
							 "/vendor/new", "/vendors", "/vendor/view**", "/vendor/edit**",
							 "/employees", "/timesheet/new", "/timesheets/**", 
							 "/invoice/new", "/invoices/", "invoice/view**")
							 .access("hasRole('ADMIN') or hasRole('OWNER')")
				.anyRequest().authenticated() //We allow all users to access all pages here
				.and()
			.formLogin() //here specify custom login page
////				.loginPage("/login")
////				.permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
				.and()
////			.csrf() //disabled csrf token for now because it was messed up for me
////				.disable()
			.httpBasic();
	}
	
}