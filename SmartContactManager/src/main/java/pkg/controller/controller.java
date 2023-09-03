package pkg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pkg.Repozitory.userRepo;
import pkg.additional.message;
import pkg.entities.contact;
import pkg.entities.usar;

@Controller
public class controller {
	
 @Autowired
	private userRepo  urep;
 
 
 @Autowired
	private BCryptPasswordEncoder psde;
 
 

@RequestMapping(value="/registered",method=RequestMethod.POST)
public String registered(@Valid @ModelAttribute("usar") usar usar,BindingResult br,@RequestParam(value="agreement",defaultValue="false") boolean agreement,Model m,HttpSession sesn) {
	 
	try {
		if(agreement==false) {
		 	m.addAttribute("title", "t&C issue");
			System.out.println("Please agree Tearm & condition to get registered");
			sesn.setAttribute("msg", new message("Please agree Tearm & condition to get registered","alert-error"));
 			return"signup";

		}else if(br.hasErrors()) {
			System.out.println("An error occured while registering you"+br.toString());
			sesn.setAttribute("msg", new message("An error occured while registering you","alert-error"));
			m.addAttribute("usar", usar);

             return"signup";
	
		}else if (usar.equals(null)||usar==null) {
			m.addAttribute("usar", usar);
			sesn.setAttribute("msg", new message("please fill all details","alert-error"));
       return"signup";
	}
 		usar.setEnabled(true);
		usar.setPassword(psde.encode(usar.getPassword()));
	      usar.setRole("ROLE_USER");
 		usar res= this.urep.save(usar);
 		m.addAttribute("title", "welcom boss");
		System.out.println("welcom boss");
		m.addAttribute("usar", res);
		System.out.println("the registerd memeber details are "+res);
		sesn.setAttribute("msg", new message("suscessfully registered","alert-success"));
 
		return "registered";

	} catch (Exception e) {
System.out.println("error while processing"+e);
m.addAttribute("usar", usar);
System.out.println("Technical errror while registering ");
sesn.setAttribute("msg", new message("Something went Wrong"+e.getMessage(),"alert-danger"));
e.printStackTrace();
return"signup";


	}}

	@GetMapping("/test")
	@ResponseBody
	public String test() {
		usar usr =new usar();
		 contact c1 =new contact();
		 contact c2 =new contact();

		usr.setName("elton");
		usr.setEmail("fernandeselton2897@gmail.com");
		usr.setPassword(psde.encode("fernandeselton2897"));
		usr.setRole("Admin");
		usr.setEnabled(true);
	    usr.setAbout("deus vult");
		 
	    c1.setContactname("Dynisha_Abreu");
	    c1.setNickname("dun");
	    c1.setEmail("Dynisha2Abreu29@gmail.com");
	    c1.setJob("CEO_At_DMajors's");
	    c1.setPhonenumber("+917083445393");
	    c1.setDiscription("Landscapers daughter");
		 c1.setUsr(usr);

	  c2.setContactname("sneha cardoso");
	  c2.setNickname("sne7597");
	  c2.setEmail("Snehacardoso15@gmail.com");
	  c2.setJob("psychologist");
	  c2.setPhonenumber("+919623269302");
	  c2.setDiscription("Bakers Daughter");
     c2.setUsr(usr);
     
List<contact> c=new ArrayList<contact>();
c.add(c1);
c.add(c2);
usr.setContacts(c);
System.out.println(usr);
		urep.save(usr);
 		return "ohkk saved to database named 'smartcontactmanager' ";
	}
	
	

@RequestMapping("/about")
public String about(Model model2) {
	model2.addAttribute("title", "info on smartcontactmanager");
	System.out.println("Inside page infomation i.e 'about'");
	return"about";
}
@RequestMapping("/signup")
public String signup(Model model3) {
	model3.addAttribute("title", "Register to smartcontactmanager");
	model3.addAttribute("usar", new usar());//this empty is to activate the object of thymeleaf in the  signup page
	System.out.println("ready to sign-up");
	
	return"signup";
}


@RequestMapping("/home")
public String home(Model model1) {
	model1.addAttribute("title", "welcome to smartcontactmanager");
	System.out.println("Inside home page");
	return"home";
	}

  @GetMapping("/signin")
 public String login(Model l) {
 	l.addAttribute("title", "Login");
 	System.out.println("Inside login page");
 	return"login";
 	
 }
   
  

/*------------------------------------------------------------------------------------------------------------------------------------------------*/
}
