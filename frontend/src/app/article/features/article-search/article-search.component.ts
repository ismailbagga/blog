import {
  HttpErrorResponse,
  HttpParams,
  HttpStatusCode,
} from '@angular/common/http';
import { FormControl } from '@angular/forms';
import {
  ArticleHttpService,
  ArticlePreviwWithResultCount,
} from '../../../core/global-services/http-article.service';
import { ArticlePreview } from '../../../core/global-services/http-article.service';
import {
  AfterViewInit,
  Component,
  OnInit,
  ChangeDetectorRef,
} from '@angular/core';
import { EMPTY, catchError, count, of, switchMap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { error } from 'console';

type Waiting = {
  waiting: true;
};

@Component({
  templateUrl: './article-search.component.html',
  styles: [],
})
export class ArticleSearchComponent implements OnInit {
  resultSet: ArticlePreview[] = [];
  latestSet: ArticlePreview[] = [];
  titleCtrl = new FormControl('');
  loading = true;
  searchData: {
    count: number | null;
    term: string;
    page: number;
    loading: boolean;
    totalPages: number;
  } = {
    term: '',
    count: null,
    page: 1,
    loading: true,
    totalPages: 0,
  };
  constructor(
    private cdRef: ChangeDetectorRef,
    private articlesService: ArticleHttpService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    // this.articlesService
    //   .fetchLatestArticles()
    //   .subscribe((result) => (this.resultSet = result));
  }

  ngOnInit(): void {
    console.log('re render');

    this.titleCtrl.valueChanges.subscribe((term) => {
      (this.searchData.term = term ?? ''), (this.searchData.count = null);
      this.router.navigate([], { queryParams: { t: term } });
    });

    this.activatedRoute.queryParams
      .pipe(
        switchMap((params) => {
          console.log('params changed');

          let title = params['t'];
          let page = parseInt(params['p']);
          const isTitleExists =
            typeof title === 'string' && title.trim() !== '';
          const isPageNumberExists = !Number.isNaN(page) && page > 0;
          !isTitleExists ? (title = '') : null;
          !isPageNumberExists ? (page = 1) : null;
          this.searchData.term = title;
          this.searchData.page = page;
          this.searchData.loading = true;
          this.cdRef.detectChanges();
          if (this.searchData.count)
            return this.articlesService
              .searchByTitle(title, page)
              .pipe(catchError((error) => this.errorhandler(error)));
          return this.articlesService
            .searchByTitleWithResultCount(title, page)
            .pipe(catchError((error) => this.errorhandler(error)));
        })
      )
      .subscribe((result) => {
        if ((result as Waiting).waiting) return;
        if ((result as ArticlePreviwWithResultCount).count !== undefined) {
          const resultWithCount = result as ArticlePreviwWithResultCount;
          this.searchData.count = resultWithCount.count;
          this.searchData.totalPages = Math.ceil(this.searchData.count / 8);
          this.resultSet = resultWithCount.articlePreviews;
        } else if ((result as ArticlePreview[]).length !== undefined) {
          const resultArray = result as ArticlePreview[];
          this.resultSet = resultArray;
        }
        this.searchData = { ...this.searchData, loading: false };
        this.cdRef.detectChanges();
      });
  }
  movePage(direction: 'NEXT' | 'PREV') {
    if (direction === 'NEXT') {
      this.router.navigate([], {
        queryParams: { p: ++this.searchData.page },
        queryParamsHandling: 'merge',
      });
    } else
      this.router.navigate([], {
        queryParams: { p: --this.searchData.page },
        preserveFragment: true,
      });
  }
  searchByTitle() {}
  errorhandler(error: Error) {
    if (error instanceof HttpErrorResponse) {
      if (error.status == HttpStatusCode.BadRequest) {
        const message = error.error.message as String;
        const maxPages: any = message.at(message.length - 1);
        if (!Number.isNaN(maxPages) && maxPages >= 1) {
          this.router.navigate([], {
            queryParams: { p: maxPages },
            preserveFragment: true,
          });
        } else this.router.navigateByUrl('/');
      }
    }
    return of({ waiting: true });
  }
}
