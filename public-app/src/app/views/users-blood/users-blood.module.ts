import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material.module';


import { UsersGivenBloodComponent } from './users-given-blood/users-given-blood.component';
import { SortUsersBloodComponent } from './sort-users-blood/sort-users-blood.component';

@NgModule({
  declarations: [
    UsersGivenBloodComponent,
    SortUsersBloodComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports:[
    UsersGivenBloodComponent,
    SortUsersBloodComponent
  ]
})
export class UsersBloodModule { }
