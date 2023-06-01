package ce.mnu.site.controller;

import ce.mnu.site.entity.SiteUser;
import ce.mnu.site.repository.SiteUserRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    @Autowired
    private SiteUserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/homepage")
    public String homepage() {
        return "homepage";
    }

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("siteuser",new SiteUser());
        return "signup";
    }

    @PostMapping(path = "/signup")
    public String signup(@ModelAttribute SiteUser user, Model model) {
        String encodedPassword = encoder.encode(user.getPasswd());
        user.setPasswd(encodedPassword);
        userRepository.save(user);
        model.addAttribute("name", user.getName());
        return "homepage";
    }
    
    @GetMapping("/update")
	 public String editUser(Model model, HttpSession session) {
    	Authentication auth=SecurityContextHolder.getContext().getAuthentication();
	     String email = auth.getName();
	     SiteUser user = userRepository.findByEmail(email);
	     if (user == null) {
	         user = new SiteUser();
	     }
	     session.setAttribute("siteuser", user);
	     model.addAttribute("user", user);
	     return "update";
	 }

	 @PostMapping(path="/update")
	  public String update(@RequestParam(name="passwd") String passwd,
			  @RequestParam(name="address") String address,
			  @RequestParam(name="tel") String tel,
	          HttpSession session, Model model,
	          RedirectAttributes rd) {
		 String email = (String) session.getAttribute("email");
	     SiteUser user = userRepository.findByEmail(email);
	     	if (user != null) {
	    	   user.setAddress(address);	
	           user.setTel(tel);
	           user.setPasswd(passwd);
	           userRepository.save(user);
	           model.addAttribute("name", user.getName( ));
	           return "update_done";
	      	}
	      	else {
	          rd.addFlashAttribute("reason", "user not found");
	          return "redirect:/error";
	      	}
	   }
}
