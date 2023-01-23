import { ArticlePreview } from './../../http/http-service.service';
import { FormControl } from '@angular/forms';
import { of, switchMap, EMPTY, map, catchError, Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { ArticleHttpService } from './../../http/http-article.service';
import { Component, OnInit } from '@angular/core';
import { Tag } from '../../http/http-article.service';

@Component({
  templateUrl: './articles-of-tag-list.component.html',
  styles: [],
})
export class ArticlesOfTagListComponent implements OnInit {
  titleField = new FormControl();
  articles: ArticlePreview[] = [];
  filteredArticle: ArticlePreview[] = [];
  tag!: Tag;
  loading = true;
  constructor(
    private articleService: ArticleHttpService,
    activeRoute: ActivatedRoute
  ) {
    activeRoute.params
      .pipe(
        map((params) => {
          console.log(params);
          return params['slug'];
        }),
        switchMap((slug) => {
          if (slug) {
            return articleService.fetchTagWithRelatedArticleBySlug(slug);
          }
          throw new Error('somethinf went wrong');
        }),
        catchError((error) => {
          console.warn(error);
          return EMPTY;
        })
      )
      .subscribe((result) => {
        this.articles = result.relatedArticles;
        this.filteredArticle = this.articles;
        this.tag = result;
        this.loading = false;
      });
  }

  ngOnInit(): void {
    this.titleField.valueChanges.subscribe((text: string) => {
      text = text.toLowerCase();
      this.filteredArticle = this.articles.filter((ref) =>
        ref.title.toLowerCase().includes(text)
      );
      console.log(text);
    });
  }
}
