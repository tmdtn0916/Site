package ce.mnu.site.controller;

import ce.mnu.site.entity.Article;
import ce.mnu.site.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService service;

    @GetMapping("/bbs/write")
    public String article(Model model) {
        model.addAttribute("article",new Article());
        return "article";
    }

    @PostMapping("/bbs/add")
    public ResponseEntity<String> save(@RequestBody Article article) {
        service.write(article);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/bbs")
    public String articles(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        model.addAttribute("boards", service.articleList(pageable));
        return "articles";
    }
}
