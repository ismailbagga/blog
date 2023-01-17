package com.ismail.personalblogpost.Article;

import com.ismail.personalblogpost.Tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(indexes = {@Index(name = "article_slug_index", columnList = "slug")})
@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "article_id_sequence")
    @SequenceGenerator(name = "article_id_sequence", sequenceName = "article_id_seq", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String title;
    @Column(unique = true)
    private String slug;
    private String description;
    @Min(1)
    private int readingTime;

    @Column(length = 500)
    private String url;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDate updatedAt;
    @OneToOne(orphanRemoval = true
            ,mappedBy = "article",fetch = FetchType.LAZY)
    MarkdownContent markdownContent ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", foreignKey = @ForeignKey(name = "next_article_id"))
    private Article nextArticle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", foreignKey = @ForeignKey(name = "prev_article_id"))
    private Article prevArticle;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "tag_of_article",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)
    )
    private Set<Tag> relatedTags;
    public String getContent() {
        return  markdownContent.getContent() ;
    }
    public void setContent(String markdownContent) {
        this.markdownContent = new MarkdownContent(this,markdownContent) ;
    }
}
