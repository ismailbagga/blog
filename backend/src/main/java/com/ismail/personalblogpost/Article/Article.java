package com.ismail.personalblogpost.Article;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Entity
@Table(indexes = {@Index(name ="article_slug_index" ,columnList = "slug",unique = true)})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
    @Id
    private Long id ;
    private String title  ;
    private String slug  ;
    private String description ;
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

    @PrePersist()
    public void prePersist() {
        if ( slug != null && !slug.strip().equals("")) return ;
        final Pattern WHITESPACE = Pattern.compile("\\s+") ;
        final Pattern NOT_NORMAL_CHAR = Pattern.compile("[^\\w-]+") ;
        var noWhiteSpace = WHITESPACE.matcher(title.toLowerCase().strip()).replaceAll("-");
        slug = NOT_NORMAL_CHAR.matcher(noWhiteSpace).replaceAll("") ;

    }

}
