import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './nav/nav.component';
import { SideBarComponent } from './ui/side-bar/side-bar.component';

@NgModule({
  declarations: [NavComponent, SideBarComponent],
  imports: [CommonModule, RouterModule],
  exports: [NavComponent],
})
export class LayoutsModule {}
