package pkg.myconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class config extends WebSecurityConfigurerAdapter{
//
//	@configuration In spring boot
//	Spring Boot lets you externalize your configuration so that you can work with the same application code in different
//	environments. You can use properties files, YAML files, environment variables, and command-line arguments to externalize configuration.
//	
	
	@Bean 
	public UserDetailsService getUserDetailService() {
		return new UsarDetailsImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	
	@Bean
	public DaoAuthenticationProvider AuthenticationProvider() {
		DaoAuthenticationProvider DaoAuthenticationProvider =new DaoAuthenticationProvider();
		DaoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
		DaoAuthenticationProvider.setPasswordEncoder( passwordEncoder());
		return DaoAuthenticationProvider;
	}

	//below method finally authenticates/verifies methods
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
auth.authenticationProvider( AuthenticationProvider())	;
}

	
	//below method declares routs for classes,pages,urls to be authorised after authentication

	@Override
	protected void configure(HttpSecurity http) throws Exception {
http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
.antMatchers("/user/**").hasRole("USER")
.antMatchers("/**").permitAll()                                                                 
 .and().formLogin().loginPage("/signin").loginProcessingUrl("/dologin").defaultSuccessUrl("/user/userhome").failureUrl("/user/errorPage")
 .and().csrf().disable();  

/*some more  functions are  
 * loginPage() -- the customised login page 
 * loginProcessingUrl() --the url to submit username and password also mention the same destination in th:action
 * defaultSuccessUrl()  -- default page after login 
 * failureUrl() -- deault page if login fails
 * */
                            
/*-->we have used custom login page i.e /login but make sure the inputs are named as
 'username' &'password' as needed to work for WebSecurityConfigurerAdapter
 
--> the 'USER' & 'ADMIN'  etc must be in uppercase & in data base the it must be saved as 'ROLE_USER' , 'USER_ADMIN' etc */
}
	
	
	 

	/*We can use @Component across the application to mark 
	 * the beans as Spring's managed components. Spring will 
	 * only pick up and register beans with @Component, and doesn't 
	 * look for @Service and @Repository in general. @Service and 
	 * @Repository are special cases of @Component.*/
	
	
	//in this 'authentication' means asking for passwords 
	//'authorising' means giving asscess after 'authentication'
	
	
	
	
	
	
	
	
	/*-x-x-x-x--x-xx--xx-x-x-x-x-x-x-x-x-x-x-x-x-x-x--x*/
}
