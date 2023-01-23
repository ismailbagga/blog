import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchFormComponent implements OnInit {
  @Input('controler') ctrl!: FormControl;
  @Input('header') header!: String;
  @Input('keyword') keyword?: String;

  constructor() {}

  ngOnInit(): void {}
}
