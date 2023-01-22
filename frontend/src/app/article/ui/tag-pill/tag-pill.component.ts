import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from '@angular/core';
import { Tag } from '../../http/http-service.service';

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