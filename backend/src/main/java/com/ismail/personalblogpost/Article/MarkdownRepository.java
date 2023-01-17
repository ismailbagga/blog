package com.ismail.personalblogpost.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarkdownRepository  extends JpaRepository<MarkdownContent, Long> {

    @Query("SELECT mk FROM MarkdownContent  mk WHERE mk.article.id = :id")
    Optional<MarkdownContent> findByIdJPQL(@Param("id") Long articleId);
}
