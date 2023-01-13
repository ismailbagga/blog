package com.ismail.personalblogpost.mapper;

import com.ismail.personalblogpost.Article.Article;
import com.ismail.personalblogpost.DtoWrapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

     DtoWrapper.ArticlePreview convertToArticlePreview(Article article) ;
}
