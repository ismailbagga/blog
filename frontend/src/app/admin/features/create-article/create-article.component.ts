import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  templateUrl: './create-article.component.html',
  styles: [],
})
export class CreateArticleComponent implements OnInit {
  form = new FormGroup({
    image: new FormControl('', [Validators.required]),
  });
  constructor() {}

  ngOnInit(): void {}
}
