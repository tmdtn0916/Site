package ce.mnu.site.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
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

    @GetMapping(path = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "home";
    }
}
