package pkg.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pkg.additional.message;
import pkg.servic.services;

@Controller
@RequestMapping("/mail")
public class mailcontroller {
	
	@Autowired
	private services svc;
	
	@GetMapping("/verifyemail")
	public String forgotpassword(Model m) {
		m.addAttribute("title", "so u forgot your password uha?!");
 
		return "/users/verifyemail";
		
	}

	
	
	@PostMapping("/sendotp")
	public String sendOTP(Model m,HttpSession ses,@RequestParam("email") String email) {
		 m.addAttribute("title", "OTP send suscessfully to verfiy user!!!!!!!!!!! ");

		Random rm=new Random(1000);
		int otp =rm.nextInt(9999);
		System.out.println("OTP isssssssssssssss"+otp);
		//sending the OTP
		String Subject ="contact manager-verify email  ";
		String	to="897@gmail.com";
		String	msg ="your otp is "+otp+" dont  share it with anyone.";
		boolean flag=this.svc.sendemail(Subject,to,msg);
		if(flag) {
			ses.setAttribute("msg",new message("OTP send suscessfully!!!!!!!!!!!","alert-success"));
			 m.addAttribute("title", "OTP send suscessfully!!!!!!!!!!! ");
}else {
			ses.setAttribute("msg",new message("failed to send OTP ,sorry","alert-danger"));
			 m.addAttribute("title", "failed to send OTP ,sorry");

		}
		return "/users/verifyemail";
		
	}
	
}
