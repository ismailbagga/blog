import { ArticlePreview } from './../../http/http-service.service';
import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from '@angular/core';

@Component({
  selector: 'app-article-preview',
  templateUrl: './article-preview.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ArticlePreviewComponent implements OnInit {
  @Input('articel') article!: ArticlePreview;
  constructor() {}

  ngOnInit(): void {}
}
