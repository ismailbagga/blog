import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  ArticleFormValues,
  ArticleHttpService,
} from 'src/app/core/global-services/http-article.service';
import { ToastType } from 'src/app/shared/toast/toast.component';

@Component({
  templateUrl: './create-article.component.html',
  styles: [],
})
export class CreateArticleComponent implements OnInit {
  toastInfo?: {
    type: ToastType;
    data: string;
  };

  form = new FormGroup({
    image: new FormControl('', [Validators.required]),
    searchTerm: new FormControl(''),
    tagIds: new FormControl([] as number[]),
    readingTime: new FormControl(15, [Validators.required, Validators.min(1)]),
    title: new FormControl('angular-', [Validators.required]),
    slug: new FormControl('', [Validators.pattern('^[a-z1-9-]+$')]),
    description: new FormControl('lorem ipsum bla bla', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    content: new FormControl('hELOLOOOOOOOOOO', [Validators.required]),
  });
  constructor(private articleService: ArticleHttpService) {}

  ngOnInit(): void {}
  closeToast() {
    this.toastInfo = undefined;
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
