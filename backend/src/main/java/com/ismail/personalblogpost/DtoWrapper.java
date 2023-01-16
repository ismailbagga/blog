package com.ismail.personalblogpost;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.Set;

public abstract class DtoWrapper {
    //    ----------------- Global Slug Dto
    @Getter
    @Setter
    private static class SlugDto {
        @Pattern(regexp = "(^[\\w-]+$)|(^.{0}$)")
        private String slug;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CloudinarySignature {
        String signature;
        Long expiredAtInSeconds;
    }

    @Data
    @NoArgsConstructor
    public static class ImageUploadDto {
        String version;
        String signature;
        String url;
    }
//    -------- Articles

    @Data
    @NoArgsConstructor
    public static class BasicArticle {
        private Long id;
        private String title;
        private String slug;
        private String description;
        private String url;
    }

    @Data
    @NoArgsConstructor
    public static class ArticleDetails {
        private Long id;
        private String title;
        private String slug;
        private String description;
        private String url;
        private LocalDate createdAt;
        private LocalDate updatedAt;
        private Set<BasicTagDto> relatedTags;
        private BasicArticle nextArticle;
        private BasicArticle prevArticle;
    }

    @Data
    @NoArgsConstructor
    public static class ArticlePreview {
        private Long id;
        private String title;
        private String slug;
        private String description;
        private String url;
        private LocalDate createdAt;
        private LocalDate updatedAt;
        private Set<BasicTagDto> relatedTags;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ArticleContent extends SlugDto {
        @NotBlank
        String title;
        @NotNull
        @Min(1)
        short readingTime;
        @NotBlank
        private String description;
        @NotBlank
        private String content;

        @NotNull
        Set<Long> tagsToRemove;
        @NotNull
        Set<Long> tagsToAdd;


    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ArticleUploadDto extends SlugDto {
        @NotBlank
        String title;
        @NotNull
        @Min(1)
        short readingTime;
        @NotBlank
        private String description;
        @NotBlank
        private String content;
        @NotNull
        private Set<Long> tagIds;
        @NotBlank
        private String url;
//        @NotBlank
//        String version;
//        @NotBlank
//        String signature;
    }
//    -------------------- Tag DTO

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TagWithAllRelatedArticles extends SlugDto {
        Long id;
        String title;
        Set<ArticlePreview> relatedArticles;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BasicTagDto extends SlugDto {
        Long id;
        @NotBlank
        String title;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateTagDto extends SlugDto {
        @NotNull
        Long id;
        @NotBlank
        String title;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BasicTagWithCountOfArticlesDto extends SlugDto {
        Long id;
        @NotBlank
        String title;
        int count;
    }
}
