package com.ismail.personalblogpost.Article;

import com.ismail.personalblogpost.DtoWrapper;
import com.ismail.personalblogpost.DtoWrapper.CloudinarySignature;
import com.ismail.personalblogpost.Tag.Tag;
import com.ismail.personalblogpost.Tag.TagRepository;
import com.ismail.personalblogpost.Utils;
import com.ismail.personalblogpost.exception.APIException;
import com.ismail.personalblogpost.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final CloudinaryImageService cloudinaryImageService;
    private final ArticleMapper articleMapper;
    private final MarkdownRepository markdownRepository ;

    public ArticleService(ArticleRepository articleRepository,
                          TagRepository tagRepository, CloudinaryImageService cloudinaryImageService,
                          ArticleMapper articleMapper, MarkdownRepository markdownRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.cloudinaryImageService = cloudinaryImageService;
        this.articleMapper = articleMapper;
        this.markdownRepository = markdownRepository;
    }

    public CloudinarySignature produceSignature() {
        return cloudinaryImageService.produceSignature();
    }


    public List<DtoWrapper.ArticlePreview> fetchAllArticle() {

        var sort = Sort.by(Sort.Direction.DESC, "updatedAt");
        return articleMapper.convertToArticlePreviewList(articleRepository.findAllWithEagerFetch(sort));
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
        markdownRepository.save(article.getMarkdownContent()) ;
        return article.getSlug();
    }

    @Transactional
    public DtoWrapper.ArticlePreview updateArticleMetaData(Long articleId, DtoWrapper.ArticleMetaData articleMetaData) {
        var article = articleRepository.findById(articleId)
                .orElseThrow(() -> new APIException("there is no article with this id", HttpStatus.NOT_FOUND));
//        articleMetaData.setSlug(Utils.OnEmptySlug(articleMetaData.getSlug(), articleMetaData.getTitle()));
//     -----------------   Insuring the title and slug are unique -------------------------------------
        if (!article.getSlug().equals(articleMetaData.getSlug()) && !article.getTitle().equals(articleMetaData.getTitle())) {
            duplicateArticleHandler(articleMetaData.getTitle(), articleMetaData.getSlug());
        } else if (!article.getSlug().equals(articleMetaData.getSlug())) {
            articleRepository.findBySlug(articleMetaData.getSlug())
                    .ifPresent((ignore -> {
                        throw new APIException("this slug already exists", HttpStatus.CONFLICT);
                    }));
        } else if (!article.getTitle().equals(articleMetaData.getTitle())) {
            articleRepository.findByTitle(articleMetaData.getTitle())
                    .ifPresent((ignore -> {
                        throw new APIException("this title already exists", HttpStatus.CONFLICT);
                    }));
        }
//        -------------------- Update Tags ------------------------------
        article.getRelatedTags().removeIf((art) -> articleMetaData.getTagsToRemove().contains(art.getId()));
        if (articleMetaData.getTagsToAdd().size() > 0) {
            Set<Tag> tagsToAdd = tagRepository.findTagByIdIn(articleMetaData.getTagsToAdd());
            article.getRelatedTags().addAll(tagsToAdd);
        }
        articleMapper.updateArticle(articleMetaData, article);
        return articleMapper.convertToArticlePreview(articleRepository.save(article));
    }


    @Transactional
    public void deleteArticle(Long articleId) {
        var article = articleRepository.findById(articleId)
                .orElseThrow(() -> new APIException("there is no article with this id", HttpStatus.NOT_FOUND));
        articleRepository.delete(article);

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

    public DtoWrapper.ArticleDetails fetchDetailOfArticle(String slug) {
        log.info("start fetch for article ");
        var article = articleRepository.fetchArticleBySlugEagerly(slug)
                .orElseThrow(() -> new APIException("there no article with this slug", HttpStatus.NOT_FOUND));
        log.info("finish fetch for article ");
        return articleMapper.convertToArticleDetails(article);
    }

    public List<DtoWrapper.ArticlePreview> findArticleByRelatedTags(Long[] tags) {
        var resultSet = articleRepository.findArticleByRelatedTagsIn(tags) ;
        return  articleMapper.convertToArticlePreviewList(resultSet) ;
    }
    @Transactional
    public void updateArticleContent(Long articleId, DtoWrapper.ArticleContent articleContent) {
        var content = markdownRepository.findByIdJPQL(articleId)
                .orElseThrow(()-> new APIException("there is no article with this id",HttpStatus.NOT_FOUND))  ;
        content.setContent(articleContent.getContent()) ;
        markdownRepository.save(content) ;

    }
}
