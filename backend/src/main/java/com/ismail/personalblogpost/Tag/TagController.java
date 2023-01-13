package com.ismail.personalblogpost.Tag;

import com.ismail.personalblogpost.DtoWrapper;
import com.ismail.personalblogpost.DtoWrapper.BasicTagWithCountOfArticlesDto;
import com.ismail.personalblogpost.DtoWrapper.TagWithAllRelatedArticles;
import com.ismail.personalblogpost.exception.APIException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    private final TagService tagService ;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<BasicTagWithCountOfArticlesDto>> findAllTagsWithCount() {

        return ResponseEntity.ok(tagService.findAllTags()) ;
    }
    @GetMapping("/{slug}/articles")
    public ResponseEntity<TagWithAllRelatedArticles> findTagWithArticles(@PathVariable("slug") String slug) {

        return ResponseEntity.ok(tagService.findTagWithRelatedArticles(slug)) ;
    }
    @GetMapping("/slug/{slug}")
    public ResponseEntity<List<DtoWrapper.BasicTagDto>> findTagBySlugTerm(@PathVariable("slug") String slug) {

        return ResponseEntity.ok(tagService.findTagBySlugTerm(slug)) ;
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<List<DtoWrapper.BasicTagDto>> findTagByTitleTerm(@PathVariable("title") String title) {

        return ResponseEntity.ok(tagService.findTagByTitleTerm(title)) ;
    }
    @PostMapping()
    public ResponseEntity<Tag> saveTag(@Valid @RequestBody DtoWrapper.BasicTagDto tag , BindingResult bindingResult) {
        if( bindingResult.hasErrors()) {
            throw new APIException("invalid request body", HttpStatus.BAD_REQUEST) ;
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.saveTag(tag)) ;
    }

    @DeleteMapping("/id/{tagId}")
    public ResponseEntity<Void> deleteById(@PathVariable("tagId") Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build() ;
    }
    @DeleteMapping("/slug/{slug}")
    public ResponseEntity<Void> deleteBySlug(@PathVariable("slug") String slug) {
        tagService.deleteTag(slug);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build() ;
    }


}
