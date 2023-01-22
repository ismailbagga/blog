package com.ismail.personalblogpost;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public abstract class DtoWrapper {
    //    ----------------- Global Slug Dto
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SlugDto {
        @Pattern(regexp = "(^a-z1-9-$)|(^.{0}$)")
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
    public static class ImagePayload {
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
        private String content;
        private LocalDate createdAt;
        private LocalDate updatedAt;
        private Set<BasicTagDto> relatedTags;
        private BasicArticle nextArticle;
        private BasicArticle prevArticle;
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    @ToString
    public static class ArticlePreview  {
        private Long id;
        private String title;
        private String slug;
        private String description;
        private String url;
        private LocalDateTime createdAt;
        private LocalDate updatedAt;
        private int readingTime ;
        private Set<BasicTagDto> relatedTags;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static  class ListOfArticlesWithTotalElements {
        Integer count ;
        List<ArticlePreview> articlePreviews ;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ArticleMetaData {
        @NotBlank
        String title;
        @Pattern(regexp = "^[a-z1-9-]+$")
        @NotBlank
        String slug;
        @NotNull
        @Min(1)
        short readingTime;
        @NotBlank
        private String description;
        @NotNull
        Set<Long> tagsToRemove;
        @NotNull
        Set<Long> tagsToAdd;


    }

    @Getter
    @Setter
    public static class ArticleContent {
        String content;
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
        @Valid
        private DtoWrapper.ImagePayload imagePayload ;

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
    @AllArgsConstructor
    @ToString
    public static class BasicTagDto extends SlugDto {
        Long id;
        @NotBlank
        String title;

        public BasicTagDto( Long id ,String title, String slug) {
            super(slug);
            this.id = id;
            this.title = title;
        }
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
