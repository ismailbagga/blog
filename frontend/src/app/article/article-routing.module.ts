import { ArticlesOfTagListComponent } from './features/articles-of-tag-list/articles-of-tag-list.component';
import { TagsListComponent } from './features/tags-list/tags-list.component';
import { ArticleSearchComponent } from './features/article-search/article-search.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { ArticleDetailsComponent } from './features/article-details/article-details.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'blogs',
    component: ArticleSearchComponent,
  },
  {
    path: 'blogs/:slug',
    component: ArticleDetailsComponent,
  },
  {
    path: 'tags',
    component: TagsListComponent,
  },
  {
    path: 'tags/:slug',
    component: ArticlesOfTagListComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top' })],
  exports: [RouterModule],
})
export class ArticleRoutingModule {}
