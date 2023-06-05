package ce.mnu.site.controller;

import ce.mnu.site.dto.ReplyDto;
import ce.mnu.site.entity.Article;
import ce.mnu.site.entity.Reply;
import ce.mnu.site.entity.SiteUser;
import ce.mnu.site.repository.SiteUserRepository;
import ce.mnu.site.service.ArticleService;
import ce.mnu.site.service.ReplyService;
import org.hibernate.SessionFactory;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/bbs")
public class ArticleController {
    @Autowired
    private SiteUserRepository userRepository;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private ArticleService service;

    @Autowired
    SessionFactory factory;

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
        article.setCreateTime(LocalDateTime.now());
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
        Article article = service.findById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        article.setFormattedCreateTime(article.getCreateTime().format(formatter));
        model.addAttribute("board", article);
        model.addAttribute("comments",article.getComments());
        model.addAttribute("comment",new Reply());
        article.setViewCount(article.getViewCount() + 1);
        service.save(article);
        return "board";
    }

    @PostMapping("/comment/create")
    @ResponseBody
    public ResponseEntity<String> createComment(@RequestBody ReplyDto replyDto) {
        Long articleId = replyDto.getArticleId();
        String content = replyDto.getContent();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        SiteUser user = userRepository.findByEmail(email);
        replyService.saveReply(articleId,content,user);
        return ResponseEntity.ok("success");
    }

}
