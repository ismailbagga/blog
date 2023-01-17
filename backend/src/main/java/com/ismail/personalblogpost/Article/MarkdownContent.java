package com.ismail.personalblogpost.Article;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "markdown_content")
@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class MarkdownContent {
    @Id
    private long articleId ;
    @MapsId()
    @OneToOne(cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    Article article ;
    @Column(columnDefinition = "TEXT")
    String content ;

    public MarkdownContent(Article article, String content) {
        this.article = article;
        this.content = content;
    }
}
