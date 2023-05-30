package ce.mnu.site.service;

import ce.mnu.site.entity.Article;
import ce.mnu.site.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    public void write(Article article) {
        articleRepository.save(article);
    }

    public Page<Article> articleList(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
