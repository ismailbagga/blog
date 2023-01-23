import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from './loading-spinner/loading-spinner.component';
import { InputFieldComponent } from './input-field/input-field.component';
import { AlertComponent } from './alert/alert.component';

@NgModule({
  declarations: [LoadingSpinnerComponent, InputFieldComponent, AlertComponent],
  imports: [CommonModule, ReactiveFormsModule],
  exports: [LoadingSpinnerComponent, InputFieldComponent, AlertComponent],
})
export class SharedModule {}
