package ce.mnu.site.repository;


import ce.mnu.site.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
	
//	Optional<SiteUser> findByEmailAndPasswd(String email, String passwd);
	SiteUser findByEmail(String email);
}
