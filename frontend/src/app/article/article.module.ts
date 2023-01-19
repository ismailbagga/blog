import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ArticleRoutingModule } from './article-routing.module';
import { HomeComponent } from './features/home/home.component';
import { SubscriptionFormComponent } from './ui/subscription-form/subscription-form.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [HomeComponent, SubscriptionFormComponent],
  imports: [CommonModule, ArticleRoutingModule, ReactiveFormsModule],
})
export class ArticleModule {}
