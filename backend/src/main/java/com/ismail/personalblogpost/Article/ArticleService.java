package com.ismail.personalblogpost.Article;

import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private  final  ArticleRepository articleRepository ;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
}
