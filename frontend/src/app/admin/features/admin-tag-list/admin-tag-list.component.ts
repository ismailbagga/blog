import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import {
  ArticleHttpService,
  Tag,
} from 'src/app/core/global-services/http-article.service';

@Component({
  templateUrl: './admin-tag-list.component.html',
  styles: [],
})
export class AdminTagListComponent implements OnInit {
  tagTitleCtrl = new FormControl();
  tags: Tag[] = [];
  filteredTags: Tag[] = [];

  constructor(
    private articleService: ArticleHttpService,
    private router: Router
  ) {
    this.articleService.findAllTags().subscribe((result) => {
      this.filteredTags = result;
      this.tags = result;
    });
  }

  ngOnInit(): void {
    this.tagTitleCtrl.valueChanges.subscribe((term: string) => {
      term = term.toLowerCase();
      this.filteredTags = this.tags.filter((t) =>
        t.title.toLowerCase().includes(term)
      );
    });
  }
  onDetails(slug: string) {
    this.router.navigateByUrl('/tags/' + slug);
  }
  onEdit(slug: string) {
    console.log(slug);
    this.router.navigateByUrl('/admin/edit/tag/' + slug);
  }
  deleteTag(tagId: number) {
    const self = this;
    this.articleService.deleteTag(tagId).subscribe({
      next(value) {
        self.filteredTags = self.filteredTags.filter((art) => art.id !== tagId);
      },
      error(err) {
        if (err instanceof HttpErrorResponse) {
          if (err.status === HttpStatusCode.NotFound) {
            alert('there is tag with this id');
            return;
          }
        }
        alert('Ops Something Went Wrong');
      },
    });
  }
}
