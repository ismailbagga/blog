import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HttpClientModule} from '@angular/common/http' ;

import { ArticleRoutingModule } from './article-routing.module';
import { HomeComponent } from './features/home/home.component';
import { SubscriptionFormComponent } from './ui/subscription-form/subscription-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TagCheckButtonComponent } from './ui/tag-check-button/tag-check-button.component';
import { ArticlePreviewComponent } from './ui/article-preview/article-preview.component';
import { TagPillComponent } from './ui/tag-pill/tag-pill.component';

@NgModule({
  declarations: [HomeComponent, SubscriptionFormComponent, TagCheckButtonComponent, ArticlePreviewComponent, TagPillComponent],
  imports: [CommonModule, ArticleRoutingModule, ReactiveFormsModule ,HttpClientModule],
})
export class ArticleModule {}
