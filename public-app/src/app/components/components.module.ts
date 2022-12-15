import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './sidebar/sidebar.component';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from './navbar/navbar.component';
import { MainAppComponent } from './main-app/main-app.component';



@NgModule({
  declarations: [SidebarComponent, NavbarComponent, MainAppComponent],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [SidebarComponent, NavbarComponent]
})
export class ComponentsModule { }
