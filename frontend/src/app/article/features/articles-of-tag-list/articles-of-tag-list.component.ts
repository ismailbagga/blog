import { FormControl } from '@angular/forms';
import { of, switchMap, EMPTY, map, catchError, Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import {
  ArticleHttpService,
  ArticlePreview,
} from '../../../core/global-services/http-article.service';
import { Component, OnInit } from '@angular/core';
import { Tag } from '../../../core/global-services/http-article.service';

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
    activeRoute: ActivatedRoute,
    router: Router
  ) {
    activeRoute.params
      .pipe(
        map((params) => {
          return params['slug'];
        }),
        switchMap((slug) => {
          if (slug) {
            return articleService.fetchTagWithRelatedArticleBySlug(slug);
          }
          throw new Error('somethinf went wrong');
        }),
        catchError((error) => {
          router.navigateByUrl('/404');
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
    });
  }
}
