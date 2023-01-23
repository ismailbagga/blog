import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { TagWithCount } from '../../../core/global-services/http-article.service';

@Component({
  selector: 'app-tag-check-button',
  templateUrl: './tag-check-button.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TagCheckButtonComponent implements OnInit {
  @Input('tag') tag!: TagWithCount;
  @Input('isChecked') isChecked = false;
  @Input('showCount') showCount = true;
  @Output('onCheckBtnClicked') onClicked = new EventEmitter<number>();
  constructor() {}

  ngOnInit(): void {}
}
