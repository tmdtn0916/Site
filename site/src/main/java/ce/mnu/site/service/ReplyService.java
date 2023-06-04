package ce.mnu.site.service;

import ce.mnu.site.entity.Reply;
import ce.mnu.site.entity.SiteUser;
import ce.mnu.site.repository.ReplyRepository;
import ce.mnu.site.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    private final ReplyRepository repository;

    @Autowired
    SiteUserRepository userRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ArticleService service;

    @Autowired
    public ReplyService(ReplyRepository repository) {
        this.repository = repository;
    }

    public Reply saveReply(Long articleId, String content, SiteUser user) {
        Reply reply = new Reply();
        reply.setArticle(service.findById(articleId));
        reply.setContent(content);
        reply.setUser(user);
        return replyRepository.save(reply);
    }
}
