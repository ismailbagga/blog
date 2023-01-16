package com.ismail.personalblogpost.Article;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {


    @Query("SELECT article FROM Article  article " +
            "LEFT JOIN FETCH article.nextArticle " +
            "LEFT JOIN FETCH article.prevArticle")
    List<Article> findAllWithEagerFetch(Sort sort);

    Optional<Article> findByTitle(String title);

    Optional<Article> findBySlug(String slug);

    List<Article> findByTitleOrSlug(String title, String slug);
}
