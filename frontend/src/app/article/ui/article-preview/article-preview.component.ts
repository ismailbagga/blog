import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from '@angular/core';
import { ArticlePreview } from '../../../core/global-services/http-article.service';

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
