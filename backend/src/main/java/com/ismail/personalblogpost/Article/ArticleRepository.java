package com.ismail.personalblogpost.Article;

import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByTitle(String title);

    //    upper(article.title) LIKE lower(CONCAT('%',:title,'%'))
    @Query(value = "SELECT art.* , t.id  as tag_id , t.slug as tag_slug , t.title as tag_title " +
            "FROM article art  " +
            "LEFT JOIN tag_of_article ta ON  ta.article_id = art.id " +
            "LEFT JOIN tag as  t ON t.id = ta.tag_id  " +
            "WHERE art.id  IN " +
            "(SELECT a2.id FROM article a2 WHERE  upper(a2.title) LIKE CONCAT('%',upper(:title) ,'%') " +
            "OFFSET  :offset " +
            "LIMIT :pageSize )",
            nativeQuery = true
    )
    Stream<Tuple> findByTitleIsContainingIgnoreCase(@Param("title") String title, int offset, int pageSize);

    @Query(value = "SELECT count(art.id) " +
            "FROM article art  " +
            "WHERE  upper(art.title) LIKE CONCAT('%',upper(:title) ,'%') ",
            nativeQuery = true
    )
    Integer findByTotalTitleIsContainingIgnoreCase(String title);


    Optional<Article> findBySlug(String slug);

    List<Article> findByTitleOrSlug(String title, String slug);

    @Query("SELECT article FROM Article article " +
            "INNER JOIN article.markdownContent  " +
            "LEFT JOIN FETCH article.relatedTags " +
            "LEFT JOIN FETCH article.nextArticle " +
            "LEFT JOIN FETCH article.prevArticle " +
            "where article.slug = :slug")
    Optional<Article> fetchArticleBySlugEagerly(@Param("slug") String slug);

    @Query("SELECT article FROM Article  article " +
            "LEFT JOIN FETCH article.relatedTags " +
            "LEFT JOIN FETCH article.nextArticle " +
            "LEFT JOIN FETCH article.prevArticle")
    List<Article> findAllWithEagerFetch(Sort sort);


    @Query(value = "SELECT article  FROM  Article article " +
            "  JOIN  FETCH  article.relatedTags rlt " +
            "WHERE rlt.id IN (:tagsId)"
    )
    List<Article> findArticleByRelatedTagsIn(@Param("tagsId") Long[] tagsId);


}
