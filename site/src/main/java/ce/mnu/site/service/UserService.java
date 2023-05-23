package ce.mnu.site.service;


import ce.mnu.site.entity.SiteUser;
import ce.mnu.site.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final SiteUserRepository repository;
	
	@Autowired
	public UserService(SiteUserRepository repository) {
		this.repository = repository;
	}
	
	public SiteUser findByEmail(String email) {
		return repository.findByEmail(email);
	}
	
}
