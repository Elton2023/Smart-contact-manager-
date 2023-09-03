package pkg.myconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pkg.Repozitory.userRepo;
import pkg.entities.usar;

public class UsarDetailsImpl implements UserDetailsService{

	@Autowired
	private userRepo ussr;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 
	usar usar=	this.ussr.getUsarByEmail(username);
		
	if(usar==null) {
		System.out.println("failed to find clint");
	}
	CustomUsarDetails csd =new CustomUsarDetails(usar);
	
		return csd;
	}

}
