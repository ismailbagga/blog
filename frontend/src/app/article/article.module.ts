import { ArticleSearchComponent } from './features/article-search/article-search.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { ArticleRoutingModule } from './article-routing.module';
import { HomeComponent } from './features/home/home.component';
import { SubscriptionFormComponent } from './ui/subscription-form/subscription-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TagCheckButtonComponent } from './ui/tag-check-button/tag-check-button.component';
import { ArticlePreviewComponent } from './ui/article-preview/article-preview.component';
import { TagPillComponent } from './ui/tag-pill/tag-pill.component';
import { TagsListComponent } from './features/tags-list/tags-list.component';
import { SearchFormComponent } from './ui/search-form/search-form.component';
import { ArticlesOfTagListComponent } from './features/articles-of-tag-list/articles-of-tag-list.component';
import { SharedModule } from '../shared/shared.module';
import { ArticleDetailsComponent } from './features/article-details/article-details.component';
import { MarkdownModule } from 'ngx-markdown';
import { ArticleArrowComponent } from './ui/article-arrow/article-arrow.component';

@NgModule({
  declarations: [
    HomeComponent,
    SubscriptionFormComponent,
    TagCheckButtonComponent,
    ArticlePreviewComponent,
    ArticleSearchComponent,
    TagPillComponent,
    TagsListComponent,
    SearchFormComponent,
    ArticlesOfTagListComponent,
    ArticleDetailsComponent,
    ArticleArrowComponent,
  ],
  imports: [
    CommonModule,
    ArticleRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    SharedModule,
    MarkdownModule.forChild(),
  ],
})
export class ArticleModule {}
