import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import {
  ArticleDetails,
  ArticleHttpService,
} from 'src/app/core/global-services/http-article.service';

@Component({
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.css'],
})
export class ArticleDetailsComponent implements OnInit {
  article?: ArticleDetails;
  constructor(
    private articleService: ArticleHttpService,
    activatedRoute: ActivatedRoute,
    router: Router
  ) {
    const self = this;
    activatedRoute.params
      .pipe(
        switchMap((params) => {
          console.log(params);
          return this.articleService.findArticleBySlug(params['slug']);
        })
      )
      .subscribe({
        next(value) {
          console.log(value);
          self.article = value;
        },
        error(err) {
          router.navigateByUrl('/404');
        },
      });
  }

  ngOnInit(): void {}
}
