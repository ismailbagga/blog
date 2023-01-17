package com.ismail.personalblogpost.projectors;

import com.ismail.personalblogpost.Article.Article;
import com.ismail.personalblogpost.Tag.Tag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public interface ArticleProjector {
    public Long getId() ;
    public String getTitle() ;
    public String getSlug() ;
    public String getDescription() ;
    public int getReadingTime() ;
    public String getUrl() ;
    public LocalDateTime getCreatedAt() ;
    public LocalDate getUpdatedAt() ;
    public Article getNextArticle() ;
    public Article getPrevArticle() ;
    public Set<Tag> getRelatedTags() ;
}
