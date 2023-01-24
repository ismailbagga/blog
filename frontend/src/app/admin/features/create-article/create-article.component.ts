import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ArticleHttpService } from 'src/app/core/global-services/http-article.service';

@Component({
  templateUrl: './create-article.component.html',
  styles: [],
})
export class CreateArticleComponent implements OnInit {
  form = new FormGroup({
    image: new FormControl('', [Validators.required]),
    searchTerm: new FormControl(''),
    relatedTopics: new FormControl([] as number[]),
    readingTimeInMinutes: new FormControl(1, [
      Validators.required,
      Validators.min(1),
    ]),
    title: new FormControl('', [Validators.required]),
    slider: new FormControl(1, [
      Validators.required,
      Validators.min(1),
      Validators.max(4),
    ]),
    description: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    content: new FormControl('', [Validators.required]),
  });
  constructor(private articleService : ArticleHttpService) {}

  ngOnInit(): void {}
  
}
