package com.ismail.personalblogpost.Article;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByTitle(String title);


    List<Article> findByTitleIsContainingIgnoreCase(String title) ;
    Optional<Article> findBySlug(String slug);

    List<Article> findByTitleOrSlug(String title, String slug);

    @Query("SELECT article FROM Article article " +
            "INNER JOIN article.markdownContent  "+
            "LEFT JOIN FETCH article.relatedTags "+
            "LEFT JOIN FETCH article.nextArticle " +
            "LEFT JOIN FETCH article.prevArticle " +
            "where article.slug = :slug")
    Optional<Article> fetchArticleBySlugEagerly(@Param("slug") String slug);

    @Query("SELECT article FROM Article  article " +
            "LEFT JOIN FETCH article.relatedTags "+
            "LEFT JOIN FETCH article.nextArticle " +
            "LEFT JOIN FETCH article.prevArticle")
    List<Article> findAllWithEagerFetch(Sort sort);


    @Query(value = "SELECT article  FROM  Article article "+
            "  JOIN  FETCH  article.relatedTags rlt " +
            "WHERE rlt.id IN (:tagsId)"
            )
    List<Article> findArticleByRelatedTagsIn(@Param("tagsId") Long[] tagsId);



}
