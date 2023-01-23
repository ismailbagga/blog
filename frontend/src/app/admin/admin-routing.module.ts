import { AdminArticlesListComponent } from './features/admin-articles-list/admin-articles-list.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { AdminTagListComponent } from './features/admin-tag-list/admin-tag-list.component';

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
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
