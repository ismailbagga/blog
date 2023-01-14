package com.ismail.personalblogpost.mapper;

import com.ismail.personalblogpost.Article.Article;
import com.ismail.personalblogpost.DtoWrapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

     DtoWrapper.ArticlePreview convertToArticlePreview(Article article) ;

     Article convertUploadDtoToArticle(DtoWrapper.ArticleUploadDto articleUploadDto) ;

     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     void updateArticle(DtoWrapper.ArticleContent articleContent , @MappingTarget Article article) ;
}
