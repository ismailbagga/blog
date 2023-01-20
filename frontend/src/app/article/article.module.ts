import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HttpClientModule} from '@angular/common/http' ;

import { ArticleRoutingModule } from './article-routing.module';
import { HomeComponent } from './features/home/home.component';
import { SubscriptionFormComponent } from './ui/subscription-form/subscription-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TagCheckButtonComponent } from './ui/tag-check-button/tag-check-button.component';

@NgModule({
  declarations: [HomeComponent, SubscriptionFormComponent, TagCheckButtonComponent],
  imports: [CommonModule, ArticleRoutingModule, ReactiveFormsModule ,HttpClientModule],
})
export class ArticleModule {}
