package com.ismail.personalblogpost.mapper;

import com.ismail.personalblogpost.Article.Article;
import com.ismail.personalblogpost.DtoWrapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

     DtoWrapper.ArticlePreview convertToArticlePreview(Article article) ;

     Article convertUploadDtoToArticle(DtoWrapper.ArticleUploadDto articleUploadDto) ;

//     void updateArticle(DtoWrapper)
}
