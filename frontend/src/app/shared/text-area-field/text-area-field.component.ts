import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-text-area-field',
  templateUrl: './text-area-field.component.html',
  styles: [
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TextAreaFieldComponent implements OnInit {
  
  constructor() { }

  ngOnInit(): void {
  }

}
