package pkg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContactManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactManagerApplication.class, args);
		
		/*We can use @Component across the application to mark 
		 * the beans as Spring's managed components. Spring will 
		 * only pick up and register beans with @Component, and doesn't 
		 * look for @Service and @Repository in general. @Service and 
		 * @Repository are special cases of @Component.*/
		
		
		//in this 'authentication' means asking for passwords 
		//'authorising' means giving asscess after 'authentication'
	}

}
