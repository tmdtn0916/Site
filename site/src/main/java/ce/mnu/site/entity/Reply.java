package ce.mnu.site.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "articleId")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "userNo")
    private SiteUser user;
}
