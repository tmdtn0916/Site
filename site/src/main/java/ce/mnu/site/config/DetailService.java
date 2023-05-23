package ce.mnu.site.config;

import ce.mnu.site.entity.SiteUser;
import ce.mnu.site.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DetailService implements UserDetailsService{
	private final UserService service;
	
	public DetailService(UserService service) {
		this.service = service;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SiteUser user = service.findByEmail(username);
		return User.builder()
				.username(user.getEmail())
				.password(user.getPasswd())
				.authorities("DEFAULT")
				.build();
	}
}
