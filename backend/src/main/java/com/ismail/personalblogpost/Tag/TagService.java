package com.ismail.personalblogpost.Tag;

import com.ismail.personalblogpost.dto.DtoWrapper;
import com.ismail.personalblogpost.dto.DtoWrapper.TagWithAllRelatedArticles;
import com.ismail.personalblogpost.Utils;
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

        tagDto.setSlug(Utils.OnEmptySlug(tagDto.getSlug(),tagDto.getTitle()));
        validateDuplicateTag(tagDto.getTitle(), tagDto.getSlug());
        // in case id was passed
        var tag = tagMapper.convertToTagDtoToTag(tagDto) ;
        return tagRepository.save(tag);
    }

    private  void validateDuplicateTag(String dtoTitle ,String dtoSlug) {
        var list = tagRepository.findByTitleOrSlug(dtoTitle, dtoSlug);

        if (list.size() > 0) {
            if (list.size() == 2 || ( list.get(0).getTitle().equals(dtoTitle) &&
                            list.get(0).getSlug().equals(dtoSlug))
            ) {
                throw new APIException("slug and title is already being used", HttpStatus.CONFLICT);
            }
            if (list.get(0).getTitle().equals(dtoTitle))
                throw new APIException("title is being used", HttpStatus.CONFLICT);
            throw new APIException("slug is being used", HttpStatus.CONFLICT);
        }
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

    public List<DtoWrapper.BasicTagDto> findTagByTitleTerm(String title) {
        return tagMapper.convertToBasicTags(tagRepository.findByTitleIsContainingIgnoreCase(title)) ;

    }

    public List<DtoWrapper.BasicTagWithCountOfArticlesDto>   findAllTags() {
        return tagMapper.convertToBasicTagsWithCount(tagRepository.findAllTagsWithCount()) ;
    }

    public DtoWrapper.BasicTagDto findTagById(Long tagId) {
        var tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new APIException("there is no tag with this id", HttpStatus.NOT_FOUND));
        return tagMapper.convertToBasicTag(tag) ;

    }

    public DtoWrapper.BasicTagDto findTagBySlug(String slug) {
        var tag = tagRepository.findBySlug(slug)
                .orElseThrow(() -> new APIException("there is no tag with this id", HttpStatus.NOT_FOUND));

        return tagMapper.convertToBasicTag(tag) ;
    }
    @Transactional
    public DtoWrapper.UpdateTagDto updateTag(DtoWrapper.UpdateTagDto dto) {
        var tag = tagRepository.findById(dto.getId())
                .orElseThrow(() -> new APIException("there is no tag with this id", HttpStatus.NOT_FOUND));
        dto.setSlug(Utils.OnEmptySlug(dto.getSlug(),dto.getTitle()));
        validateDuplicateTag(dto.getTitle(),dto.getSlug());
        tagMapper.updateTag(dto,tag);
        var savedTag = tagRepository.save(tag) ;
        return dto ;

    }


}
