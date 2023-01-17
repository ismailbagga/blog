package com.ismail.personalblogpost.mapper;

import com.ismail.personalblogpost.Article.Article;
import com.ismail.personalblogpost.DtoWrapper;
import com.ismail.personalblogpost.projectors.ArticleProjector;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

     DtoWrapper.ArticlePreview convertToArticlePreview(Article article) ;
     DtoWrapper.ArticlePreview convertProjectorToArticlePreview(ArticleProjector article) ;
     List<DtoWrapper.ArticlePreview> convertProjectorsToArticlePreviews(List<ArticleProjector> article) ;
     DtoWrapper.ArticleDetails convertToArticleDetails(Article article) ;
     DtoWrapper.BasicArticle convertToBasicArticle(Article article) ;
     List<DtoWrapper.ArticlePreview> convertToArticlePreviewList(List<Article> article) ;

     Article convertUploadDtoToArticle(DtoWrapper.ArticleUploadDto articleUploadDto) ;

     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     void updateArticle(DtoWrapper.ArticleMetaData articleMetaData, @MappingTarget Article article) ;
}
