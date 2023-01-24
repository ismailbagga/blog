import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

type PageOptions = 'BlogMarkdown' | 'MarkdownPreview';

@Component({
  selector: 'app-article-content',
  templateUrl: './article-content.component.html',
  styleUrls: ['./article-content.component.css'],
})
export class ArticleContentComponent implements OnInit {
  @Input() controler!: FormControl;
  currentPage: PageOptions = 'BlogMarkdown';
  constructor() {}
  ngOnInit(): void {}

  onChangeHandler(event: Event) {
    const textAreaElem = event.target as HTMLTextAreaElement;
    console.log('on change');

    // if (event.) {}
    this.controler.patchValue(textAreaElem.value);
  }
  showBlogMarkdown() {
    this.currentPage = 'BlogMarkdown';
  }
  showPreview() {
    this.currentPage = 'MarkdownPreview';
  }
  onKeyDown(event: KeyboardEvent) {
    if (event.key === 'Tab') {
      event.preventDefault();
      const oldValue: string = this.controler.value ?? '';
      const element = event.target as HTMLTextAreaElement;
      // index wich from to im selecting in textarea
      const start = element.selectionStart;
      const end = element.selectionEnd;
      // now bring me from 0 to where cursor start
      // to curson end until the length of string
      //add tab between them
      //
      this.controler.patchValue(
        oldValue.substring(0, start) + '\t' + oldValue.substring(end)
      );
      // after add tab move cursor to were tab start
      element.selectionStart = element.selectionEnd = start + 1;
    }
  }
}
