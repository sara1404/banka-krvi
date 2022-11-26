import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material.module';
import { UserComponent } from './user/user.component';
import { UsersComponent } from './users.component';
import { ExaminationComponent } from './examination/examination.component';
import { MatDialogModule } from '@angular/material/dialog';



@NgModule({
  declarations: [
    UsersComponent,
    UserComponent,
    ExaminationComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    MatDialogModule
  ],
  exports: [UsersComponent]
})
export class UsersModule { }
