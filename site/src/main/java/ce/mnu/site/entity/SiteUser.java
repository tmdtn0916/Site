package ce.mnu.site.entity;

import jakarta.persistence.*;

@Entity
@Table
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long userNo;

	@Column(length = 50, unique = true, nullable = false)
	private String email;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false, length = 130, name = "password")
	private String passwd;

	@Column(length = 50)
	private String address;

	@Column(length = 20)
	private String tel;
	
//	private SiteUser(Long userNo, String email, String pw) {
//		this.userNo = userNo;
//		this.email = email;
//		this.passwd = pw;
//	}
	
//	public static SiteUser createUser(String email, String pw, PasswordEncoder encoder) {
//		return new SiteUser(null, email, encoder.encode(pw));
//	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
