package ce.mnu.site.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String title;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn
    private SiteUser user;

//    @OneToMany(mappedBy = "article")
//    private List<Comment> comments;

}
