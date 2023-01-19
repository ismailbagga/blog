import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-subscription-form',
  templateUrl: './subscription-form.component.html',
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SubscriptionFormComponent implements OnInit {
  emailCtrl = new FormControl('', [Validators.required, Validators.email]);
  constructor() {}

  ngOnInit(): void {
    // this.emailCtrl.
  }
  onSubmit(event: Event) {
    event.preventDefault();
    if (this.emailCtrl.invalid) return;
    console.log(this.emailCtrl.value);

    console.log('subscribing this email');
  }
}
