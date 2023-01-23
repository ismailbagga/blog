import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { AdminNavComponent } from './ui/admin-nav/admin-nav.component';
import { AdminArticlesListComponent } from './features/admin-articles-list/admin-articles-list.component';
import { AdminTagListComponent } from './features/admin-tag-list/admin-tag-list.component';

@NgModule({
  declarations: [AdminComponent, AdminNavComponent, AdminArticlesListComponent, AdminTagListComponent],
  imports: [CommonModule, AdminRoutingModule],
})
export class AdminModule {}
