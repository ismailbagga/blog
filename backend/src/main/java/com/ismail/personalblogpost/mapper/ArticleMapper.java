package com.ismail.personalblogpost.mapper;

import com.ismail.personalblogpost.Article.Article;
import com.ismail.personalblogpost.DtoWrapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

     DtoWrapper.ArticlePreview convertToArticlePreview(Article article) ;
     List<DtoWrapper.ArticlePreview> convertToArticlePreviewList(List<Article> article) ;

     Article convertUploadDtoToArticle(DtoWrapper.ArticleUploadDto articleUploadDto) ;

     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     void updateArticle(DtoWrapper.ArticleContent articleContent , @MappingTarget Article article) ;
}
