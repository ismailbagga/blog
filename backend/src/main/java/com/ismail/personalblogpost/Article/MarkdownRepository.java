package com.ismail.personalblogpost.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkdownRepository  extends JpaRepository<MarkdownContent, Long> {




}
