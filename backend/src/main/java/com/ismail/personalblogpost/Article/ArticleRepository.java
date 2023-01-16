package com.ismail.personalblogpost.Article;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository  extends JpaRepository<Article,Long> {



    @Query(value = "SELECT article , prev_article , next_article , FROM ",nativeQuery = true )
    Optional<Article> fetchArticleDetails(String slug) ;

    Optional<Article> findByTitle(String title) ;

    Optional<Article> findBySlug(String slug) ;
    List<Article> findByTitleOrSlug(String title , String slug) ;
}
