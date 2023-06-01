package ce.mnu.site.repository;

import ce.mnu.site.ArticleHeader;
import ce.mnu.site.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
	@Query(value = "Select num, title, author, time from article", nativeQuery = true)
	Page<ArticleHeader> findArticleHeaders(Pageable pageable);

	Optional<Article> findById(Long id);
}
