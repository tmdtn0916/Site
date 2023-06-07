package ce.mnu.site.controller;

import ce.mnu.site.dto.UserDto;
import ce.mnu.site.entity.SiteUser;
import ce.mnu.site.repository.SiteUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private SiteUserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

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
        model.addAttribute("siteuser", new SiteUser());
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
    public String editUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        SiteUser user = userRepository.findByEmail(email);
        if (user == null) {
            user = new SiteUser();
        }
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping(path = "/update")
    @ResponseBody
    public ResponseEntity<String> update(@RequestBody UserDto userdto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        SiteUser user = userRepository.findByEmail(email);
        if (!encoder.matches(userdto.getPassword(), user.getPasswd())) {
            return ResponseEntity.ok().body("Password error");
        } else if (!userdto.getNewPassword().equals(userdto.getCheckPassword())) {
            return ResponseEntity.ok().body("newPassword error");
        }
        user.setAddress(userdto.getAddress());
        user.setTel(userdto.getTel());
        user.setPasswd(encoder.encode(userdto.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("success");
    }
}
