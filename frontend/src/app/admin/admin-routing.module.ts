import { AdminArticlesListComponent } from './features/admin-articles-list/admin-articles-list.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { AdminTagListComponent } from './features/admin-tag-list/admin-tag-list.component';
import { CreateArticleComponent } from './features/create-article/create-article.component';
import { EditArticleComponent } from './features/edit-article/edit-article.component';
import { EditTagComponent } from './features/edit-tag/edit-tag.component';
import { CreateTagComponent } from './features/create-tag/create-tag.component';

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: '',
        redirectTo: 'articles',
        pathMatch: 'full',
      },
      {
        path: 'articles',
        component: AdminArticlesListComponent,
      },
      {
        path: 'tags',
        component: AdminTagListComponent,
      },
      {
        path: 'edit/article/:slug',
        component: EditArticleComponent,
      },
      {
        path: 'create/article',
        component: CreateArticleComponent,
      },
      {
        path: 'edit/tag/:slug',
        component: EditTagComponent,
      },
      {
        path: 'create/tag',
        component: CreateTagComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
