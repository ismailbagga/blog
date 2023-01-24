import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';
import { FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import {
  ArticleHttpService,
  ArticlePreview,
} from 'src/app/core/global-services/http-article.service';

@Component({
  templateUrl: './admin-articles-list.component.html',
  styles: [],
})
export class AdminArticlesListComponent implements OnInit {
  articleTitleCtrl = new FormControl();
  articles: ArticlePreview[] = [];
  filteredArticles: ArticlePreview[] = [];
  constructor(private articleService: ArticleHttpService) {
    articleService.fetchLatestArticles().subscribe((result) => {
      this.articles = result;
      this.filteredArticles = this.articles;
    });
  }

  ngOnInit(): void {
    this.articleTitleCtrl.valueChanges.subscribe((term: string) => {
      term = term.toLowerCase();
      this.filteredArticles = this.articles.filter((art) =>
        art.title.toLowerCase().includes(term)
      );
    });
  }

  deleteArticle(articleId: number) {
    const self = this;
    this.articleService.deleteArticle(articleId).subscribe({
      next(value) {
        self.filteredArticles = self.filteredArticles.filter(
          (art) => art.id !== articleId
        );
      },
      error(err) {
        if (err instanceof HttpErrorResponse) {
          if (err.status === HttpStatusCode.NotFound) {
            alert('there is article with this id');
          }
        }
        alert('Ops Something Went Wrong');
      },
    });
  }
}