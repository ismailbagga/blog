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
@Table(indexes = @Index(name = "tag_slug_index",columnList = "slug"))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY , generator = "tag_id_sequence")
    @SequenceGenerator(name = "tag_id_sequence",sequenceName = "tag_id_seq",allocationSize = 1)
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



}
