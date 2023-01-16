package com.ismail.personalblogpost;

import jakarta.persistence.PrePersist;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;
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

    public static Map<String, String> mapErrorToMap(BindingResult bindingResult) {
        var errors = new HashMap<String, String>();
        bindingResult.getAllErrors().forEach((objectError) -> {
              errors.put(objectError.getObjectName(),objectError.getDefaultMessage()) ;
        });
        return errors;
    }
}
