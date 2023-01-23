import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';

@Component({
  selector: 'app-delete-model',
  templateUrl: './delete-model.component.html',
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DeleteModelComponent implements OnInit {
  @Output() closeModel = new EventEmitter();
  @Output() onDelete = new EventEmitter();
  constructor() {}

  ngOnInit(): void {}
}
