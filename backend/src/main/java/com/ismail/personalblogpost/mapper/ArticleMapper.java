package com.ismail.personalblogpost.mapper;

import com.ismail.personalblogpost.Article.Article;
import com.ismail.personalblogpost.DtoWrapper;
import com.ismail.personalblogpost.projectors.ArticleProjector;
import jakarta.persistence.Tuple;

import org.mapstruct.*;

import java.sql.Date;
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
     @Mapping(target = "id",expression = "java(tuple.get(\"id\",Long.class))")
     @Mapping(target = "title",expression = "java(tuple.get(\"title\",String.class))")
     @Mapping(target = "slug",expression = "java(tuple.get(\"slug\",String.class))")
     @Mapping(target = "description",expression = "java(tuple.get(\"description\",String.class))")
     @Mapping(target = "url",expression = "java(tuple.get(\"url\",String.class))")
     @Mapping(target = "readingTime",expression = "java(tuple.get(\"reading_time\",Integer.class))")
     @Mapping(target = "createdAt",expression = "java(LocalDateTime.ofInstant(tuple.get(\"created_at\",java.time.Instant.class),java.time.Clock.systemUTC().getZone()))")
//     Loca
     @Mapping(target = "updatedAt",expression = "java(tuple.get(\"updated_at\", java.sql.Date.class).toLocalDate())")
     DtoWrapper.ArticlePreview convertTupleToArticlePreview(Tuple tuple) ;

}
