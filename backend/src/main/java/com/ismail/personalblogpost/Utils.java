package com.ismail.personalblogpost;

import jakarta.persistence.PrePersist;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;

import java.util.*;
import java.util.regex.Pattern;

public class Utils {


    public static String OnEmptySlug(String slug, String title) {
        if (slug != null && !slug.strip().equals("")) return slug;
        return Utils.slugify(title);
    }

    private static String slugify(String slug) {
        final Pattern WHITESPACE = Pattern.compile("\\s+");
        final Pattern NOT_NORMAL_CHAR = Pattern.compile("[^\\w-]+");
        var noWhiteSpace = WHITESPACE.matcher(slug.toLowerCase().strip()).replaceAll("-");
        return NOT_NORMAL_CHAR.matcher(noWhiteSpace).replaceAll("");

    }
    public static String  mapErrorToMap(BindingResult bindingResult) {
        var errors  = new  StringBuilder().append("{");
        bindingResult.getAllErrors().forEach((objectError) -> {
            var fieldName =  Objects.requireNonNull(objectError.getCodes())[0].split("\\.")[2] ;
            errors.append(" %s : %s , ".formatted(fieldName,objectError.getDefaultMessage())) ;
        });
        // To Remove the Last ,
        var len = errors.length()-1 ;
        return errors.delete(len-2,len).append("}").toString();
    }

}
