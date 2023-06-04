package ce.mnu.site.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @JoinColumn(name = "userNo")
    private SiteUser user;

    @OneToMany(mappedBy = "article")
    private List<Reply> comments;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int viewCount;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    @Transient
    private String formattedCreateTime;

}
