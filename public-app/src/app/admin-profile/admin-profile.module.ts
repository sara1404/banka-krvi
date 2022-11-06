import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import { BloodbankInfoComponent } from './bloodbank-info/bloodbank-info.component';
import { OtherAdminsComponent } from './other-admins/other-admins.component';
import { AdminInfoComponent } from './admin-info/admin-info.component';



@NgModule({
  declarations: [
    AdminProfileComponent,
    BloodbankInfoComponent,
    OtherAdminsComponent,
    AdminInfoComponent
  ],
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatTableModule
  ]
})
export class AdminProfileModule { }
