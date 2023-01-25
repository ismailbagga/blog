import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { Tag } from 'src/app/core/global-services/http-article.service';
import { TagHttpService } from 'src/app/core/global-services/tag-http-service.service';
import { ToastType } from 'src/app/shared/toast/toast.component';

@Component({
  templateUrl: './edit-tag.component.html',
  styles: [],
})
export class EditTagComponent implements OnInit {
  tag?: Tag;
  form = new FormGroup({
    id: new FormControl(1, [Validators.required]),
    title: new FormControl('angular-', [Validators.required]),
    slug: new FormControl('', [Validators.pattern('^[a-z1-9-]+$')]),
  });
  toastInfo?: {
    type: ToastType;
    data: string;
  };
  constructor(
    private tagService: TagHttpService,
    activeRoute: ActivatedRoute,
    router: Router
  ) {
    const self = this;
    activeRoute.params
      .pipe(
        switchMap((params) => {
          return this.tagService.findTagBySlug(params['slug']);
        })
      )
      .subscribe({
        next(value: Tag) {
          self.tag = value;
          self.form.patchValue({ ...value });
        },
        error(err) {
          router.navigateByUrl('/404');
        },
      });
  }

  ngOnInit(): void {}
  onFormSubmit(event: Event) {
    event.preventDefault();
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const self = this;
    this.tagService.editTag(this.form.value as any).subscribe({
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
