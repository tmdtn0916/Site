package ce.mnu.site.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDto {
    private Long articleId;
    private String content;
}
