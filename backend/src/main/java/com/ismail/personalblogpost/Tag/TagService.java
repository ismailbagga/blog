package com.ismail.personalblogpost.Tag;

import com.ismail.personalblogpost.DtoWrapper;
import com.ismail.personalblogpost.DtoWrapper.TagWithAllRelatedArticles;
import com.ismail.personalblogpost.exception.APIException;
import com.ismail.personalblogpost.mapper.TagMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Transactional
    public Tag saveTag(DtoWrapper.BasicTagDto tagDto) {

        var list = tagRepository.findByTitleOrSlug(tagDto.getTitle(), tagDto.getSlug());
        if (list.size() > 0) {
            if (list.size() == 2 || ( list.get(0).getTitle().equals(tagDto.getTitle()) &&
                            list.get(0).getSlug().equals(tagDto.getSlug()))
            ) {
                throw new APIException("slug and title is already being used", HttpStatus.CONFLICT);
            }
            if (list.get(0).getTitle().equals(tagDto.getTitle()))
                throw new APIException("title is being used", HttpStatus.CONFLICT);
            throw new APIException("slug is being used", HttpStatus.CONFLICT);
        }
            // in case id was passed
        var tag = tagMapper.convertToTagDtoToTag(tagDto) ;

        return tagRepository.save(tag);
    }

    @Transactional
    public void deleteTag(Long tagId) {
        var tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new APIException("there is no tag with id", HttpStatus.NOT_FOUND));
        tagRepository.delete(tag);
    }
    @Transactional
    public void deleteTag(String slug) {
        System.out.println(slug);

        var tag = tagRepository.findBySlug(slug)
                .orElseThrow(() -> new APIException("there is no tag with this slug", HttpStatus.NOT_FOUND));
        tagRepository.delete(tag);
    }

    public TagWithAllRelatedArticles findTagWithRelatedArticles(String slug) {
        var tag = tagRepository.findBySlugWithArticles(slug)
                .orElseThrow(() -> new APIException("there is no tag with slug", HttpStatus.NOT_FOUND));
        return tagMapper.convertToTagWithArticles(tag);
    }

    public List<DtoWrapper.BasicTagDto> findTagBySlugTerm(String slug) {
        return tagMapper.convertToBasicTag(tagRepository.findBySlugLikeIgnoreCase(slug)) ;
    }

    public List<DtoWrapper.BasicTagDto> findTagByTitleTerm(String title) {
        return tagMapper.convertToBasicTag(tagRepository.findByTitleLikeIgnoreCase(title)) ;

    }

    public List<DtoWrapper.BasicTagWithCountOfArticlesDto>   findAllTags() {
        return tagMapper.convertToBasicTagWithCount(tagRepository.findAllTagsWithCount()) ;
    }
}
