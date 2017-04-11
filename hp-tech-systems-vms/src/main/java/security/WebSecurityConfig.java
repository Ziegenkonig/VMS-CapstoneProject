//package security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.context.annotation.*;
//import org.springframework.security.config.annotation.authentication.builders.*;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.*;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("user").password("password").roles("USER").build());
//		manager.createUser(User.withUsername("admin").password("password").roles("USER","ADMIN").build());
//		return manager;
//	}
//	
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//				.anyRequest().authenticated()
//				.and()
//			.formLogin()
//			.loginPage("/login")
//			.permitAll(); 
////		http
////		.authorizeRequests()                                                                
////		.antMatchers("/resources/**", "/signup", "/about").permitAll()  //patterns that any user can access. Specifically, any user can access a request if the URL starts with "/resources/", equals "/signup", or equals "/about".              
////		.antMatchers("/admin/**").hasRole("ADMIN") //Any URL that starts with "/admin/" will be restricted to users who have the role "ROLE_ADMIN".
////		.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')") //Any URL that starts with "/db/" requires the user to have both "ROLE_ADMIN" and "ROLE_DBA".
////		.anyRequest().authenticated(); //Any URL that has not already been matched on only requires that the user be authenticated
//	}
//}