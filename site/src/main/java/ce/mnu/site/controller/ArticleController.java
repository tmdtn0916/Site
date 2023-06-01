package ce.mnu.site.controller;

import ce.mnu.site.entity.Article;
import ce.mnu.site.entity.SiteUser;
import ce.mnu.site.repository.ArticleRepository;
import ce.mnu.site.repository.SiteUserRepository;
import ce.mnu.site.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bbs")
public class ArticleController {
    @Autowired
    private ArticleRepository repository;

    @Autowired
    private SiteUserRepository userRepository;
    @Autowired
    private ArticleService service;

    @GetMapping("/write")
    public String article(Model model) {
        model.addAttribute("article",new Article());
        return "article";
    }

    @PostMapping("/add")
    public ResponseEntity<String> save(@RequestBody Article article) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        SiteUser user = userRepository.findByEmail(email);
        article.setUser(user);
        service.write(article);
        return ResponseEntity.ok("ok");
    }

    @GetMapping({"","/"})
    public String articles(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        model.addAttribute("boards", service.articleList(pageable));
        return "articles";
    }

    @GetMapping("/read/{id}")
    public String read(Model model, @PathVariable Long id) {
        model.addAttribute("board", service.findById(id));
        return "board";
    }
}
