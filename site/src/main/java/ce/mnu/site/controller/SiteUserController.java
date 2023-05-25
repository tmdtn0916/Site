package ce.mnu.site.controller;

import ce.mnu.site.ArticleHeader;
import ce.mnu.site.dto.FileDto;
import ce.mnu.site.entity.Article;
import ce.mnu.site.entity.SiteUser;
import ce.mnu.site.repository.ArticleRepository;
import ce.mnu.site.repository.SiteUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping(path = "/siteuser")
public class SiteUserController {

	@Autowired
	private SiteUserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	@GetMapping(value = { "", "/" })
	public String start(Model model) {
		return "start";
	}

	@GetMapping(path = "/signup")
	public String signup(Model model) {
		model.addAttribute("siteuser", new SiteUser());
		return "signup_input";
	}

	@PostMapping(path = "/signup")
	public String signup(@ModelAttribute SiteUser user, Model model) {
		String encodedPassword = encoder.encode(user.getPasswd());
		user.setPasswd(encodedPassword);
		userRepository.save(user);
		model.addAttribute("name", user.getName());
		return "signup_done";
	}

	@PostMapping(path = "/find")
	public String findUser(@RequestParam(name = "email") String email, HttpSession session, Model model,
			RedirectAttributes rd) {
		SiteUser user = userRepository.findByEmail(email);
		if (user != null) {
			model.addAttribute("user", user);
			return "find_done";
		}
		rd.addFlashAttribute("reason", "wrong email");
		return "redirect:/error";
	}

	@GetMapping(path = "/find")
	public String find() {
		return "find_user";
	}

//	@PostMapping(path = "/login")
//	public String loginUser(@RequestParam(name = "email") String email, @RequestParam(name = "passwd") String passwd,
//			HttpSession session, RedirectAttributes rd) {
//		SiteUser user = userRepository.findByEmail(email);
//		if (user != null) {
//			if (passwd.equals(user.getPasswd())) {
//				session.setAttribute("email", email);
//				session.setAttribute("name", user.getName());
//				session.setAttribute("address", user.getAddress());
//				session.setAttribute("tel", user.getTel());
//				return "login_done";
//			}
//		}
//		rd.addFlashAttribute("reason", "wrong password");
//		return "redirect:/error";
//	}
	

	
	@GetMapping(path = "/login")
	public String loginForm() {
		return "login";
	}

	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "start";
	}

	@GetMapping(path = "/updateuser")
	public String updateuser(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		model.addAttribute("siteuser", new SiteUser());

		return "update_user";
	}

	@PostMapping("/updateuser")
	public String updateuser(@ModelAttribute SiteUser user, HttpServletRequest request,
			@RequestParam(name = "newPassword") String newPassword,
			@RequestParam(name = "checkPassword") String checkPassword, RedirectAttributes rd) {
		HttpSession session = request.getSession();
		String email = session.getAttribute("email").toString();
		SiteUser updateUser = userRepository.findByEmail(email);
		if (!(updateUser.getPasswd().equals(user.getPasswd())) || !(checkPassword.equals(checkPassword))) {
			rd.addFlashAttribute("reason", "wrong password");
			return "redirect:/error";
		}
		updateUser.setPasswd(newPassword);
		updateUser.setAddress(user.getAddress());
		updateUser.setTel(user.getTel());

		userRepository.save(updateUser);
		return "update_done";
	}

	// 게시판 관련
	@Autowired
	private ArticleRepository articleRepository;

	@GetMapping(path = "/bbs/write")
	public String bbsForm(Model model, Authentication authentication, RedirectAttributes rd) {
		String email = authentication.getName();
		if(email == null) {
			rd.addFlashAttribute("reason","login required");
			return "redirect:/error";
		}
		model.addAttribute("article", new Article());
		return "new_article";
	}

	@PostMapping(path = "/bbs/add")
	public String addArticle(@ModelAttribute Article article, Model model, @RequestParam MultipartFile file) {
		articleRepository.save(article);
		try {
			if(! file.isEmpty()) {
				String newName = file.getOriginalFilename();
				newName = newName.replace(' ','_');
				FileDto dto = new FileDto(newName, file.getContentType());
				File upfile = new File(dto.getFileName());
				file.transferTo(upfile);
				model.addAttribute("file",dto);
			}
		} catch (Exception e) {
			System.out.println("error");
		}
		model.addAttribute("article", article);
		return "saved";
	}

	@GetMapping(path = "/bbs")
	public String getAllArticles(@RequestParam(name = "pno", defaultValue = "0") String pno, Model model, Authentication authentication, RedirectAttributes rd) {
		String email = authentication.getName();
		if(email == null) {
			rd.addFlashAttribute("reason","login required");
			return "redirect:/error";
		}
		Integer pageNo = 0;
		if(pno != null) {
			pageNo = Integer.valueOf(pno);
		}
		Integer pageSize = 2;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, "num");
		Page<ArticleHeader> data = articleRepository.findArticleHeaders(paging);
		model.addAttribute("articles",data);
		return "articles";
	}

	@GetMapping(path = "/read")
	public String readArticle(@RequestParam(name = "num") String num, HttpSession session, Model model) {
		Long no = Long.valueOf(num);
		Article article = articleRepository.getReferenceById(no);
		model.addAttribute("article", article);
		return "article";
	}

	@GetMapping(path = "/home")
	public String home() {
		return "home";
	}

	@PostMapping(path = "/upload")
	public String upload(@RequestParam MultipartFile file, Model model) throws IllegalStateException, IOException {
		if(! file.isEmpty()) {
			String newName = file.getOriginalFilename();
			newName = newName.replace(' ','_');
			FileDto dto = new FileDto(newName, file.getContentType());
			File upfile = new File(dto.getFileName());
			file.transferTo(upfile);
			model.addAttribute("file",dto);
		}
		return "result";
	}

	@GetMapping(path = "/upload")
	public String visitUpload() {
		return "uploadForm";
	}

	@Value("${spring.servlet.multipart.location}")
	String base;

	@GetMapping(path = "/download")
	public ResponseEntity<Resource> download(@ModelAttribute FileDto dto) throws IOException {
		Path path = Paths.get(base + "/" + dto.getFileName());
		String contentType = Files.probeContentType(path);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("attachment")
				 .filename(dto.getFileName(), StandardCharsets.UTF_8).build( ));
		headers.add(HttpHeaders.CONTENT_TYPE,contentType);
		Resource rsc = new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<>(rsc, headers, HttpStatus.OK);
	}
}
