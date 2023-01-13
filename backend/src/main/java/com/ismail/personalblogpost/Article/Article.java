package com.ismail.personalblogpost.Article;

import com.ismail.personalblogpost.Tag.Tag;
import com.ismail.personalblogpost.Utils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
@Table(indexes = {@Index(name ="article_slug_index" ,columnList = "slug",unique = true)})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String title  ;
    private String slug  ;
    private String description ;
    @Min(1)
    private int readingTime ;
    @Column(columnDefinition = "TEXT")
    private String content ;
    @Column(length = 500)
    private String url ;

    @CreationTimestamp
    private LocalDate createdAt  ;
    @UpdateTimestamp
    private LocalDate updatedAt  ;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article nextArticle  ;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article prevArticle  ;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private Set<Tag> relatedTags ;

    @PrePersist()
    public void prePersist() {
        if ( slug != null && !slug.strip().equals("")) return ;
        slug = Utils.slugify(title) ;

    }

}
