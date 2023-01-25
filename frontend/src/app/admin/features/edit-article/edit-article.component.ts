import { ArticleDetails } from './../../../core/global-services/http-article.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import {
  ArticleHttpService,
  ArticlePreview,
} from 'src/app/core/global-services/http-article.service';
import { ToastType } from 'src/app/shared/toast/toast.component';

@Component({
  templateUrl: './edit-article.component.html',
  styles: [],
})
export class EditArticleComponent implements OnInit {
  article: ArticleDetails | null = null;

  toastInfo?: {
    type: ToastType;
    data: string;
  };
  imageField = new FormControl('', Validators.required);
  contentField = new FormControl('hELOLOOOOOOOOOO', [Validators.required]);

  form = new FormGroup({
    tagsToAdd: new FormControl([] as number[]),
    tagsToRemove: new FormControl([] as number[]),
    readingTime: new FormControl(15, [Validators.required, Validators.min(1)]),
    title: new FormControl('angular-', [Validators.required]),
    slug: new FormControl('', [Validators.pattern('^[a-z1-9-]+$')]),
    description: new FormControl('lorem ipsum bla bla', [
      Validators.required,
      Validators.maxLength(255),
    ]),
  });
  constructor(
    private articleService: ArticleHttpService,
    private activatedRoute: ActivatedRoute,
    private router: Router
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
          self.contentField.setValue(value.content);
          self.form.patchValue({ ...value });
          console.log(self.form.value);
        },
        error(err) {
          router.navigateByUrl('/404');
        },
      });
  }

  ngOnInit(): void {}
  closeToast() {
    this.toastInfo = undefined;
  }
  editImage() {
    if (this.imageField.invalid) {
      this.imageField.markAsTouched();
      return;
    }
    const file = this.imageField.value as any;
    const self = this;
    this.articleService
      .editArticleImage(file as File, this.article?.id ?? 0)
      .subscribe({
        next(slug) {
          self.toastInfo = { type: 'SUCCESS', data: slug.slug };
        },
        error(err) {
          self.toastInfo = { type: 'ERROR', data: err };
          console.warn(err);
        },
      });
  }
  editArticleContent() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const self = this;
    this.articleService
      .editArticleContent(this.form.value as any, this.article?.id ?? 0)
      .subscribe({
        next(slug) {
          self.toastInfo = { type: 'SUCCESS', data: slug.slug };
        },
        error(err) {
          self.toastInfo = { type: 'ERROR', data: err };
          console.warn(err);
        },
      });
  }
  editArticleMarkdown() {
    if (this.contentField.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const self = this;
    this.articleService
      .editArticleMarkdown(
        this.contentField.value as any,
        this.article?.id ?? 0
      )
      .subscribe({
        next(slug) {
          self.toastInfo = { type: 'SUCCESS', data: self.article?.slug ?? '' };
        },
        error(err) {
          self.toastInfo = { type: 'ERROR', data: err };
          console.warn(err);
        },
      });
  }
  onFormSubmit(event: Event) {
    event.preventDefault();
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const self = this;
    self.toastInfo = undefined;
    this.articleService.uploadArticle(this.form.value as any).subscribe({
      next(slug) {
        self.toastInfo = { type: 'SUCCESS', data: slug.slug };
      },
      error(err) {
        self.toastInfo = { type: 'ERROR', data: err };
        console.warn(err);
      },
    });
  }
}
