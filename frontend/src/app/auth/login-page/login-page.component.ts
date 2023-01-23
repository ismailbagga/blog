import { Router } from '@angular/router';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from 'src/app/core/global-services/auth.service';
import {
  HttpErrorResponse,
  HttpResponse,
  HttpStatusCode,
} from '@angular/common/http';

@Component({
  templateUrl: './login-page.component.html',
  styles: [],
})
export class LoginPageComponent implements OnInit {
  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });
  errorMessage = '';
  passwordFieldType: 'text' | 'password' = 'password';
  constructor(
    private authService: AuthService,
    private router: Router,
    private changeDetection: ChangeDetectorRef
  ) {}

  ngOnInit(): void {}
  togglePasswordFieldType() {
    this.passwordFieldType === 'password'
      ? (this.passwordFieldType = 'text')
      : (this.passwordFieldType = 'password');
  }
  onLoginAttempt(event: Event) {
    event.preventDefault();
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }
    const { username, password } = this.loginForm.value;
    const self = this;
    this.authService.login(username ?? '', password ?? '').subscribe({
      next() {
        self.router.navigateByUrl('/admin');
      },
      error(err) {
        if (err instanceof HttpErrorResponse) {
          if (err.status === HttpStatusCode.Forbidden)
            self.errorMessage = 'Invlaid Credantials';
          else if (err.status === HttpStatusCode.Locked)
            self.errorMessage = 'This Account Is Locked';
        }
        self.errorMessage = 'Ops Something Went Wrong Try Again Later';
      },
    });
    console.log('login ');
  }
}
