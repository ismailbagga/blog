package com.ismail.personalblogpost.mapper;

import com.ismail.personalblogpost.DtoWrapper;
import com.ismail.personalblogpost.DtoWrapper.TagWithAllRelatedArticles;
import com.ismail.personalblogpost.Tag.Tag;
import com.ismail.personalblogpost.projectors.TagWithCountProjector;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = ArticleMapper.class)
public interface TagMapper {

    TagWithAllRelatedArticles convertToTagWithArticles(Tag tag);

    Tag convertToTagDtoToTag(DtoWrapper.BasicTagDto tagDto);

    DtoWrapper.BasicTagDto convertToBasicTag(Tag tag) ;
    List<DtoWrapper.BasicTagDto> convertToBasicTags(List<Tag> tag) ;
    List<DtoWrapper.BasicTagWithCountOfArticlesDto> convertToBasicTagsWithCount(List<TagWithCountProjector> tag) ;
}
