import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { Tag } from 'src/app/core/global-services/http-article.service';

@Component({
  selector: 'app-tag-button',
  templateUrl: './tag-check-button.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TagButton implements OnInit {
  @Input('tag') tag!: Tag;
  @Output('onClicked') onClicked = new EventEmitter<number>();
  constructor() {}

  ngOnInit(): void {}
}
