package com.ismail.personalblogpost.Tag;

import com.ismail.personalblogpost.Article.Article;
import com.ismail.personalblogpost.Utils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Entity
@Table(indexes = @Index(name = "tag_slug_index",columnList = "slug",unique = true))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @NotNull
    @NotBlank
    @Column(unique = true)
    private String title  ;
    @Pattern(regexp = "(^[\\w-]+$)|(^.{0}$)")
    @Column(unique = true)
    @NotNull
    private String slug  ;
    @ManyToMany(mappedBy = "relatedTags")
    private Set<Article> relatedArticles ;

//    @PrePersist()
//    @PreUpdate()
//    public void prePersist() {
//        System.out.println("Pre Persisting Tag");
//        System.out.println("slug is --> "+slug);
//        if ( slug != null && !slug.strip().equals("")) return ;
//        slug = Utils.slugify(title) ;
//
//    }

}
