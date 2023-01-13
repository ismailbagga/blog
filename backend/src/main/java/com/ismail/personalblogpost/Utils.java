package com.ismail.personalblogpost;

import jakarta.persistence.PrePersist;

import java.util.regex.Pattern;

public class Utils {

    public static String slugify(String slug) {
        final Pattern WHITESPACE = Pattern.compile("\\s+") ;
        final Pattern NOT_NORMAL_CHAR = Pattern.compile("[^\\w-]+") ;
        var noWhiteSpace = WHITESPACE.matcher(slug.toLowerCase().strip()).replaceAll("-");
        return  NOT_NORMAL_CHAR.matcher(noWhiteSpace).replaceAll("") ;

    }
}
