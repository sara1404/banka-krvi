import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material.module';
import { UserComponent } from './user/user.component';
import { UsersComponent } from './users.component';
import { ExaminationComponent } from './examination/examination.component';
import { MatDialogModule } from '@angular/material/dialog';
import { AppointmentInfoComponent } from './appointment-info/appointment-info.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    UsersComponent,
    UserComponent,
    ExaminationComponent,
    AppointmentInfoComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [UsersComponent]
})
export class UsersModule { }
