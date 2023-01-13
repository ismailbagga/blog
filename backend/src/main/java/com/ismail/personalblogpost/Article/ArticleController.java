package com.ismail.personalblogpost.Article;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    @GetMapping()
    public String hello() {
        return "hello there" ;
    }


}
