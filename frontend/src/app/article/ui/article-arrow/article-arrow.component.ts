import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from '@angular/core';
import { BasicArticle } from 'src/app/core/global-services/http-article.service';

@Component({
  selector: 'app-article-arrow',
  templateUrl: './article-arrow.component.html',
  styleUrls: ['./article-arrow.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ArticleArrowComponent implements OnInit {
  @Input() nextArticle!: BasicArticle;
  @Input() prevArticle!: BasicArticle;
  constructor() {}

  ngOnInit(): void {}
}
