import { ArticleHttpService } from 'src/app/core/global-services/http-article.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastType } from 'src/app/shared/toast/toast.component';
import { TagHttpService } from 'src/app/core/global-services/tag-http-service.service';

@Component({
  templateUrl: './create-tag.component.html',
  styles: [],
})
export class CreateTagComponent implements OnInit {
  form = new FormGroup({
    title: new FormControl('angular-', [Validators.required]),
    slug: new FormControl('', [Validators.pattern('^[a-z1-9-]+$')]),
  });
  toastInfo?: {
    type: ToastType;
    data: string;
  };
  constructor(private tagService: TagHttpService) {}

  ngOnInit(): void {}
  onFormSubmit(event: Event) {
    event.preventDefault();
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const self = this;
    this.tagService.saveTag(this.form.value as any).subscribe({
      next(tag) {
        self.toastInfo = { type: 'SUCCESS', data: tag.slug };
      },
      error(err) {
        self.toastInfo = { type: 'ERROR', data: err };
        console.warn(err);
      },
    });
  }
  closeToast() {
    this.toastInfo = undefined;
  }
}
