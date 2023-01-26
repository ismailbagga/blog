import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './nav/nav.component';
import { SideBarComponent } from './ui/side-bar/side-bar.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [NavComponent, SideBarComponent, FooterComponent],
  imports: [CommonModule, RouterModule],
  exports: [NavComponent, FooterComponent],
})
export class LayoutsModule {}
