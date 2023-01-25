import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { AdminNavComponent } from './ui/admin-nav/admin-nav.component';
import { AdminArticlesListComponent } from './features/admin-articles-list/admin-articles-list.component';
import { AdminTagListComponent } from './features/admin-tag-list/admin-tag-list.component';
import { SearchFieldComponent } from './ui/search-field/search-field.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AdminTableComponent } from './ui/admin-table/admin-table.component';
import { DeleteModelComponent } from './ui/delete-model/delete-model.component';
import { UpdateArticleComponent } from './features/update-article/update-article.component';
import { CreateArticleComponent } from './features/create-article/create-article.component';
import { UploadFieldComponent } from './ui/upload-field/upload-field.component';
import { SharedModule } from '../shared/shared.module';
import { RelaedTagCheckField } from './ui/related-tags-check-field/related-tags-check-field.component';
import { TagButton } from './ui/tag-button/tag-check-button.component';
import { MarkdownModule } from 'ngx-markdown';
import { ArticleContentComponent } from './ui/article-content/article-content.component';
import { EditArticleComponent } from './features/edit-article/edit-article.component';

@NgModule({
  declarations: [
    AdminComponent,
    AdminNavComponent,
    AdminArticlesListComponent,
    AdminTagListComponent,
    SearchFieldComponent,
    AdminTableComponent,
    DeleteModelComponent,
    UpdateArticleComponent,
    CreateArticleComponent,
    UploadFieldComponent,
    RelaedTagCheckField,
    TagButton,
    ArticleContentComponent,
    EditArticleComponent,
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    ReactiveFormsModule,
    SharedModule,
    MarkdownModule.forChild(),
  ],
})
export class AdminModule {}
