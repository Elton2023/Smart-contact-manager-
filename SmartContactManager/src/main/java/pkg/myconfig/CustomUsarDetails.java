package pkg.myconfig;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pkg.entities.usar;

public class CustomUsarDetails implements UserDetails{
	
	
        @Autowired
	private usar usar;
	
	public CustomUsarDetails(usar usar)    {
	super();
	this.usar = usar;
}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		/*the 'SimpleGrantedAuthority' holdes  permissions like roles etc..thats y if the role is admin it must be saved as 'USER_ADMIN'
		 * so this variable can recognis it*/		
		SimpleGrantedAuthority SimpleGrantedAuthority=new SimpleGrantedAuthority(usar.getRole());
		return List.of(SimpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
 		return usar.getPassword();
	}

	@Override
	public String getUsername() {
 		return usar.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
 		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
 		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
 		return true;
	}

	@Override
	public boolean isEnabled() {
 		return true;
	}

}
