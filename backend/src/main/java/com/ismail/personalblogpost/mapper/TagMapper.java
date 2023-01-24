package com.ismail.personalblogpost.mapper;

import com.ismail.personalblogpost.dto.DtoWrapper;
import com.ismail.personalblogpost.dto.DtoWrapper.TagWithAllRelatedArticles;
import com.ismail.personalblogpost.Tag.Tag;
import com.ismail.personalblogpost.projectors.TagWithCountProjector;
import jakarta.persistence.Tuple;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = ArticleMapper.class)
public interface TagMapper {


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTag(DtoWrapper.UpdateTagDto basicTagDto ,@MappingTarget Tag tag) ;
    @Mapping(target = "id",expression = "java(tuple.get(\"tag_id\",Long.class))")
    @Mapping(target = "title",expression = "java(tuple.get(\"tag_title\",String.class))")
    @Mapping(target = "slug",expression = "java(tuple.get(\"tag_slug\",String.class))")
    DtoWrapper.BasicTagDto convertTupleToTagDto(Tuple tuple) ;
    TagWithAllRelatedArticles convertToTagWithArticles(Tag tag);

    Tag convertToTagDtoToTag(DtoWrapper.BasicTagDto tagDto);

    DtoWrapper.BasicTagDto convertToBasicTag(Tag tag) ;
    List<DtoWrapper.BasicTagDto> convertToBasicTags(List<Tag> tag) ;
    List<DtoWrapper.BasicTagWithCountOfArticlesDto> convertToBasicTagsWithCount(List<TagWithCountProjector> tag) ;
}
