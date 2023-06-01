//package ce.mnu.site.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Comment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private Long id;
//
//    @Column
//    private String content;
//
//    @ManyToOne
//    @JoinColumn(name = "article_id")
//    private Article article;
//    @ManyToOne
//    @JoinColumn
//    private SiteUser user;
//}
