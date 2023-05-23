package ce.mnu.site;

import java.time.LocalDateTime;

public interface ArticleHeader {
	Long getNum();
	String getTitle();
	String getAuthor();

	LocalDateTime getTime();
}
