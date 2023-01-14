package com.ismail.personalblogpost.Article;

import com.ismail.personalblogpost.DtoWrapper;
import com.ismail.personalblogpost.DtoWrapper.CloudinarySignature;
import com.ismail.personalblogpost.Tag.Tag;
import com.ismail.personalblogpost.Tag.TagRepository;
import com.ismail.personalblogpost.Utils;
import com.ismail.personalblogpost.exception.APIException;
import com.ismail.personalblogpost.mapper.ArticleMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final CloudinaryImageService cloudinaryImageService;
    private final ArticleMapper articleMapper;

    public ArticleService(ArticleRepository articleRepository,
                          TagRepository tagRepository, CloudinaryImageService cloudinaryImageService,
                          ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.cloudinaryImageService = cloudinaryImageService;
        this.articleMapper = articleMapper;
    }

    public CloudinarySignature produceSignature() {
        return cloudinaryImageService.produceSignature();
    }

    @Transactional
    public String saveArticle(DtoWrapper.ArticleUploadDto articleUploadDto) {
        articleUploadDto.setSlug(Utils.OnEmptySlug(articleUploadDto.getSlug(), articleUploadDto.getTitle()));
//        cloudinaryImageService.validate();
        duplicateArticleHandler(articleUploadDto.getTitle(), articleUploadDto.getSlug());

        var article = articleMapper.convertUploadDtoToArticle(articleUploadDto);
        if (articleUploadDto.getTagIds().size() > 0) {
            Set<Tag> relatedTags = tagRepository.findTagByIdIn(articleUploadDto.getTagIds());
            article.setRelatedTags(relatedTags);
        }
        // TODO:set prev article here
        articleRepository.save(article);
        return article.getSlug();
    }

    @Transactional
    public DtoWrapper.ArticlePreview updateArticleContent(Long articleId , DtoWrapper.ArticleContent articleContent) {
        var article = articleRepository.findById(articleId)
                .orElseThrow(() -> new APIException("there is no article with this id", HttpStatus.NOT_FOUND));
//     -----------------   Insuring the title and slug are unique -------------------------------------
        if (!article.getSlug().equals(articleContent.getSlug()) && article.getTitle().equals(articleContent.getTitle())) {
            duplicateArticleHandler(articleContent.getTitle(), article.getSlug());
        } else if (!article.getSlug().equals(articleContent.getSlug())) {
            articleRepository.findBySlug(articleContent.getSlug())
                    .ifPresent((ignore -> {
                        throw new APIException("this slug already exists", HttpStatus.CONFLICT);
                    }));
        } else if (!article.getTitle().equals(articleContent.getTitle())) {
            articleRepository.findByTitle(articleContent.getTitle())
                    .ifPresent((ignore -> {
                        throw new APIException("this title already exists", HttpStatus.CONFLICT);
                    }));
        }
//        -------------------- Update Tags ------------------------------
        article.getRelatedTags().removeIf((art) ->  articleContent.getTagsToRemove().contains(art.getId()) ) ;
        if (articleContent.getTagsToAdd().size() >  0) {
            Set<Tag> tagsToAdd = tagRepository.findTagByIdIn(articleContent.getTagsToAdd());
            article.getRelatedTags().addAll(tagsToAdd);
        }
        articleMapper.updateArticle(articleContent, article);
        return articleMapper.convertToArticlePreview(articleRepository.save(article));
    }

    private void duplicateArticleHandler(String title, String slug) {
        var list = articleRepository.findByTitleOrSlug(title, slug);
        if (list.size() > 0) {
            if (list.size() == 2 || (list.get(0).getTitle().equals(title) && list.get(0).getSlug().equals(slug))) {
                throw new APIException("slug and title is already being used", HttpStatus.CONFLICT);
            }
            if (list.get(0).getTitle().equals(title))
                throw new APIException("title is being used", HttpStatus.CONFLICT);
            throw new APIException("slug is being used", HttpStatus.CONFLICT);
        }
    }
}
