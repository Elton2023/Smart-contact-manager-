package pkg.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pkg.Repozitory.contactRepo;
import pkg.Repozitory.userRepo;
import pkg.additional.message;
import pkg.entities.contact;
import pkg.entities.usar;
import pkg.servic.services;
 
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private BCryptPasswordEncoder enc;
	
	@Autowired
	private userRepo  urep;
	
	@Autowired
	private contactRepo corp;
	
	
	
	@Autowired
	private services emsvc;
	
	//below we are creating a system that will send user data to all pages accesed via this controller
	@ModelAttribute 
	public void commonData(Model m ,Principal popy) {
		/*Principal is part of 'spring-security' it 
		 * gets the unique value( usally 'id' is unique but here for spring security username/email is unqie, can 
		 * be used to represent any entity, such as an individual, acorporation, and a login id.The principal is the currently logged in user.
		 *  However, you retrieve it through the security context which is bound to the current thread 
		 *  and as such it's also bound to the current request and its session..
		 */
		String Principal = popy.getName(); 
		System.out.println("(common-arrribt) logged in user -->  "+Principal);
		usar usar =this.urep.getUsarByEmail(Principal);
		m.addAttribute("usar", usar);
		//System.out.println("the user details"+usar);
	}
	
	@RequestMapping("/index")
     public String dashboard(Model m  ) {
//		String Principal = popy.getName(); 
//		System.out.print("the username is "+Principal);
//		usar usar =this.urep.getUsarByEmail(Principal);
//		m.addAttribute("usar", usar);
//		System.out.print("the user details"+usar);
		m.addAttribute("title", "Dashboard");
		 vel();
		
		return "/users/dashboard";
		
	}
	
	 @GetMapping("/errorPage")
	  public String errorPage(Model e) {
	  	e.addAttribute("title", "Error to login");
	  	System.out.println("Inside Error page");
	  	return"/users/errorPage";
	  	
	  }
	 
	 
	 
	 @GetMapping("/contact_form")
	  public String contact_form(Model contact_form) {
		 contact_form.addAttribute("title", "contact_form ");
	  	System.out.println("Inside contact_form");
		 contact_form.addAttribute("contact",new contact());

	  	return "/users/contact_form";
	  	
	  }            
	              
	 @PostMapping("/addContact")
	 public String addContact(@ModelAttribute("contact") contact contact ,Principal princi,HttpSession ses) {
		try {
			String nav = princi.getName();
			usar the_user  =	this.urep.getUsarByEmail(nav); 
			contact.setUsr(the_user);//saving user in contacts so we have user_id in contact table
			the_user.getContacts().add(contact);  //saving contact in user so we have contact_id in user table
			this.urep.save(the_user);
			ses.setAttribute("msg", new message("suscessfully Added contact","alert-success"));
			 System.out.println("yoooooooo"+contact.getContactname() +"& it belows to"+contact.getUsr()+" user");
		} catch (Exception e) {
			ses.setAttribute("msg", new message("failed Added contact","alert-danger"));
			 System.out.println("sorrrry!!!! failed to add"+contact.getContactname() );			
			 e.printStackTrace();
		}
 		  	return "/users/contact_form";
 
	 }
	 
	 @GetMapping("/viewContacts/{page}")
	 public String viewContacts(@PathVariable("page")Integer page,Model vc,Principal p) {
		String un= p.getName();
		usar usar =this.urep.getUsarByEmail(un);
		int id = usar.getId();
		 //above we retrived data of the logged in session user so we can show only n only his contacts
		/*just like 'List', 'Page' also store multiple variables but along with value it stores the page's position after certain values
	    'Pageable' stores values per page & current page	*/
		Pageable pp =PageRequest.of(page,2);// '2' is number of outputs which should be shown in each page
	Page <contact> contacts =	this.corp.findcontactsByUsr(id,pp);
	vc.addAttribute("contacts", contacts);
	 contacts.forEach(c->System.out.println(c.getContactname()+c.getEmail()));
		 vc.addAttribute("title", "your COntactz");
		 vc.addAttribute("currentPage",page);
		 vc.addAttribute("totalPages",contacts.getTotalPages());
		 
		 
		 System.out.println("Displaying contacts"+contacts);
		return "/users/contacts";
		 
	 }
	                        
		@GetMapping("/{cid}/contactdetails") 
	     public String contactdetails(@PathVariable("cid")Integer cid,Model m,Principal p) {
			m.addAttribute("title","userdetails");

			String user = p.getName();//ths will giv  user name of current session adim / the person loged in
		usar	current_user =this.urep.getUsarByEmail(user);
		//above we have recived data of the current user only so only contacts from logged-in user is seen 

			Optional<contact> co =this.corp.findById(cid);
			contact c =co.get();
			if(current_user.getId()==c.getUsr().getId()) {
		m.addAttribute("details", c);
		}
 		else {
 		m.addAttribute("msg",new message("ascess to this information is restricted","alert-primary"));
 
 		}
 	return "/users/contactdetails";

 		
		}
		
		@GetMapping("/delete/{cid}")
		public String delete(@PathVariable("cid") int cid,Model d,Principal pl,HttpSession ses) {
		try {
			String uid=	pl.getName();
			usar usr =this.urep.getUsarByEmail(uid);
Optional <contact> cc =this.corp.findById(cid);
contact c =cc.get();
if( usr.getId()==c.getUsr().getId()) {
			 System.out.println(" Dear "+pl.getName()+ "we have deleted "+c.getContactname()+"from your  contact");
				//since there is OneToMany mapping and cascading we must make changes in both

			 c.setUsr(null); //since there is one to one mapping and casaciding the entities are linked to eachother and must be disconnected by making null be4 deleting
				 usr.setContacts(null);
				 c.setUsr(null);
				 this.urep.save(usr);
				 this.corp.save(c);
 				 this.corp.deleteById(c.getCid());
			  //           this.corp.save(c);
//			 this.corp.delete(c);
//			 this.corp.deleteById(c.getCid());
			// this.corp.deleteById(c.getCid());
			// this.corp.deleteById(c.getCid());
//			 corp.deleteAll();
 			 
 			ses.setAttribute("msg",new message("suscessfully deleted contact","alert-success"));
}
		} catch (Exception e) {
			 System.out.println(" Dear "+pl.getName()+ "we have failed to delete the contact with id "+cid);
			 ses.setAttribute("msg",new message(" Dear "+pl.getName()+ "we have failed to delete the contact with id "+cid+"because"+e,"alert-danger"));
			e.printStackTrace();
		}
			return "redirect:/users/viewContacts/0";
			
		}
	
		@PostMapping("/update/{cid}")
		public String Update(@PathVariable("cid") int cid,Model m) {
		 
			 Optional<contact> ContactfromDatabase = this.corp.findById(cid);
				contact c =ContactfromDatabase.get();
			
			m.addAttribute("contact", c);
           m.addAttribute("title", "Updating contact");

			return "/users/update";
		}
 	
 	@PostMapping("/updateing")
 		public String updating_process(@ModelAttribute("contact") contact con,Principal pl,Model m,HttpSession ses) {
 		try {
 
 			String cu=	pl.getName();
 		usar ussr =this.urep.getUsarByEmail(cu);
 		con.setUsr(ussr);
   		contact upd=	 this.corp.save(con);

			
			m.addAttribute("contact", upd);
      m.addAttribute("title", "Updating contact");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("failed to update because of"+e);
		}
		return "/users/update";
 			
 		}
 	
 	//handler for profile 
 		@GetMapping("/profile")
 		public String profile(Model m) {
 			m.addAttribute("title", "Profle page");
 		return "/users/profile";
 		}
 		
 		
 		@GetMapping("/userhome")
 		public String userhome(Model m) {
 			m.addAttribute("title", "User Home page");
 		return "/users/userhome";
 		}
 		
 		@GetMapping("/settings")
 		public String settings(Model m) {
 			m.addAttribute("title", "Settings page");
 
 		return "/users/settings";
 		}
 		
 		
 		@RequestMapping("/psdotp")
 		public String otp_to_change_password(Model m,HttpSession ses) {
 			Random rm=new Random(1000);
	 			int OTPnumber = rm.nextInt(9999);
ses.setAttribute("otp",OTPnumber);
	 			String subject ="OTP for 'forgot password' ";
	 			String to="fernandeselton2897@gmail.com";
			String msg ="you OTP is "+OTPnumber+" pleas dont share it with anyone";
	 			boolean OTPsender=this.emsvc.sendemail(subject, to, msg);
	 			if(OTPsender) {
	 				ses.setAttribute("msg",new message("OTP send suscessfully,check your email","alert-success"));
 				 m.addAttribute("title", "OTP send suscessfully!!!!!!!!!!! ");
 	 	 		return "/users/passwordChanger";

 	  	 }else{
	 				ses.setAttribute("msg",new message("failed to send OTP ,sorry","alert-danger"));
	 				 m.addAttribute("title", "failed to send OTP ,sorry");

 	  	 } 
	 	 		return "/users/settings";

	}
 		
 		
 		@PostMapping("/changepsd")
 		public String changepsd(Model m,@RequestParam("oldpsd")String oldpsd,@RequestParam("newpsd")String newpsd,Principal pric,HttpSession ses,@RequestParam("otp")int otp ) {
 			 
 			Integer the_otp =(int) ses.getAttribute("otp");
 			if(otp==the_otp) {
  				 
 
 			usar user =this.urep.getUsarByEmail(pric.getName());
	 			String oldpsdfromdb = user.getPassword();
	 			
	 			if(this.enc.matches(oldpsd, oldpsdfromdb)) {
	 				user.setPassword(this.enc.encode(newpsd));
	  				System.out.println("password changed succesfully");
	  	 			ses.setAttribute("msg",new message("suscessfully Changed the password","alert-success"));
	 				this.urep.save(user);
		 	 		return "/users/settings";


	 			}else {
	 				System.out.print("your old password didnt match");
	 				m.addAttribute("subtitle", "password change failed");
	  	 			ses.setAttribute("msg",new message("Are you a thief?!!","alert-danger"));

	 			}}
	 	 		return "/users/passwordChanger";

 			 

 			
 			
 		}
 		
 		
 		
 		/*
 		 	
 		@PostMapping("/changepsd")
 		public String changepsd(Model m,@RequestParam("oldpsd")String oldpsd,@RequestParam("newpsd")String newpsd,Principal pric,HttpSession ses) {
 			 
 			
 			System.out.print("old password is "+oldpsd+" & new password is "+newpsd);
 			m.addAttribute("title", "setting page");
 			
 	
 			usar user =this.urep.getUsarByEmail(pric.getName());
 			String oldpsdfromdb = user.getPassword();
 			
 			if(this.enc.matches(oldpsd, oldpsdfromdb)) {
 				user.setPassword(this.enc.encode(newpsd));
  				System.out.println("password changed succesfully");
  	 			ses.setAttribute("msg",new message("suscessfully Changed the password","alert-success"));
 				this.urep.save(user);

 			}else {
 				System.out.print("your old password didnt match");
 				m.addAttribute("subtitle", "password change failed");
  	 			ses.setAttribute("msg",new message("Are you a thief?!!","alert-danger"));

 			}
 	 		return "/users/settings";
 			
 		}*/
	/*-x-x-x-x-x-x-x -x-x-x-x-x-x-x-x-x-xx-x-x-x-x-x-x-below is date and time function-x-x-x-x-x-x-x-x-x-x-x-x-x--*/
    public static void vel() {
    	  LocalDateTime myObj = LocalDateTime.now();
  	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
          String formattedDate = myObj.format(myFormatObj);
   	    System.out.println("user inside index at-->"+formattedDate);
     	
    }
/*-x-x-x-xx-x-x-x-x-x-x---x-x-x-xx-x-x-x-x-x-x---x-x-x-xx-x-x-x-x-x-x---x-x-x-xx-x-x-x-x-x-x---x-x-x-xx-x-x-x-x-x-x--*/
}
