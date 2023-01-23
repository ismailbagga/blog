import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from '@angular/core';
import { Tag } from '../../../core/global-services/http-article.service';

@Component({
  selector: 'app-tag-pill',
  templateUrl: './tag-pill.component.html',
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TagPillComponent implements OnInit {
  @Input('tag') tag!: Tag;
  constructor() {}

  ngOnInit(): void {}
}
