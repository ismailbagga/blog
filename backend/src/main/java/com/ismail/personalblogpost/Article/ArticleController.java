package com.ismail.personalblogpost.Article;

import com.ismail.personalblogpost.DtoWrapper;
import com.ismail.personalblogpost.DtoWrapper.ArticleUploadDto;
import com.ismail.personalblogpost.exception.APIException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/signature")
    public ResponseEntity<DtoWrapper.CloudinarySignature> generateSignature() {
        return ResponseEntity.ok(articleService.produceSignature());
    }

    @PostMapping()
    public ResponseEntity<Map> uploadArticle(@Valid @RequestBody ArticleUploadDto articleUploadDto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new APIException("Invalid request body ", HttpStatus.BAD_REQUEST);
        }
        var articleSlug = articleService.saveArticle(articleUploadDto) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("articleSlug",articleSlug)) ;
    }
    @PutMapping("/{articleId}")
    public ResponseEntity<DtoWrapper.ArticlePreview> updateArticleContent(@PathVariable Long articleId ,
                                                                          @Valid @RequestBody DtoWrapper.ArticleContent articleContent,
                                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new APIException("Invalid request body ", HttpStatus.BAD_REQUEST);
        }
        var article = articleService.updateArticleContent(articleId,articleContent) ;
        return ResponseEntity.ok(article) ;
    }

}
