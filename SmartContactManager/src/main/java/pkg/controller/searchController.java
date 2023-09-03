package pkg.controller;

 import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

 import pkg.Repozitory.contactRepo;
import pkg.Repozitory.userRepo;
import pkg.entities.contact;
import pkg.entities.usar;

@RestController
public class searchController {
	@Autowired
	private userRepo ur;
	@Autowired
	private contactRepo cr;
	
    @GetMapping("/search/{query}")
	public ResponseEntity<?> searching(@PathVariable("query")String query,Principal principal){
    	System.out.println("the user searched for "+query);
    	usar usar =this.ur.getUsarByEmail(principal.getName());
    List<contact> results=	this.cr.findByEmailContainingAndUsr(query,usar);
	System.out.println("the user's reult "+results);

		return ResponseEntity.ok(results);
		
	}
	
	//-------------------------------------------------------------------------------------------------
}
