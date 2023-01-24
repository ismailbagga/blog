import { LoginStateGuard } from './core/guard/login-state.guard';
import { NgModule } from '@angular/core';
import { RouterModule, Routes, CanLoad } from '@angular/router';

const routes: Routes = [
  {
    path: 'admin',
    canLoad: [LoginStateGuard],
    loadChildren: () =>
      import('./admin/admin.module').then((m) => m.AdminModule),
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      anchorScrolling: 'enabled', // scrolls to the anchor element when the URL has a fragment
      scrollOffset: [0, 64], // scroll offset when scrolling to an element (optional)
      scrollPositionRestoration: 'enabled',
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
