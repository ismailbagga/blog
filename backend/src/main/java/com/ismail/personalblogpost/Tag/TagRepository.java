package com.ismail.personalblogpost.Tag;

import com.ismail.personalblogpost.projectors.TagWithCountProjector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    @Query(" SELECT tag FROM Tag  tag " +
            "LEFT JOIN FETCH tag.relatedArticles "+
            "where  tag.slug = :slug")
    Optional<Tag> findBySlugWithArticles(@Param("slug") String slug) ;
    @Query("SELECT tag FROM Tag tag WHERE tag.slug=:slug")
    Optional<Tag> findBySlug(@Param("slug") String slug) ;
    @Query("SELECT tag.id as id , tag.slug as slug , tag.title as title , size(tag.relatedArticles) as count FROM Tag  tag ")
    List<TagWithCountProjector> findAllTagsWithCount() ;
    List<Tag> findByTitleOrSlug(String title , String slug) ;


    Set<Tag> findTagByIdIn(Set<Long> tagsIds) ;

    List<Tag> findByTitleIsContainingIgnoreCase(String title) ;

}
