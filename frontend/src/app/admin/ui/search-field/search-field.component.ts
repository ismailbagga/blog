import { FormControl } from '@angular/forms';
import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from '@angular/core';

@Component({
  selector: 'app-search-field',
  templateUrl: './search-field.component.html',
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchFieldComponent implements OnInit {
  @Input() fieldCtrl!: FormControl;
  @Input() keyword!: string;

  constructor() {}

  ngOnInit(): void {}
}
