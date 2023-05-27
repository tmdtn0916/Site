package ce.mnu.site.controller;

import ce.mnu.site.entity.SiteUser;
import ce.mnu.site.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        return "signup_done";
    }
}
