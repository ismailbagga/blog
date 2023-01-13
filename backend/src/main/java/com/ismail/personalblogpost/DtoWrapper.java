package com.ismail.personalblogpost;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

public class DtoWrapper {
    @NoArgsConstructor
    @Getter
    @Setter
    public static class TagPreview {
        Long id;
        String title;
        String slug;
    }

    @Data
    @NoArgsConstructor

    public static class ImageUploadDto {
        String version;
        String signature;
        String url;
    }


    @Data
    @NoArgsConstructor
    public static class ArticlePreview {
        private Long id  ;
        private String title;
        private String slug;
        private String description;
        private String url;
        private LocalDate createdAt;
        private LocalDate updatedAt;
        private Set<TagPreview> relatedTags;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ArticleUploadDto {
        String title;
        String username;
        int readingTime;
        String version;
        String signature;
        String url;
    }

    @Data
    @NoArgsConstructor
    public static class TagWithAllRelatedArticles {
        Long id;
        String title;
        String slug;
        Set<ArticlePreview> relatedArticles;
    }
    @Data
    @NoArgsConstructor
    public static class BasicTagDto {
        Long id  ;
        @NotBlank
        String title;
        @Pattern(regexp = "(^[\\w-]+$)|(^.{0}$)")
        @NotNull
        String slug;
    }
    @Data
    @NoArgsConstructor
    public static class BasicTagWithCountOfArticlesDto {
        Long id ;
        @NotBlank
        String title;
        @NotNull
        String slug;
        int count ;
    }
}
