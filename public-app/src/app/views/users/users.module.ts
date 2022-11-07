import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material.module';
import { UserComponent } from './user/user.component';
import { UsersComponent } from './users.component';



@NgModule({
  declarations: [
    UsersComponent,
    UserComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [UsersComponent]
})
export class UsersModule { }
