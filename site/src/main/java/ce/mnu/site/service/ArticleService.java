package ce.mnu.site.service;

import ce.mnu.site.entity.Article;
import ce.mnu.site.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
        Page<Article> articles = articleRepository.findAll(pageable);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        articles.forEach(article -> {
            String formattedCreateTime = article.getCreateTime().format(formatter);
            article.setFormattedCreateTime(formattedCreateTime);
        });

        return articles;
    }

    public Article findById(Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.orElse(null);
    }

    public void save(Article article) {
        articleRepository.save(article);
    }
}
