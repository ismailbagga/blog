import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from './loading-spinner/loading-spinner.component';
import { InputFieldComponent } from './input-field/input-field.component';
import { AlertComponent } from './alert/alert.component';
import { TextAreaFieldComponent } from './text-area-field/text-area-field.component';
import { ToastComponent } from './toast/toast.component';

@NgModule({
  declarations: [
    LoadingSpinnerComponent,
    InputFieldComponent,
    AlertComponent,
    TextAreaFieldComponent,
    ToastComponent,
  ],
  imports: [CommonModule, ReactiveFormsModule],
  exports: [
    LoadingSpinnerComponent,
    InputFieldComponent,
    AlertComponent,
    TextAreaFieldComponent,
    ToastComponent,
  ],
})
export class SharedModule {}
