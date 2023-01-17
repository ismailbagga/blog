package com.ismail.personalblogpost.Article;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "markdown_content")
@Entity
@AllArgsConstructor
@Builder
@Setter
@Getter
@NoArgsConstructor
public class MarkdownContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "markdown_id_sequence")
    @SequenceGenerator(name = "markdown_id_sequence", sequenceName = "markdown_content_id_seq", allocationSize = 1)
    Long id   ;
    @Column(columnDefinition = "TEXT")
    String content ;


}
