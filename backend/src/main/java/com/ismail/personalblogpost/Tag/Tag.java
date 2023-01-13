package com.ismail.personalblogpost.Tag;

import com.ismail.personalblogpost.Utils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String title  ;
    private String slug  ;

    @PrePersist()
    public void prePersist() {
        if ( slug != null && !slug.strip().equals("")) return ;
        slug = Utils.slugify(title) ;

    }

}
