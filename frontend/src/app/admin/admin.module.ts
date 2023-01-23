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

@NgModule({
  declarations: [
    AdminComponent,
    AdminNavComponent,
    AdminArticlesListComponent,
    AdminTagListComponent,
    SearchFieldComponent,
    AdminTableComponent,
    DeleteModelComponent,
  ],
  imports: [CommonModule, AdminRoutingModule, ReactiveFormsModule],
})
export class AdminModule {}
