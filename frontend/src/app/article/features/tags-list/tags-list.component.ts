import {
  ArticleHttpService,
  TagWithCount,
} from '../../../core/global-services/http-article.service';
import { FormControl } from '@angular/forms';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  templateUrl: './tags-list.component.html',
  styles: [],
})
export class TagsListComponent implements OnInit {
  tags: TagWithCount[] = [];
  constructor(
    private articleHttpService: ArticleHttpService,
    private router: Router
  ) {
    const self = this;
    articleHttpService.fetchAllTagWithCount().subscribe({
      next(result) {
        self.tags = result;
      },
    });
  }

  ngOnInit(): void {}
  onTagClicked(id: number) {
    console.log(id);
    const result = this.tags.find((tag) => tag.id === id);
    if (result) {
      console.log(result);
      this.router.navigate(['/tags', result.slug]);
    }
  }
}
