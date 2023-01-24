import { FormControl } from '@angular/forms';
import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from '@angular/core';

@Component({
  selector: 'app-input-field',
  templateUrl: './input-field.component.html',
  styles: [],
})
export class InputFieldComponent implements OnInit {
  @Input() fieldControler!: FormControl;
  @Input() placeholder!: string;
  @Input() type!: 'password' | 'email' | 'text' | 'placeholder' | 'number';
  @Input() label!: string;
  constructor() {}

  ngOnInit(): void {}
}
