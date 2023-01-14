package com.ismail.personalblogpost.Article;

import com.ismail.personalblogpost.DtoWrapper;
import com.ismail.personalblogpost.DtoWrapper.CloudinarySignature;
import com.ismail.personalblogpost.Utils;
import com.ismail.personalblogpost.exception.APIException;
import com.ismail.personalblogpost.mapper.ArticleMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private  final  ArticleRepository articleRepository ;
    private final  CloudinaryImageService cloudinaryImageService ;
    private final ArticleMapper articleMapper ;

    public ArticleService(ArticleRepository articleRepository,
                          CloudinaryImageService cloudinaryImageService,
                          ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.cloudinaryImageService = cloudinaryImageService;
        this.articleMapper = articleMapper;
    }

    public CloudinarySignature produceSignature() {
        return cloudinaryImageService.produceSignature() ;
    }

    public String  saveArticle(DtoWrapper.ArticleUploadDto articleUploadDto) {
        articleUploadDto.setSlug(Utils.OnEmptySlug(articleUploadDto.getSlug(),articleUploadDto.getTitle()));
//        cloudinaryImageService.validate();
        duplicateArticleHandler(articleUploadDto.getTitle(),articleUploadDto.getSlug());
        var article =  articleMapper.convertUploadDtoToArticle(articleUploadDto) ;
        articleRepository.save(article) ;
        return  article.getSlug() ;

    }
    private  void duplicateArticleHandler(String title ,String slug) {
        var list = articleRepository.findByTitleOrSlug(title, slug);

        if (list.size() > 0) {
            if (list.size() == 2 || ( list.get(0).getTitle().equals(title) &&
                    list.get(0).getSlug().equals(slug))
            ) {
                throw new APIException("slug and title is already being used", HttpStatus.CONFLICT);
            }
            if (list.get(0).getTitle().equals(title))
                throw new APIException("title is being used", HttpStatus.CONFLICT);
            throw new APIException("slug is being used", HttpStatus.CONFLICT);
        }
    }
}
