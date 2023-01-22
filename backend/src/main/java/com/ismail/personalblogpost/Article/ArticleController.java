package com.ismail.personalblogpost.Article;

import com.ismail.personalblogpost.DtoWrapper;
import com.ismail.personalblogpost.DtoWrapper.ArticleUploadDto;
import com.ismail.personalblogpost.Utils;
import com.ismail.personalblogpost.exception.APIException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/articles")
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public ResponseEntity<List<DtoWrapper.ArticlePreview>> fetchAll() {
        return ResponseEntity.ok(articleService.fetchAllArticle());
    }
    @GetMapping("/details/{slug}")
    public ResponseEntity<DtoWrapper.ArticleDetails> findDetailsOfArticleBySlug(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(articleService.fetchDetailOfArticle(slug));
    }
    @GetMapping("/search/tags")
    public ResponseEntity<List<DtoWrapper.ArticlePreview>> findArticleRelatedToTags(@RequestParam("tags") Long[] tags) {
        log.debug("tags passed are -> {}", Arrays.toString(tags));
        return ResponseEntity.ok(articleService.findArticleByRelatedTags(tags)) ;
    }
    @GetMapping("/search/title/{offset}")
    public ResponseEntity<List<DtoWrapper.ArticlePreview>> findArticleByTitle(
            @PathVariable int  offset ,
            @RequestParam("title") String title) {
        if ( offset < 0 ) offset = 0 ;
        return ResponseEntity.ok(articleService.findArticleByTerm(title,offset)) ;
    }

    @GetMapping("/search/title/total/{offset}")
    public ResponseEntity<DtoWrapper.ListOfArticlesWithTotalElements> findArticleByTitleWithTotalElements(
            @PathVariable int  offset ,
            @RequestParam("title") String title) {
        if ( offset < 0 ) offset = 0 ;
        return ResponseEntity.ok(articleService.findArticleWithTotalElementsByTerm(title,offset)) ;
    }

    @PostMapping("/signature")
    public ResponseEntity<DtoWrapper.CloudinarySignature> generateSignature() {
        return ResponseEntity.ok(articleService.produceSignature());
    }

    @PostMapping
    public ResponseEntity<Map> uploadArticle(@Valid @RequestBody ArticleUploadDto articleUploadDto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new APIException(Utils.mapErrorToMap(bindingResult), HttpStatus.BAD_REQUEST);
        }
        var articleSlug = articleService.saveArticle(articleUploadDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("articleSlug", articleSlug));
    }

    @PutMapping("/meta-data/{articleId}")
    public ResponseEntity<DtoWrapper.ArticlePreview> updateArticleMetaData(@PathVariable Long articleId,
                                                                          @Valid @RequestBody DtoWrapper.ArticleMetaData articleMetaData,
                                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            throw new APIException(Utils.mapErrorToMap(bindingResult), HttpStatus.BAD_REQUEST);
        }
        var article = articleService.updateArticleMetaData(articleId, articleMetaData);
        return ResponseEntity.ok(article);
    }
    @PutMapping("/image/{articleId}")
    public ResponseEntity<DtoWrapper.ArticlePreview> updateArticleImage(@PathVariable Long articleId,
                                                                          @Valid @RequestBody DtoWrapper.ImagePayload articleMetaData,
                                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            throw new APIException(Utils.mapErrorToMap(bindingResult), HttpStatus.BAD_REQUEST);
        }
        var article = articleService.updateArticleImage(articleId, articleMetaData);
        return ResponseEntity.ok(article);
    }
    @PutMapping("/content/{articleId}")
    public ResponseEntity<Void> updateArticleContent(@PathVariable Long articleId,
                                                                          @Valid @RequestBody DtoWrapper.ArticleContent articleContent,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new APIException(Utils.mapErrorToMap(bindingResult), HttpStatus.BAD_REQUEST);
        }
        articleService.updateArticleContent(articleId, articleContent);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId) {

        articleService.deleteArticle(articleId);
        return ResponseEntity.ok().build();
    }

}
