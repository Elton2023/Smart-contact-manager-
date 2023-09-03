package pkg.controller;

 
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

 
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UsernameNotFoundException.class)//this annotation will capture eurro if its of a type in in its parameter
	public String resourceNotFoundExceptionHandeler(UsernameNotFoundException ex){
	 
		return "/users/errorPage";
	}
	
	@ExceptionHandler(NullPointerException.class) 
	public String NullPointerException(NullPointerException np){
	 System.out.println("NullPointerException invoked");
		return "/users/errorPage";
	}
 

}
